package courseregistrationsystem;

import data.Student;
import data.Subject;
import data.CourseSection;
import data.Registration;
import enums.StudentStatus;
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
                default: System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 6);
    }

    /**
     * Khởi tạo các Manager: Đã sửa lỗi phụ thuộc vòng bằng cách dùng setter.
     */
    private void initializeManagers() {
        System.out.println("--- INITIALIZING SYSTEM ---");

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
        
        /// TRuyền vào một tham số thôi 
        /*
        registrationManager = new RegistrationManager(
            initialRegistrations,
            courseManager,
            subjectManager
        );
        */
        
        // 4. Khởi tạo StudentManager (Cần RegistrationManager)
        /*
        studentManager = new StudentManager(
            initialStudents,
            registrationManager
        );
        */
        
        // 5. Gán StudentManager đã khởi tạo cho RegistrationManager (GIẢI QUYẾT PHỤ THUỘC VÒNG)
        // YÊU CẦU: Lớp RegistrationManager phải có public void setStudentManager(StudentManager sm)
        // Đây là bước quan trọng nhất để fix lỗi! 
        // làm gì có cái hàm nào seetStudentManager??
        /*
        registrationManager.setStudentManager(studentManager);
        */
        
        System.out.println("System initialized successfully.");
        System.out.println("---------------------------");
    }

    // +handleSaveData(): void
    public void handleSaveData() {
        System.out.println("--- SAVING DATA AND EXITING ---");

        // Lưu dữ liệu (sử dụng đối tượng fileHandler)
        FileHandler.saveDataToFile(studentManager.getAll(), FileHandler.STUDENT_FILE);
        FileHandler.saveDataToFile(subjectManager.getAll(), FileHandler.SUBJECT_FILE);
        FileHandler.saveDataToFile(courseManager.getAll(), FileHandler.COURSE_FILE);
        FileHandler.saveDataToFile(registrationManager.getAll(), FileHandler.REG_FILE);

        System.out.println("All data saved successfully. See you later!");
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
                default: System.out.println("Invalid choice.");
            }
        } while (choice != 8);
    }

    private void handleAddStudent() {
        System.out.println("\n--- ADD NEW STUDENT ---");
        String id;
        do {
            id = Validator.getString("Enter Student ID (e.g., S1001): ", "Invalid or empty ID.", "^[sS]\\d{4}$");
            if (studentManager.findById(id) != null) {
                System.out.println("Error: Student ID already exists. Please enter a different ID.");
            } else { break; }
        } while (true);

        String fullName = Validator.getString("Enter Full Name: ", "Name cannot be empty.");
        String major = Validator.getString("Enter Major: ", "Major cannot be empty.");
        String email = Validator.getString("Enter Email: ", "Invalid email format.", "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");

        // Tạo đối tượng Student (5 tham số)
        data.Student newStudent = new data.Student(id, fullName, major, email, enums.StudentStatus.ACTIVE);

        if (studentManager.add(newStudent)) {
            System.out.println("Student added successfully: " + newStudent);
        } else {
            System.out.println("Failed to add student.");
        }
    }
    private void handleFindStudentById() {
        System.out.println("\n--- FIND STUDENT BY ID ---");
        String id = Validator.getString("Enter Student ID to find: ", "ID cannot be empty.");
        data.Student s = studentManager.findById(id);
        if (s != null) {
            System.out.println("Found student: " + s);
        } else {
            System.out.println("Student not found with ID: " + id);
        }
    }
    private void handleFindStudentByName() {
        System.out.println("\n--- FIND STUDENT BY NAME ---");
        String name = Validator.getString("Enter name (or partial name) to find: ", "Name cannot be empty.");
        List<data.Student> results = studentManager.findByName(name);
        if (!results.isEmpty()) {
            System.out.println("=== SEARCH RESULTS BY NAME ===");
            results.forEach(System.out::println);
        } else {
            System.out.println("No student found with name containing: " + name);
        }
    }
    //SỬA
    /*
    private void handleDeleteStudent() {
        System.out.println("\n--- DELETE STUDENT ---");
        String id = Validator.getString("Enter Student ID to delete: ", "ID cannot be empty.");
        if (studentManager.delete(id)) { // Dùng hàm delete(String id) từ Management
            System.out.println("Student ID " + id + " deleted successfully.");
        } else {
            System.out.println("Failed to delete student.");
        }
    }
    */
    private void handleUpdateStudent() {
        System.out.println("\n--- UPDATE STUDENT INFORMATION ---");
        String id = Validator.getString("Enter Student ID to update: ", "ID cannot be empty.");
        data.Student studentToUpdate = studentManager.findById(id);

        if (studentToUpdate == null) {
            System.out.println("Error: Student not found with ID: " + id);
            return;
        }

        System.out.println("--- Updating: " + studentToUpdate.getFullName() + " ---");

        // Nhập thông tin mới (sử dụng Enter để giữ nguyên)
        String newFullName = Validator.getString(
            "Enter new Full Name (Press Enter to keep '" + studentToUpdate.getFullName() + "'): ", null
        );
        if (!newFullName.isEmpty()) { studentToUpdate.setFullName(newFullName); }

        String newMajor = Validator.getString(
            "Enter new Major (Press Enter to keep '" + studentToUpdate.getMajor() + "'): ", null
        );
        if (!newMajor.isEmpty()) { studentToUpdate.setMajor(newMajor); }

        String newEmail = Validator.getString(
            "Enter new Email (Press Enter to keep '" + studentToUpdate.getEmail() + "'): ", null
        );
        if (!newEmail.isEmpty()) { studentToUpdate.setEmail(newEmail); }

        // Gọi hàm update() từ Management
        if (studentManager.update(studentToUpdate)) {
            System.out.println("Student ID " + id + " updated successfully!");
        } else {
            System.out.println("Update failed.");
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
                default: System.out.println("Invalid choice.");
            }
        } while (choice != 6);
    }
    private void handleAddSubject() {
        System.out.println("\n--- ADD NEW SUBJECT ---");
        String id;
        do {
            id = Validator.getString("Enter Subject ID (e.g., IT101): ", "ID cannot be empty.", "^[a-zA-Z]{2,4}\\d{3}$");
            if (subjectManager.findById(id) != null) {
                System.out.println("Error: Subject ID already exists. Please enter a different ID.");
            } else { break; }
        } while (true);

        String name = Validator.getString("Enter Subject Name: ", "Name cannot be empty.");
        int credits = Validator.getInt("Enter Credits (1-10): ", "Credits must be an integer.", 1, 10);

        data.Subject newSubject = new data.Subject(id, name, credits);

        // Xử lý Tiên quyết (Prerequisite)
        boolean addMore;
        do {
            addMore = Validator.getYesNo("Do you want to add a prerequisite? (Y/N): ", "Please enter Y or N.");
            if (addMore) {
                String preId = Validator.getString("Enter prerequisite Subject ID: ", "ID cannot be empty.");
                newSubject.addPrerequisite(preId);
            }
        } while (addMore);

        if (subjectManager.add(newSubject)) {
            System.out.println("Subject added successfully: " + newSubject);
        } else {
            System.out.println("Failed to add subject.");
        }
    }
    private void handleFindSubjectById() {
        System.out.println("\n--- FIND SUBJECT BY ID ---");
        String id = Validator.getString("Enter Subject ID to find: ", "ID cannot be empty.");
        data.Subject s = subjectManager.findById(id);
        if (s != null) {
            System.out.println("Found subject: " + s);
            System.out.println("  Prerequisites: " + s.getPrerequisiteSubjectIds());
        } else {
            System.out.println("Subject not found with ID: " + id);
        }
    }
    private void handleUpdateSubject() {
        System.out.println("\n--- UPDATE SUBJECT INFORMATION ---");
        String id = Validator.getString("Enter Subject ID to update: ", "ID cannot be empty.");

        // Tìm kiếm môn học
        data.Subject subjectToUpdate = subjectManager.findById(id);

        if (subjectToUpdate == null) {
            System.out.println("Error: Subject not found with ID: " + id);
            return;
        }

        System.out.println("--- Updating: " + subjectToUpdate.getSubjectName() + " ---");

        // 1. Cập nhật Tên môn học
        String currentName = subjectToUpdate.getSubjectName();
        String newName = Validator.getString(
            "Enter new Subject Name (Press Enter to keep '" + currentName + "'): ",
            null
        );
        if (!newName.isEmpty()) {
            subjectToUpdate.setSubjectName(newName);
        }

        // 2. Cập nhật Số tín chỉ
        int currentCredits = subjectToUpdate.getCredit();
        System.out.println("Current credits: " + currentCredits);

        // Lấy input chuỗi và parse thủ công để cho phép người dùng nhập rỗng (giữ nguyên)
        String newCreditsStr = Validator.getString(
            "Enter new Credits (1-10, Press Enter to keep): ",
            null
        );

        if (!newCreditsStr.isEmpty()) {
            try {
                int newCredits = Integer.parseInt(newCreditsStr);
                if (newCredits >= 1 && newCredits <= 10) {
                    subjectToUpdate.setCredit(newCredits);
                } else {
                    System.out.println("Warning: Credits must be between 1-10. Keeping old value.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Format error. Keeping old value.");
            }
        }

        // 3. Gọi hàm update() từ Management
        if (subjectManager.update(subjectToUpdate)) {
            System.out.println("Subject ID " + id + " updated successfully!");
        } else {
            // Lỗi đã được in ra trong Management.update() nếu ID không được tìm thấy
            System.out.println("Update failed.");
        }
    }
    
    //SỬA
    /*
    private void handleDeleteSubject() {
        String id = Validator.getString("Enter Subject ID to delete: ", "ID cannot be empty.");
        if (subjectManager.delete(id)) {
            System.out.println("Subject ID " + id + " deleted successfully.");
        } else {
            System.out.println("Failed to delete subject.");
        }
    }
    */

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
                default: System.out.println("Invalid choice.");
            }
        } while (choice != 7);
    }
    
    //SỬA
    /*
    private void handleAddCourseSection() {
        System.out.println("\n--- ADD NEW COURSE SECTION ---");
        String subjectId;

        // 1. Kiểm tra Subject ID có tồn tại không
        do {
            subjectId = Validator.getString("Enter Subject ID for this course section: ", "ID cannot be empty.");
            if (subjectManager.findById(subjectId) == null) {
                System.out.println("Error: Subject with ID " + subjectId + " does not exist. Please add the subject first.");
            } else { break; }
        } while (true);

        // 2. Nhập các trường còn lại
        String courseSectionId = Validator.getString("Enter Course Section ID (e.g., IT101-L1): ", "ID cannot be empty.");
        int semester = Validator.getInt("Enter Semester (1-10): ", "Semester must be an integer.", 1, 10);
        int maxStudents = Validator.getInt("Enter Max Students (10-100): ", "Student count must be valid.", 10, 100);
        String dayOfWeek = Validator.getString("Enter Day of Week (e.g., Mon, Tue): ", "Invalid day of week.");
        int startSlot = Validator.getInt("Enter Start Slot (1-10): ", "Slot must be valid.", 1, 10);
        int endSlot = Validator.getInt("Enter End Slot (1-10): ", "Slot must be valid.", startSlot, 10);

        // 3. Tạo đối tượng và thêm vào Manager
        data.CourseSection newCS = new data.CourseSection(
            courseSectionId, subjectId, semester, maxStudents,
            0, // currentStudentCount = 0
            dayOfWeek, startSlot, endSlot
        );

        if (courseManager.add(newCS)) {
            System.out.println("Course section added successfully: " + newCS);
        } else {
            System.out.println("Failed to add course section (ID might already exist).");
        }
    }

    */
    
    private void handleFindCourseSectionById() {
        System.out.println("\n--- FIND COURSE SECTION BY ID ---");
        String id = Validator.getString("Enter Course Section ID to find: ", "ID cannot be empty.");
        data.CourseSection cs = courseManager.findById(id);
        if (cs != null) {
            System.out.println("Found course section: " + cs);
            System.out.println("  Status: " + cs.getCurrentStudentCount() + "/" + cs.getMaxStudents());
        } else {
            System.out.println("Course section not found with ID: " + id);
        }
    }
    
    //SỬA
    /*
    private void handleFindCourseSectionBySubjectId() {
        System.out.println("\n--- FIND COURSE SECTION BY SUBJECT ID ---");
        String id = Validator.getString("Enter Subject ID to find: ", "ID cannot be empty.");
        // Giả sử bạn giữ lại hàm findBySubjectId trong CourseManager
        List<data.CourseSection> results = registrationManager.findBySubjectId(id);

        if (!results.isEmpty()) {
            System.out.println("=== SEARCH RESULTS FOR SUBJECT " + id + " ===");
            results.forEach(cs -> System.out.println("  " + cs));
        } else {
            System.out.println("No course sections found for Subject ID: " + id);
        }
    }
    */
    
    private void handleUpdateCourseSection() {
        System.out.println("\n--- UPDATE COURSE SECTION INFORMATION ---");
        String id = Validator.getString("Enter Course Section ID to update: ", "ID cannot be empty.");

        CourseSection csToUpdate = courseManager.findById(id);

        if (csToUpdate == null) {
            System.out.println("Error: Course section not found with ID: " + id);
            return;
        }

        System.out.println("--- Updating: " + csToUpdate.getCourseSectionId() + " ---");

        // Yêu cầu nhập Max Students mới
        System.out.println("Current Max Students: " + csToUpdate.getMaxStudents());
        // Chỉ nhập nếu người dùng không nhập rỗng (Validator cần được chỉnh sửa để hỗ trợ nhập số và chuỗi rỗng)
        String maxStr = Validator.getString("Enter new Max Students (Press Enter to keep): ", null);
        if (!maxStr.isEmpty()) {
            try {
                int newMax = Integer.parseInt(maxStr);
                if (newMax >= csToUpdate.getCurrentStudentCount() && newMax > 0) {
                    // Đảm bảo số lượng tối đa mới không nhỏ hơn số lượng hiện tại
                    csToUpdate.setMaxStudents(newMax);
                } else {
                    System.out.println("Warning: Max Students must be greater than current count and > 0. Keeping old value.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Format error. Keeping old value.");
            }
        }

        // Cập nhật Ngày học
        String newDay = Validator.getString("Enter new Day of Week (Press Enter to keep): ", null);
        if (!newDay.isEmpty()) {
            // csToUpdate.setDayOfWeek(newDay); // Giả định có setter cho dayOfWeek
        }

        // Gọi hàm update() từ Management
        if (courseManager.update(csToUpdate)) {
            System.out.println("Course section ID " + id + " updated successfully!");
        } else {
            System.out.println("Update failed.");
        }
    }
    
    //SỬA
    /*
    private void handleDeleteCourseSection() {
        String id = Validator.getString("Enter Course Section ID to delete: ", "ID cannot be empty.");
        if (courseManager.delete(id)) {
            System.out.println("Course section ID " + id + " deleted successfully.");
        } else {
            System.out.println("Failed to delete course section.");
        }
    }
    */

    private void handleRegistrationManagement() {
        int choice;
        do {
            choice = Menu.showRegistrationManagementMenu();
            switch(choice) {
                case 1: handleRegisterForStudent(); break; // Đăng ký
                case 2: handleWithdrawCourse(); break;     // Hủy đăng ký
                case 3: handleInputGrade(); break;         // Nhập điểm
                case 4: handleViewStudentRegistration(); break; // Xem đăng ký của SV
                case 5: handleViewCourseStudents(); break;       // Xem SV của học phần
                case 6: return; // Quay lại Menu chính
                default: System.out.println("Invalid choice.");
            }
        } while (choice != 6);

    }
    private void handleRegisterForStudent() {
        System.out.println("\n--- REGISTER FOR COURSE ---");

        // Yêu cầu nhập ID sinh viên và ID học phần
        String studentId = Validator.getString("Enter Student ID: ", "ID cannot be empty.", "^[sS]\\d{4}$");
        String courseSectionId = Validator.getString("Enter Course Section ID (e.g., IT101-L1): ", "ID cannot be empty.");

        // Gọi hàm nghiệp vụ phức tạp
        registrationManager.registerCourse(studentId, courseSectionId);
    }
    private void handleWithdrawCourse() {
        System.out.println("\n--- WITHDRAW FROM COURSE ---");

        String studentId = Validator.getString("Enter Student ID to withdraw: ", "ID cannot be empty.", "^[sS]\\d{4}$");
        String courseSectionId = Validator.getString("Enter Course Section ID to withdraw: ", "ID cannot be empty.");

        // Gọi hàm nghiệp vụ
        registrationManager.withdrawCourse(studentId, courseSectionId);
    }
    private void handleInputGrade() {
        System.out.println("\n--- INPUT GRADE AND UPDATE STATUS ---");

        String studentId = Validator.getString("Enter Student ID: ", "ID cannot be empty.", "^[sS]\\d{44}$");
        String courseSectionId = Validator.getString("Enter Course Section ID: ", "ID cannot be empty.");

        String regId = studentId + "_" + courseSectionId;
        Registration reg = registrationManager.findById(regId);

        if (reg == null) {
            System.out.println("Error: Registration record not found for this course section.");
            return;
        }

        // Kiểm tra xem môn học đã có điểm hay đã kết thúc chưa
        if (reg.getStatus() == enums.RegistrationStatus.PASSED ||
            reg.getStatus() == enums.RegistrationStatus.FAILED) {
            System.out.println("Warning: This course section already has a grade (Status: " + reg.getStatus() + ").");
        }

        // Nhập điểm mới (giả định thang điểm 0-10)
        double grade = Validator.getDouble("Enter new Grade (0.0 - 10.0): ", "Invalid grade.", 0.0, 10.0);

        // Cập nhật điểm và trạng thái
        reg.setGrade(grade);
        reg.updateStatusByGrade(); // Logic nghiệp vụ nhỏ trong lớp Registration

        if (registrationManager.update(reg)) {
            System.out.println("Grade updated successfully. New status: " + reg.getStatus());
        } else {
            System.out.println("Error: Failed to update grade.");
        }
    }
    private void handleViewStudentRegistration() {
        System.out.println("\n--- VIEW REGISTRATIONS BY STUDENT ---");
        String studentId = Validator.getString("Enter Student ID: ", "ID cannot be empty.", "^[sS]\\d{4}$");

        List<Registration> list = registrationManager.getRegistrationsByStudent(studentId);

        if (list.isEmpty()) {
            System.out.println("Student " + studentId + " has not registered for any courses.");
        } else {
            System.out.println("=== REGISTRATION LIST FOR STUDENT " + studentId + " ===");
            list.forEach(System.out::println);
        }
    }
    private void handleViewCourseStudents() {
        System.out.println("\n--- VIEW STUDENT LIST BY COURSE SECTION ---");
        String courseId = Validator.getString("Enter Course Section ID: ", "ID cannot be empty.");

        List<Registration> list = registrationManager.getRegistrationsByCourse(courseId);

        if (list.isEmpty()) {
            System.out.println("Course section " + courseId + " has no registered students.");
        } else {
            System.out.println("=== STUDENT LIST FOR COURSE SECTION " + courseId + " ===");
            list.forEach(r -> System.out.println("  - Student ID: " + r.getStudentId() + ", Status: " + r.getStatus()));
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
                default: System.out.println("Invalid choice.");
            }
        } while (choice != 3);
    }
    private void handleCalculateOverallGPA() {
        System.out.println("\n--- CALCULATE OVERALL GPA ---");

        // Yêu cầu nhập ID sinh viên và kiểm tra tồn tại
        String studentId = Validator.getString("Enter Student ID: ", "ID cannot be empty.", "^[sS]\\d{4}$");

        Student student = studentManager.findById(studentId);
        if (student == null) {
            System.out.println("Error: Student not found with ID: " + studentId);
            return;
        }

        // Gọi hàm nghiệp vụ tính GPA
        double gpa = registrationManager.calculateOverallGPA(studentId);

        System.out.println("-------------------------------------------");
        System.out.printf("| Overall GPA for %s is: %.2f\n", student.getFullName(), gpa);
        System.out.println("-------------------------------------------");
    }
    private void handleCalculateSemesterGPA() {
        System.out.println("\n--- CALCULATE SEMESTER GPA ---");

        // 1. Nhập ID sinh viên và kiểm tra tồn tại
        String studentId = Validator.getString("Enter Student ID: ", "ID cannot be empty.", "^[sS]\\d{4}$");
        Student student = studentManager.findById(studentId);
        if (student == null) {
            System.out.println("Error: Student not found with ID: " + studentId);
            return;
        }

        // 2. Nhập học kỳ
        int semester = Validator.getInt("Enter Semester (1-10) to calculate: ", "Semester must be a valid integer.", 1, 10);

        // 3. Gọi hàm nghiệp vụ tính GPA theo học kỳ
        double semGpa = registrationManager.calculateSemesterGPA(studentId, semester);

        System.out.println("-------------------------------------------");
        System.out.printf("| GPA for Semester %d for %s is: %.2f\n", semester, student.getFullName(), semGpa);
        System.out.println("-------------------------------------------");
    }
}