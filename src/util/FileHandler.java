package util;

import java.io.*;
import java.util.*;
import interfaces.FileSerializable;
import data.*;
import enums.*;

public class FileHandler {

    public static final String STUDENT_FILE = "students.txt";
    public static final String SUBJECT_FILE = "subjects.txt";
    public static final String COURSE_FILE = "courses.txt";
    public static final String REG_FILE = "registrations.txt";

    public <T extends FileSerializable> void saveDataToFile(List<T> list, String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (T item : list) {
                bw.write(item.toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("ERROR saving data to " + fileName + ": " + e.getMessage());
        }
    }

    public static List<Student> loadStudents(String fileName) {
        List<Student> students = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    students.add(new Student(
                            parts[0],
                            parts[1],
                            parts[2],
                            parts[3],
                            StudentStatus.valueOf(parts[4])
                    ));
                }
            }
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("ERROR loading Students from " + fileName + ": " + e.getMessage());
        }
        return students;
    }

    public static List<Subject> loadSubjects(String fileName) {
        List<Subject> subjects = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split("\\|");
                if (parts.length >= 3) {
                    Subject subject = new Subject(
                            parts[0],
                            parts[1],
                            Integer.parseInt(parts[2])
                    );
                    if (parts.length == 4 && !parts[3].isEmpty()) {
                        String[] prereqs = parts[3].split(",");
                        for (String reqId : prereqs) {
                            subject.addPrerequisite(reqId.trim());
                        }
                    }
                    subjects.add(subject);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("ERROR loading Subjects from " + fileName + ": " + e.getMessage());
        }
        return subjects;
    }

    public static List<CourseSection> loadCourseSections(String fileName) {
        List<CourseSection> sections = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split("\\|");

                if (parts.length == 8) {
                    sections.add(new CourseSection(
                            parts[0],
                            parts[1],
                            Integer.parseInt(parts[2]),
                            Integer.parseInt(parts[3]),
                            Integer.parseInt(parts[4]),
                            DayOfWeek.valueOf(parts[5]),
                            Integer.parseInt(parts[6]),
                            Integer.parseInt(parts[7])
                    ));
                }
            }
        } catch (Exception e) {
            System.err.println("ERROR loading CourseSections from " + fileName + ": " + e.getMessage());
        }
        return sections;
    }

    // 4. loadRegistrations 
    public static List<Registration> loadRegistrations(String fileName) {
        List<Registration> registrations = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split("\\|");
                if (parts.length == 4) {
                    registrations.add(new Registration(
                            parts[0],
                            parts[1],
                            Double.parseDouble(parts[2]),
                            RegistrationStatus.valueOf(parts[3])
                    ));
                }
            }
        } catch (Exception e) {
            System.err.println("ERROR loading Registrations from " + fileName + ": " + e.getMessage());
        }
        return registrations;
    }
    /*
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
                subjects.size() >= 3 ? subjects.get(2).getCredit() : -1
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
    */
    
}
