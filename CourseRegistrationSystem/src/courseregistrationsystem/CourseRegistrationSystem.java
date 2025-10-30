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
        fileHandler.saveDataToFile(studentManager.getAll(), FileHandler.STUDENT_FILE);
        fileHandler.saveDataToFile(subjectManager.getAll(), FileHandler.SUBJECT_FILE);
        fileHandler.saveDataToFile(courseManager.getAll(), FileHandler.COURSE_FILE);
        fileHandler.saveDataToFile(registrationManager.getAll(), FileHandler.REG_FILE);

        System.out.println("Đã lưu tất cả dữ liệu thành công. Hẹn gặp lại!");
    }
    
    // ---------------------------------------------------------------------
    // XỬ LÝ MENU CON (ĐÃ TRIỂN KHAI VÀ SỬA LỖI TÊN HÀM)
    // ---------------------------------------------------------------------

    // -handleStudentManagement(): void
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
                case 8: return;
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
        
        Student newStudent = new Student(id, fullName, major, email, data.enums.StudentStatus.ACTIVE); 
        
        if (studentManager.add(newStudent)) {
            System.out.println("Thêm sinh viên thành công: " + newStudent);
        } else {
            System.out.println("Thêm sinh viên thất bại.");
        }
    }
    
    // Các hàm xử lý nghiệp vụ đơn giản
    private void handleFindStudentById() {
        String id = Validator.getString("Nhập ID sinh viên cần tìm: ", "ID không được rỗng.");
        Student s = studentManager.findById(id);
        if (s != null) {
            System.out.println("Tìm thấy sinh viên: " + s);
        } else {
            System.out.println("Không tìm thấy sinh viên có ID: " + id);
        }
    }
    
    private void handleFindStudentByName() {
        String name = Validator.getString("Nhập tên (hoặc một phần tên) cần tìm: ", "Tên không được rỗng.");
        List<Student> results = studentManager.findByName(name);
        if (!results.isEmpty()) {
            System.out.println("=== KẾT QUẢ TÌM KIẾM THEO TÊN ===");
            results.forEach(System.out::println);
        } else {
            System.out.println("Không tìm thấy sinh viên nào có tên chứa: " + name);
        }
    }

    private void handleDeleteStudent() {
        String id = Validator.getString("Nhập ID sinh viên cần xóa: ", "ID không được rỗng.");
        if (studentManager.delete(id)) {
            System.out.println("Xóa sinh viên ID " + id + " thành công.");
        } else {
            System.out.println("Xóa sinh viên thất bại. (Không tìm thấy hoặc lỗi nội bộ)");
        }
    }
    
    private void handleUpdateStudent() {
        System.out.println("Tính năng cập nhật chưa được triển khai chi tiết.");
    }
    
    // -handleSubjectManagement(): void
    private void handleSubjectManagement() {
        System.out.println("Tính năng Quản lý Môn học đang được phát triển.");
        // Logic sẽ gọi Menu.showSubjectManagementMenu() và xử lý case
    }
    
    // -handleCourseManagement(): void
    private void handleCourseManagement() {
        System.out.println("Tính năng Quản lý Học phần đang được phát triển.");
        // Logic sẽ gọi Menu.showCourseManagementMenu() và xử lý case
    }
    
    // -handleRegistrationManagement(): void
    private void handleRegistrationManagement() {
        System.out.println("Tính năng Quản lý Đăng ký/Điểm đang được phát triển.");
        // Logic sẽ gọi Menu.showRegistrationManagementMenu() và xử lý case
    }

    private void handleReportMenu() {
        System.out.println("Tính năng Báo cáo/Thống kê đang được phát triển.");
        // Logic sẽ gọi Menu.showReportMenu() và xử lý case
    }
}