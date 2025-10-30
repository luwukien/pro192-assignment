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

    private StudentManager studentManager; // Thuộc tính này ban đầu là null
    private CourseManager courseManager;
    private SubjectManager subjectManager;

    // CONSTRUCTOR ĐÃ THAY ĐỔI: Chỉ nhận 3 Manager (bỏ StudentManager)
    // Khớp với đề xuất: create constructor "RegistrationManager(List<Registration>, CourseManager, SubjectManager)"
    public RegistrationManager(List<Registration> initialList, 
                               CourseManager courseMan, 
                               SubjectManager subjectMan) {
        super(initialList);
        this.courseManager = courseMan;
        this.subjectManager = subjectMan;
        // studentManager sẽ được gán sau bằng setter
    }
    
    // ---------------------------------------------------------------------
    // PHƯƠNG THỨC SETTER MỚI (PHẢI CÓ)
    // Khớp với đề xuất: Create method "setStudentManager(StudentManager)"
    // ---------------------------------------------------------------------

    public void setStudentManager(StudentManager studentManager) {
        this.studentManager = studentManager;
    }
    
    // ---------------------------------------------------------------------
    // CÁC PHƯƠNG THỨC NGHIỆP VỤ (GIỮ NGUYÊN)
    // ---------------------------------------------------------------------
    
    public boolean registerCourse(String studentId, String courseSectionId) {
        // Đảm bảo studentManager không null trước khi sử dụng
        if (studentManager == null) {
            System.err.println("Lỗi nội bộ: StudentManager chưa được khởi tạo!");
            return false;
        }
        
        Student student = studentManager.findById(studentId);
        CourseSection course = courseManager.findById(courseSectionId);
        
        // ... (phần còn lại của logic registerCourse) ...
        if (student == null || course == null) {
            System.out.println("Lỗi: Student ID hoặc Course Section ID không tồn tại.");
            return false;
        }
        // ... (các kiểm tra khác) ...

        // Hoàn tất đăng ký
        Registration newRegis = new Registration(studentId, courseSectionId, 0.0, RegistrationStatus.ENROLLED);
        super.add(newRegis);
        course.incrementStudentCount();
        courseManager.update(course); 
        System.out.println("Đăng ký thành công cho sinh viên ID: " + studentId);
        return true;
    }
    
    // ... (các phương thức withdrawCourse, calculateOverallGPA, displayAll, v.v., giữ nguyên) ...

    public double calculateOverallGPA(String studentId) {
        // Logic tính GPA
        // ... 
        return 0.0; // Thay bằng logic thực tế
    }
    
    public List<Registration> getRegistrationsByStudent(String studentId) {
        return list.stream().filter(r -> r.getStudentId().equals(studentId)).collect(Collectors.toList());
    }

    @Override
    public void displayAll() {
        if (this.list.isEmpty()) { System.out.println("Danh sách đăng ký học phần trống."); return; }
        System.out.println("=== DANH SÁCH ĐĂNG KÝ HỌC PHẦN ===");
        for (Registration registration : list) { System.out.println(registration); }
    }
}