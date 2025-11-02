package courseregistrationsystem;

import data.Student;
import data.Subject;
import data.CourseSection;
import data.Registration;
import data.enums.StudentStatus;
import manager.*;
import util.FileHandler;
import util.Menu;
import util.Validator;

import java.util.List;

public class CourseRegistrationSystem {

    // Khai báo các Manager và FileHandler
    private StudentManager studentManager;
    private SubjectManager subjectManager;
    private CourseManager courseManager;
    private RegistrationManager registrationManager;
    private FileHandler fileHandler; 

    // Constructor: Khởi tạo FileHandler
    public CourseRegistrationSystem() {
        this.fileHandler = new FileHandler(); 
    }

    public static void main(String[] args) {
        CourseRegistrationSystem system = new CourseRegistrationSystem();
        system.run();
    }

    // +run(): void
    public void run() {
        initializeManagers();
        int choice;

        do {
            choice = Menu.showMainMenu();
            switch (choice) {
                case 1: handleStudentManagement(); break;
                case 2: handleSubjectManagement(); break; // Giữ tên hàm theo logic Menu
                case 3: handleCourseManagement(); break; // Giữ tên hàm theo logic Menu
                case 4: handleRegistrationManagement(); break; // Giữ tên hàm theo logic Menu
                case 5: handleReportMenu(); break;
                case 6: handleSaveData(); break;
                default: System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        } while (choice != 6);
    }

    /**
     * Khởi tạo các Manager: Đã sửa lỗi phụ thuộc vòng bằng cách dùng setter.
     */
    private void initializeManagers() {
        System.out.println("--- KHỞI TẠO HỆ THỐNG ---");
        
        // 1. Tải dữ liệu từ File (Sử dụng FileHandler static)
        List<Student> initialStudents = FileHandler.loadStudents(FileHandler.STUDENT_FILE);
        List<Subject> initialSubjects = FileHandler.loadSubjects(FileHandler.SUBJECT_FILE);
        List<CourseSection> initialCourses = FileHandler.loadCourseSections(FileHandler.COURSE_FILE);
        List<Registration> initialRegistrations = FileHandler.loadRegistrations(FileHandler.REG_FILE);

        // 2. Khởi tạo các Manager độc lập (Subject và Course)
        subjectManager = new SubjectManager(initialSubjects);
        courseManager = new CourseManager(initialCourses);
        
        // 3. Khởi tạo RegistrationManager (Chỉ truyền Course và Subject)
        // GIẢ ĐỊNH Constructor mới: RegistrationManager(List, CourseManager, SubjectManager)
        registrationManager = new RegistrationManager(
            initialRegistrations, 
            courseManager, 
            subjectManager 
        );

        // 4. Khởi tạo StudentManager (Cần RegistrationManager)
        studentManager = new StudentManager(
            initialStudents, 
            registrationManager
        );

        // 5. Gán StudentManager đã khởi tạo cho RegistrationManager (GIẢI QUYẾT PHỤ THUỘC VÒNG)
        // YÊU CẦU: Lớp RegistrationManager phải có public void setStudentManager(StudentManager sm)
        // Đây là bước quan trọng nhất để fix lỗi!
        registrationManager.setStudentManager(studentManager);

        System.out.println("Hệ thống khởi tạo thành công.");
        System.out.println("---------------------------");
    }

    // +handleSaveData(): void
    public void handleSaveData() {
        System.out.println("--- LƯU DỮ LIỆU VÀ THOÁT ---");
        
        // Lưu dữ liệu (sử dụng đối tượng fileHandler)
        FileHandler.saveDataToFile(studentManager.getAll(), FileHandler.STUDENT_FILE);
        FileHandler.saveDataToFile(subjectManager.getAll(), FileHandler.SUBJECT_FILE);
        FileHandler.saveDataToFile(courseManager.getAll(), FileHandler.COURSE_FILE);
        FileHandler.saveDataToFile(registrationManager.getAll(), FileHandler.REG_FILE);

        System.out.println("Đã lưu tất cả dữ liệu thành công. Hẹn gặp lại!");
    }

// ---------------------------------------------------------------------
    // XỬ LÝ MENU QUẢN LÝ SINH VIÊN (CASE 1)
    // ---------------------------------------------------------------------

    private void handleStudentManagement() {
        int choice;
        do {
            choice = Menu.showStudentManagementMenu();
            switch(choice) {
                case 1: handleAddStudent(); break; 
                case 2: handleFindStudentById(); break; 
                case 3: handleFindStudentByName(); break; 
                case 4: handleUpdateStudent(); break; 
                case 5: handleDeleteStudent(); break; 
                case 6: studentManager.displayAll(); break;
                case 7: studentManager.sortByGPA(); studentManager.displayAll(); break;
                case 8: return; // Quay lại Menu Chính
                default: System.out.println("Lựa chọn không hợp lệ.");
            }
        } while (choice != 8);
    }

    private void handleAddStudent() {
        System.out.println("\n--- THÊM SINH VIÊN MỚI ---");
        String id;
        do {
            id = Validator.getString("Nhập ID sinh viên (VD: S1001): ", "ID không hợp lệ hoặc rỗng.", "^[sS]\\d{4}$"); 
            if (studentManager.findById(id) != null) {
                System.out.println("Lỗi: ID sinh viên đã tồn tại. Vui lòng nhập ID khác.");
            } else { break; }
        } while (true);
        
        String fullName = Validator.getString("Nhập Họ và Tên: ", "Tên không được rỗng.");
        String major = Validator.getString("Nhập Chuyên ngành: ", "Chuyên ngành không được rỗng.");
        String email = Validator.getString("Nhập Email: ", "Email không được rỗng.", "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"); 
        
        // Tạo đối tượng Student (5 tham số)
        data.Student newStudent = new data.Student(id, fullName, major, email, data.enums.StudentStatus.ACTIVE); 
        
        if (studentManager.add(newStudent)) {
            System.out.println("Thêm sinh viên thành công: " + newStudent);
        } else {
            System.out.println("Thêm sinh viên thất bại.");
        }
    }
    
    private void handleFindStudentById() {
        System.out.println("\n--- TÌM KIẾM SINH VIÊN THEO ID ---");
        String id = Validator.getString("Nhập ID sinh viên cần tìm: ", "ID không được rỗng.");
        data.Student s = studentManager.findById(id);
        if (s != null) {
            System.out.println("Tìm thấy sinh viên: " + s);
        } else {
            System.out.println("Không tìm thấy sinh viên có ID: " + id);
        }
    }
    
    private void handleFindStudentByName() {
        System.out.println("\n--- TÌM KIẾM SINH VIÊN THEO TÊN ---");
        String name = Validator.getString("Nhập tên (hoặc một phần tên) cần tìm: ", "Tên không được rỗng.");
        List<data.Student> results = studentManager.findByName(name);
        if (!results.isEmpty()) {
            System.out.println("=== KẾT QUẢ TÌM KIẾM THEO TÊN ===");
            results.forEach(System.out::println);
        } else {
            System.out.println("Không tìm thấy sinh viên nào có tên chứa: " + name);
        }
    }

    private void handleDeleteStudent() {
        System.out.println("\n--- XÓA SINH VIÊN ---");
        String id = Validator.getString("Nhập ID sinh viên cần xóa: ", "ID không được rỗng.");
        if (studentManager.delete(id)) { // Dùng hàm delete(String id) từ Management
            System.out.println("Xóa sinh viên ID " + id + " thành công.");
        } else {
            System.out.println("Xóa sinh viên thất bại.");
        }
    }

    private void handleUpdateStudent() {
        System.out.println("\n--- CẬP NHẬT THÔNG TIN SINH VIÊN ---");
        String id = Validator.getString("Nhập ID sinh viên cần cập nhật: ", "ID không được rỗng.");
        data.Student studentToUpdate = studentManager.findById(id);
        
        if (studentToUpdate == null) {
            System.out.println("Lỗi: Không tìm thấy sinh viên có ID: " + id);
            return;
        }

        System.out.println("--- Đang cập nhật cho: " + studentToUpdate.getFullName() + " ---");
        
        // Nhập thông tin mới (sử dụng Enter để giữ nguyên)
        String newFullName = Validator.getString(
            "Nhập Họ và Tên mới (Enter để giữ nguyên '" + studentToUpdate.getFullName() + "'): ", null
        );
        if (!newFullName.isEmpty()) { studentToUpdate.setFullName(newFullName); }
        
        String newMajor = Validator.getString(
            "Nhập Chuyên ngành mới (Enter để giữ nguyên '" + studentToUpdate.getMajor() + "'): ", null
        );
        if (!newMajor.isEmpty()) { studentToUpdate.setMajor(newMajor); }
        
        String newEmail = Validator.getString(
            "Nhập Email mới (Enter để giữ nguyên '" + studentToUpdate.getEmail() + "'): ", null
        );
        if (!newEmail.isEmpty()) { studentToUpdate.setEmail(newEmail); }

        // Gọi hàm update() từ Management
        if (studentManager.update(studentToUpdate)) {
            System.out.println("Cập nhật sinh viên ID " + id + " thành công!");
        } else {
            System.out.println("Cập nhật thất bại.");
        }
    }

    // ---------------------------------------------------------------------
    // XỬ LÝ MENU QUẢN LÝ MÔN HỌC (CASE 2)
    // ---------------------------------------------------------------------

    private void handleSubjectManagement() {
        int choice;
        do {
            choice = Menu.showSubjectManagementMenu();
            switch(choice) {
                case 1: handleAddSubject(); break;
                case 2: handleFindSubjectById(); break;
                case 3: handleUpdateSubject(); break;
                case 4: handleDeleteSubject(); break;
                case 5: subjectManager.displayAll(); break;
                case 6: return; // Quay lại Menu chính
                default: System.out.println("Lựa chọn không hợp lệ.");
            }
        } while (choice != 6);
    }

    private void handleAddSubject() {
        System.out.println("\n--- THÊM MÔN HỌC MỚI ---");
        String id;
        do {
            id = Validator.getString("Nhập ID môn học (VD: IT101): ", "ID không được rỗng.", "^[a-zA-Z]{2,4}\\d{3}$");
            if (subjectManager.findById(id) != null) {
                System.out.println("Lỗi: ID môn học đã tồn tại. Vui lòng nhập ID khác.");
            } else { break; }
        } while (true);
        
        String name = Validator.getString("Nhập Tên môn học: ", "Tên không được rỗng.");
        int credits = Validator.getInt("Nhập Số tín chỉ (1-10): ", "Số tín chỉ phải là số nguyên.", 1, 10);
        
        data.Subject newSubject = new data.Subject(id, name, credits);
        
        // Xử lý Tiên quyết (Prerequisite)
        boolean addMore;
        do {
            addMore = Validator.getYesNo("Bạn có muốn thêm môn tiên quyết không? (Y/N): ", "Vui lòng nhập Y hoặc N.");
            if (addMore) {
                String preId = Validator.getString("Nhập ID môn tiên quyết: ", "ID không được rỗng.");
                newSubject.addPrerequisite(preId);
            }
        } while (addMore);
        
        if (subjectManager.add(newSubject)) {
            System.out.println("Thêm môn học thành công: " + newSubject);
        } else {
            System.out.println("Thêm môn học thất bại.");
        }
    }

    private void handleFindSubjectById() {
        System.out.println("\n--- TÌM KIẾM MÔN HỌC THEO ID ---");
        String id = Validator.getString("Nhập ID môn học cần tìm: ", "ID không được rỗng.");
        data.Subject s = subjectManager.findById(id);
        if (s != null) {
            System.out.println("Tìm thấy môn học: " + s);
            System.out.println("  Môn tiên quyết: " + s.getPrerequisiteSubjectIds());
        } else {
            System.out.println("Không tìm thấy môn học có ID: " + id);
        }
    }

    private void handleUpdateSubject() {
        System.out.println("Tính năng cập nhật Môn học đang được triển khai.");
    }

    private void handleDeleteSubject() {
        String id = Validator.getString("Nhập ID môn học cần xóa: ", "ID không được rỗng.");
        if (subjectManager.delete(id)) {
            System.out.println("Xóa môn học ID " + id + " thành công.");
        } else {
            System.out.println("Xóa môn học thất bại.");
        }
    }
    
    // ---------------------------------------------------------------------
    // XỬ LÝ MENU QUẢN LÝ HỌC PHẦN (CASE 3)
    // ---------------------------------------------------------------------

    private void handleCourseManagement() {
        int choice;
        do {
            choice = Menu.showCourseManagementMenu();
            switch(choice) {
                case 1: handleAddCourseSection(); break;
                case 2: handleFindCourseSectionById(); break;
                case 3: handleFindCourseSectionBySubjectId(); break;
                case 4: handleUpdateCourseSection(); break;
                case 5: handleDeleteCourseSection(); break;
                case 6: courseManager.displayAll(); break;
                case 7: return; // Quay lại Menu chính
                default: System.out.println("Lựa chọn không hợp lệ.");
            }
        } while (choice != 7);
    }

    private void handleAddCourseSection() {
        System.out.println("\n--- THÊM HỌC PHẦN MỚI ---");
        String subjectId;
        
        // 1. Kiểm tra Subject ID có tồn tại không
        do {
            subjectId = Validator.getString("Nhập ID Môn học (Subject ID) cho học phần: ", "ID không được rỗng.");
            if (subjectManager.findById(subjectId) == null) {
                System.out.println("Lỗi: Môn học với ID " + subjectId + " không tồn tại. Vui lòng thêm môn học trước.");
            } else { break; }
        } while (true);
        
        // 2. Nhập các trường còn lại
        String courseSectionId = Validator.getString("Nhập ID Học phần (VD: IT101-L1): ", "ID không được rỗng.");
        int semester = Validator.getInt("Nhập Học kỳ (Semester 1-10): ", "Học kỳ phải là số nguyên.", 1, 10);
        int maxStudents = Validator.getInt("Nhập Số lượng SV tối đa (Max Students 10-100): ", "Số lượng SV phải hợp lệ.", 10, 100);
        String dayOfWeek = Validator.getString("Nhập Ngày học (VD: Mon, Tue): ", "Ngày học không hợp lệ.");
        int startSlot = Validator.getInt("Nhập Tiết bắt đầu (Slot 1-10): ", "Tiết học phải hợp lệ.", 1, 10);
        int endSlot = Validator.getInt("Nhập Tiết kết thúc (Slot 1-10): ", "Tiết học phải hợp lệ.", startSlot, 10);
        
        // 3. Tạo đối tượng và thêm vào Manager
        data.CourseSection newCS = new data.CourseSection(
            courseSectionId, subjectId, semester, maxStudents, 
            0, // currentStudentCount = 0
            dayOfWeek, startSlot, endSlot
        );
        
        if (courseManager.add(newCS)) {
            System.out.println("Thêm học phần thành công: " + newCS);
        } else {
            System.out.println("Thêm học phần thất bại (ID có thể đã trùng).");
        }
    }

    private void handleFindCourseSectionById() {
        System.out.println("\n--- TÌM KIẾM HỌC PHẦN THEO ID ---");
        String id = Validator.getString("Nhập ID học phần cần tìm: ", "ID không được rỗng.");
        data.CourseSection cs = courseManager.findById(id);
        if (cs != null) {
            System.out.println("Tìm thấy học phần: " + cs);
            System.out.println("  Tình trạng: " + cs.getCurrentStudentCount() + "/" + cs.getMaxStudents());
        } else {
            System.out.println("Không tìm thấy học phần có ID: " + id);
        }
    }

    private void handleFindCourseSectionBySubjectId() {
        System.out.println("\n--- TÌM KIẾM HỌC PHẦN THEO SUBJECT ID ---");
        String id = Validator.getString("Nhập Subject ID cần tìm: ", "ID không được rỗng.");
        // Giả sử bạn giữ lại hàm findBySubjectId trong CourseManager
        List<data.CourseSection> results = courseManager.findBySubjectId(id); 
        
        if (!results.isEmpty()) {
            System.out.println("=== KẾT QUẢ TÌM KIẾM CHO SUBJECT " + id + " ===");
            results.forEach(cs -> System.out.println("  " + cs));
        } else {
            System.out.println("Không tìm thấy học phần nào cho Subject ID: " + id);
        }
    }

    private void handleUpdateCourseSection() {
        System.out.println("Tính năng cập nhật Học phần đang được triển khai.");
    }
    
    private void handleDeleteCourseSection() {
        String id = Validator.getString("Nhập ID học phần cần xóa: ", "ID không được rỗng.");
        if (courseManager.delete(id)) {
            System.out.println("Xóa học phần ID " + id + " thành công.");
        } else {
            System.out.println("Xóa học phần thất bại.");
        }
    }
    
    // ---------------------------------------------------------------------
    // CÁC PHƯƠNG THỨC PLACEHOLDER CHƯA TRIỂN KHAI
    // ---------------------------------------------------------------------
    
    // -handleRegistrationManagement(): void
private void handleRegistrationManagement() {
    int choice;
    do {
        choice = Menu.showRegistrationManagementMenu();
        switch(choice) {
            case 1: handleRegisterForStudent(); break; // Đăng ký
            case 2: handleWithdrawCourse(); break;    // Hủy đăng ký
            case 3: handleInputGrade(); break;        // Nhập điểm
            case 4: handleViewStudentRegistration(); break; // Xem đăng ký của SV
            case 5: handleViewCourseStudents(); break;      // Xem SV của học phần
            case 6: return; // Quay lại Menu chính
            default: System.out.println("Lựa chọn không hợp lệ.");
        }
    } while (choice != 6);
    
}
    private void handleRegisterForStudent() {
        System.out.println("\n--- ĐĂNG KÝ HỌC PHẦN ---");
    
    // Yêu cầu nhập ID sinh viên và ID học phần
    String studentId = Validator.getString("Nhập ID sinh viên: ", "ID không được rỗng.", "^[sS]\\d{4}$");
    String courseSectionId = Validator.getString("Nhập ID học phần (VD: IT101-L1): ", "ID không được rỗng.");
    
    // Gọi hàm nghiệp vụ phức tạp
    registrationManager.registerCourse(studentId, courseSectionId);
}
    private void handleWithdrawCourse() {
    System.out.println("\n--- HỦY ĐĂNG KÝ HỌC PHẦN ---");
    
    String studentId = Validator.getString("Nhập ID sinh viên cần hủy đăng ký: ", "ID không được rỗng.", "^[sS]\\d{4}$");
    String courseSectionId = Validator.getString("Nhập ID học phần cần hủy: ", "ID không được rỗng.");
    
    // Gọi hàm nghiệp vụ
    registrationManager.withdrawCourse(studentId, courseSectionId);
}
    private void handleInputGrade() {
    System.out.println("\n--- NHẬP ĐIỂM VÀ CẬP NHẬT TRẠNG THÁI ---");
    
    String studentId = Validator.getString("Nhập ID sinh viên: ", "ID không được rỗng.", "^[sS]\\d{4}$");
    String courseSectionId = Validator.getString("Nhập ID học phần: ", "ID không được rỗng.");
    
    String regId = studentId + "_" + courseSectionId;
    Registration reg = registrationManager.findById(regId);

    if (reg == null) {
        System.out.println("Lỗi: Không tìm thấy bản ghi đăng ký cho học phần này.");
        return;
    }
    
    // Kiểm tra xem môn học đã có điểm hay đã kết thúc chưa
    if (reg.getStatus() == data.enums.RegistrationStatus.PASSED || 
        reg.getStatus() == data.enums.RegistrationStatus.FAILED) {
        System.out.println("Cảnh báo: Học phần này đã có điểm (Trạng thái: " + reg.getStatus() + ").");
    }

    // Nhập điểm mới (giả định thang điểm 0-10)
    double grade = Validator.getDouble("Nhập Điểm mới (0.0 - 10.0): ", "Điểm không hợp lệ.", 0.0, 10.0);
    
    // Cập nhật điểm và trạng thái
    reg.setGrade(grade);
    reg.updateStatusByGrade(); // Logic nghiệp vụ nhỏ trong lớp Registration
    
    if (registrationManager.update(reg)) {
        System.out.println("Cập nhật điểm thành công. Trạng thái mới: " + reg.getStatus());
    } else {
        System.out.println("Lỗi: Cập nhật điểm thất bại.");
    }
}
    private void handleViewStudentRegistration() {
    System.out.println("\n--- XEM ĐĂNG KÝ THEO SINH VIÊN ---");
    String studentId = Validator.getString("Nhập ID sinh viên: ", "ID không được rỗng.", "^[sS]\\d{4}$");
    
    List<Registration> list = registrationManager.getRegistrationsByStudent(studentId);
    
    if (list.isEmpty()) {
        System.out.println("Sinh viên " + studentId + " chưa đăng ký học phần nào.");
    } else {
        System.out.println("=== DANH SÁCH ĐĂNG KÝ CỦA SV " + studentId + " ===");
        list.forEach(System.out::println);
    }
}

private void handleViewCourseStudents() {
    System.out.println("\n--- XEM DANH SÁCH SINH VIÊN THEO HỌC PHẦN ---");
    String courseId = Validator.getString("Nhập ID học phần: ", "ID không được rỗng.");
    
    List<Registration> list = registrationManager.getRegistrationsByCourseSection(courseId);
    
    if (list.isEmpty()) {
        System.out.println("Học phần " + courseId + " chưa có sinh viên nào đăng ký.");
    } else {
        System.out.println("=== DANH SÁCH SINH VIÊN ĐĂNG KÝ HỌC PHẦN " + courseId + " ===");
        list.forEach(r -> System.out.println("  - Sinh viên ID: " + r.getStudentId() + ", Trạng thái: " + r.getStatus()));
    }
}

private void handleReportMenu() {
    int choice;
    do {
        choice = Menu.showReportMenu();
        switch(choice) {
            case 1: handleCalculateOverallGPA(); break; // Tính GPA tổng
            case 2: handleCalculateSemesterGPA(); break; // Tính GPA theo học kỳ
            case 3: return; // Quay lại Menu chính
            default: System.out.println("Lựa chọn không hợp lệ.");
        }
    } while (choice != 3);
}
    private void handleCalculateOverallGPA() {
    System.out.println("\n--- TÍNH GPA TỔNG ---");
    
    // Yêu cầu nhập ID sinh viên và kiểm tra tồn tại
    String studentId = Validator.getString("Nhập ID sinh viên: ", "ID không được rỗng.", "^[sS]\\d{4}$");
    
    Student student = studentManager.findById(studentId);
    if (student == null) {
        System.out.println("Lỗi: Không tìm thấy sinh viên có ID: " + studentId);
        return;
    }
    
    // Gọi hàm nghiệp vụ tính GPA
    double gpa = registrationManager.calculateOverallGPA(studentId);
    
    System.out.println("-------------------------------------------");
    System.out.printf("| GPA tổng tích lũy của %s là: %.2f\n", student.getFullName(), gpa);
    System.out.println("-------------------------------------------");
}
    private void handleCalculateSemesterGPA() {
    System.out.println("\n--- TÍNH GPA THEO HỌC KỲ ---");
    
    // 1. Nhập ID sinh viên và kiểm tra tồn tại
    String studentId = Validator.getString("Nhập ID sinh viên: ", "ID không được rỗng.", "^[sS]\\d{4}$");
    Student student = studentManager.findById(studentId);
    if (student == null) {
        System.out.println("Lỗi: Không tìm thấy sinh viên có ID: " + studentId);
        return;
    }
    
    // 2. Nhập học kỳ
    int semester = Validator.getInt("Nhập Học kỳ (1-10) cần tính: ", "Học kỳ phải là số nguyên hợp lệ.", 1, 10);
    
    // 3. Gọi hàm nghiệp vụ tính GPA theo học kỳ
    double semGpa = registrationManager.calculateSemesterGPA(studentId, semester);
    
    System.out.println("-------------------------------------------");
    System.out.printf("| GPA Học kỳ %d của %s là: %.2f\n", semester, student.getFullName(), semGpa);
    System.out.println("-------------------------------------------");
}
}