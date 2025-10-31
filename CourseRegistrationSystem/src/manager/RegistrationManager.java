package manager;

import java.util.List;
import data.Registration;
import data.CourseSection;
import data.Subject;
import data.Student;
import data.enums.RegistrationStatus;
import interfaces.Displayable;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class RegistrationManager extends Management<Registration> implements Displayable {

    private StudentManager studentManager;
    private CourseManager courseManager;
    private SubjectManager subjectManager;

    // CONSTRUCTOR MỚI: Bỏ StudentManager khỏi danh sách tham số để giải quyết lỗi vòng lặp khởi tạo
    public RegistrationManager(List<Registration> initialList, 
                               CourseManager courseMan, 
                               SubjectManager subjectMan) {
        super(initialList);
        this.courseManager = courseMan;
        this.subjectManager = subjectMan;
        // studentManager sẽ được gán sau bằng setter
    }
    
    // PHƯƠNG THỨC SETTER MỚI (CẦN THIẾT ĐỂ GIẢI QUYẾT LỖI VÒNG)
    public void setStudentManager(StudentManager studentManager) {
        this.studentManager = studentManager;
    }

    // ---------------------------------------------------------------------
    // PHƯƠNG THỨC NGHIỆP VỤ: ĐĂNG KÝ/HỦY VÀ TÍNH ĐIỂM
    // ---------------------------------------------------------------------

    /**
     * Thực hiện đăng ký khóa học với nhiều quy tắc kiểm tra.
     * @param studentId
     * @param courseSectionId
     * @return 
     */
    public boolean registerCourse(String studentId, String courseSectionId) {
        if (studentManager == null) { 
            System.err.println("Lỗi nội bộ: StudentManager chưa được gán!"); 
            return false; 
        }
        
        Student student = studentManager.findById(studentId);
        CourseSection course = courseManager.findById(courseSectionId);

        if (student == null || course == null) { 
             System.out.println("Lỗi: Student ID hoặc Course Section ID không tồn tại.");
            return false; 
        }

        if (course.isFull()) {
            System.out.println("Lỗi: Học phần " + courseSectionId + " đã đầy!");
            return false;
        }

        // Kiểm tra trùng lặp đăng ký học phần (sử dụng findById từ lớp cha)
        if (findById(studentId + "_" + courseSectionId) != null) {
            System.out.println("Lỗi: Sinh viên đã đăng ký học phần này.");
            return false;
        }

        String subjectId = course.getSubjectId();
        Subject subject = subjectManager.findById(subjectId);
        if (subject == null) {
            System.out.println("Lỗi: Không tìm thấy Subject ID " + subjectId + ".");
            return false;
        }
        
        // Kiểm tra Giới hạn tín chỉ (Max 20 credits)
        int newCredits = subject.getCredits();
        int targetSemester = course.getSemester();
        int currentCredits = calculateCurrentCredits(studentId, targetSemester);
        if (currentCredits + newCredits > 20) {
            System.out.println("Lỗi: Vượt quá 20 tín chỉ cho học kỳ này!");
            return false;
        }

        // Kiểm tra Điều kiện tiên quyết (Prerequisite Check)
        List<String> prerequisiteSubjects = subject.getPrerequisiteSubjectIds();
        List<Registration> studentRegistrations = this.getRegistrationsByStudent(studentId);

        for (String preSubjectId : prerequisiteSubjects) {
            boolean foundAndPassed = false;
            for (Registration registration : studentRegistrations) {
                CourseSection historicalSection = courseManager.findById(registration.getCourseSectionId());
                if (historicalSection != null && historicalSection.getSubjectId().equals(preSubjectId)) {
                    if (registration.getStatus() == RegistrationStatus.PASSED) {
                        foundAndPassed = true;
                        break;
                    }
                }
            }
            if (!foundAndPassed) {
                System.out.println("Lỗi: Chưa hoàn thành môn tiên quyết ID: " + preSubjectId + "!");
                return false;
            }
        }
        
        // Hoàn tất đăng ký
        Registration newRegis = new Registration(studentId, courseSectionId, 0.0, RegistrationStatus.ENROLLED);
        super.add(newRegis); // Dùng hàm add() từ lớp cha
        course.incrementStudentCount();
        courseManager.update(course); // Cập nhật lại CourseManager
        System.out.println("Đăng ký thành công cho sinh viên ID: " + studentId);
        return true;
    }
    
    /**
     * Hủy đăng ký học phần (chuyển trạng thái sang WITHDRAWN).
     * @param studentId
     * @param courseSectionId
     * @return 
     */
    public boolean withdrawCourse(String studentId, String courseSectionId) {
        String registrationId = studentId + "_" + courseSectionId;
        Registration reg = findById(registrationId);
        
        if (reg == null || reg.getStatus() != RegistrationStatus.ENROLLED) {
            System.out.println("Lỗi: Đăng ký không tồn tại hoặc không ở trạng thái ENROLLED.");
            return false;
        }
        
        // Cập nhật trạng thái và giảm số lượng sinh viên
        reg.setStatus(RegistrationStatus.WITHDRAWN);
        boolean success = this.update(reg); // Dùng hàm update() từ lớp cha
        
        if (success) {
            CourseSection cs = courseManager.findById(courseSectionId);
            if (cs != null) {
                cs.decrementStudentCount();
                courseManager.update(cs); 
            }
            System.out.println("Hủy đăng ký thành công cho sinh viên ID: " + studentId);
        }
        return success;
    }
    
    /**
     * Tính GPA tổng (Overall GPA) cho một sinh viên.
     * @param studentId
     * @return 
     */
    public double calculateOverallGPA(String studentId) {
        List<Registration> studentRegs = getRegistrationsByStudent(studentId);
        if (studentRegs.isEmpty()) return 0.0;
        
        double totalWeightedGrade = 0.0;
        int totalCredits = 0;

        for (Registration r : studentRegs) {
            CourseSection cs = courseManager.findById(r.getCourseSectionId());
            Subject subject = (cs != null) ? subjectManager.findById(cs.getSubjectId()) : null;
            
            if (subject != null && (r.getStatus() == RegistrationStatus.PASSED || r.getStatus() == RegistrationStatus.FAILED)) {
                int credits = subject.getCredits(); 
                totalWeightedGrade += r.getGrade() * credits;
                totalCredits += credits;
            }
        }
        
        return (totalCredits == 0) ? 0.0 : totalWeightedGrade / totalCredits; 
    }

    /**
     * Tính GPA theo học kỳ.
     * @param studentId
     * @param semester
     * @return 
     */
    public double calculateSemesterGPA(String studentId, int semester) {
        List<Registration> studentRegs = getRegistrationsByStudent(studentId);
        if (studentRegs.isEmpty()) return 0.0;

        double totalWeightedGrade = 0.0;
        int totalCredits = 0;

        for (Registration r : studentRegs) {
            CourseSection cs = courseManager.findById(r.getCourseSectionId());
            if (cs == null || cs.getSemester() != semester) continue;

            Subject subject = subjectManager.findById(cs.getSubjectId());
            if (subject != null && (r.getStatus() == RegistrationStatus.PASSED || r.getStatus() == RegistrationStatus.FAILED)) {
                int credits = subject.getCredits(); 
                totalWeightedGrade += r.getGrade() * credits;
                totalCredits += credits;
            }
        }
        
        return (totalCredits == 0) ? 0.0 : totalWeightedGrade / totalCredits;
    }
    
    // ---------------------------------------------------------------------
    // PHƯƠNG THỨC TIỆN ÍCH VÀ INTERFACE
    // ---------------------------------------------------------------------

    private int calculateCurrentCredits(String studentId, int semester) {
        int totalCredits = 0;
        for (Registration registration : getRegistrationsByStudent(studentId)) {
            if (registration.getStatus() == RegistrationStatus.ENROLLED) {
                CourseSection section = courseManager.findById(registration.getCourseSectionId());
                Subject subject = (section != null && section.getSemester() == semester) ? subjectManager.findById(section.getSubjectId()) : null;
                if (subject != null) { totalCredits += subject.getCredits(); }
            }
        }
        return totalCredits;
    }
    
    public List<Registration> getRegistrationsByStudent(String studentId) {
        return list.stream().filter(r -> r.getStudentId().equals(studentId)).collect(Collectors.toList());
    }

    // implement Displayable
    @Override
    public void displayAll() {
        if (this.list.isEmpty()) { System.out.println("Danh sách đăng ký học phần trống."); return; }
        System.out.println("=== DANH SÁCH ĐĂNG KÝ HỌC PHẦN ===");
        for (Registration registration : list) { System.out.println(registration); }
    }
}