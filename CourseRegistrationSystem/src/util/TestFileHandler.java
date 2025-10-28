package util; // Đặt ở thư mục gốc hoặc nơi bạn muốn

import java.util.*;
import util.FileHandler;
import data.*;
import data.enums.*;
import interfaces.FileSerializable;

public class TestFileHandler{

    private static final FileHandler handler = new FileHandler();

    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("      BẮT ĐẦU KIỂM THỬ FILE HANDLER      ");
        System.out.println("=========================================");
        
        // 1. Kiểm tra chức năng ĐỌC (LOAD)
        testLoadFunctions();
        
        // 2. Kiểm tra chức năng GHI (SAVE)
        testSaveFunctions();
        
        System.out.println("\n=========================================");
        System.out.println("      KIỂM THỬ HOÀN TẤT! 🎉             ");
        System.out.println("=========================================");
    }
    
    // --- 1. KIỂM TRA HÀM ĐỌC (LOAD) ---
    private static void testLoadFunctions() {
        System.out.println("\n--- 1. Kiểm tra chức năng LOAD ---");

        // Load Students
        List<Student> students = handler.loadStudents(FileHandler.STUDENT_FILE);
        System.out.printf("   - Loaded Students: %d. Kiểm tra SV001: %s\n", 
            students.size(), 
            students.size() > 0 ? students.get(0).getFullName() : "Lỗi đọc file!"
        );
        // Kiểm tra Enum parsing (SV002 nên là INACTIVE)
        if (students.size() >= 2) {
             System.out.printf("   - Trạng thái SV002: %s (Mong muốn: INACTIVE)\n", students.get(1).getStatus());
        }

        // Load Subjects
        List<Subject> subjects = handler.loadSubjects(FileHandler.SUBJECT_FILE);
        System.out.printf("   - Loaded Subjects: %d. Kiểm tra WEB301 Credits: %d\n", 
            subjects.size(), 
            subjects.size() >= 3 ? subjects.get(2).getCredits() : -1
        );
        // Kiểm tra Prerequisite parsing
        if (subjects.size() >= 3) {
             System.out.printf("   - WEB301 Prereqs: %s (Mong muốn: 2)\n", subjects.get(2).getPrerequisiteSubjectIds().size());
        }

        // Load Course Sections
        List<CourseSection> sections = handler.loadCourseSections(FileHandler.COURSE_FILE);
        System.out.printf("   - Loaded Course Sections: %d. Kiểm tra CS01 Max Students: %d\n", 
            sections.size(), 
            sections.size() > 0 ? sections.get(0).getMaxStudents() : -1
        );

        // Load Registrations
        List<Registration> regs = handler.loadRegistrations(FileHandler.REG_FILE);
        System.out.printf("   - Loaded Registrations: %d. Kiểm tra SV001|CS01 Grade: %.1f\n", 
            regs.size(), 
            regs.size() > 0 ? regs.get(0).getGrade() : -1.0
        );
        // Kiểm tra Double và Enum parsing
         if (regs.size() >= 4) {
             System.out.printf("   - Trạng thái Reg cuối (SV003): %s (Mong muốn: ENROLLED)\n", regs.get(3).getStatus());
        }
    }

    // --- 2. KIỂM TRA HÀM GHI (SAVE) ---
    private static void testSaveFunctions() {
        System.out.println("\n--- 2. Kiểm tra chức năng SAVE ---");
        
        // Bước 1: Load lại dữ liệu để đảm bảo không bị mất
        List<Student> students = handler.loadStudents(FileHandler.STUDENT_FILE);
        int initialSize = students.size();
        
        // Bước 2: Tạo một đối tượng mới để thêm vào danh sách
        Student newStudent = new Student(
            "SV099", 
            "Test Student Save", 
            "TEST", 
            "test@save.com", 
            StudentStatus.ACTIVE
        );
        students.add(newStudent);
        
        // Bước 3: Ghi dữ liệu mới (4 sinh viên) ra file
        handler.saveDataToFile(students, FileHandler.STUDENT_FILE);
        System.out.println("   - Đã lưu " + students.size() + " Students vào " + FileHandler.STUDENT_FILE + ".");
        
        // Bước 4: Tải lại file để kiểm tra xem dòng mới có được thêm vào không
        List<Student> studentsAfterSave = handler.loadStudents(FileHandler.STUDENT_FILE);
        
        System.out.printf("   - Sau khi SAVE, load lại file. Kích thước mới: %d (Mong muốn: %d)\n", 
            studentsAfterSave.size(), 
            initialSize + 1
        );
        
        // Bước 5: Kiểm tra xem sinh viên mới có tồn tại không
        Student savedNewStudent = studentsAfterSave.stream()
            .filter(s -> s.getId().equals("SV099"))
            .findFirst()
            .orElse(null);

        if (savedNewStudent != null) {
            System.out.println("   - THÀNH CÔNG: Tìm thấy Student SV099 đã được lưu!");
        } else {
            System.err.println("   - THẤT BẠI: Không tìm thấy Student SV099 sau khi lưu.");
        }
        
        // Quan trọng: Khôi phục file gốc (xóa SV099)
        students.remove(students.size() - 1);
        handler.saveDataToFile(students, FileHandler.STUDENT_FILE);
        System.out.println("   - Đã khôi phục file " + FileHandler.STUDENT_FILE + " về kích thước ban đầu.");
    }
}