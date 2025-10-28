package util;

import java.io.*;
import java.util.*;
import interfaces.FileSerializable;
import data.*; // Import tất cả các Model
import data.enums.*; // Import tất cả các Enum

public class FileHandler {
    // 4 Hằng số tên file (ReadOnly) [cite: 2]
    public static final String STUDENT_FILE = "students.txt"; 
    public static final String SUBJECT_FILE = "subjects.txt";
    public static final String COURSE_FILE = "courses.txt";
    public static final String REG_FILE = "registrations.txt";

    // --- CÁC HÀM GHI (SAVE) - SỬ DỤNG ĐA HÌNH ---
    // Hàm Generic dùng chung cho tất cả 4 List (Student, Subject, CourseSection, Registration)
    public <T extends FileSerializable> void saveDataToFile(List<T> list, String fileName) {
        // Ghi đè file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) { 
            for (T item : list) {
                // Đa Hình: item.toFileString() sẽ gọi đúng phương thức của lớp con
                bw.write(item.toFileString()); 
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("ERROR saving data to " + fileName + ": " + e.getMessage());
        }
    }
    
    // --- CÁC HÀM ĐỌC (LOAD) - CẦN PARSING CHI TIẾT ---
    
    // 1. loadStudents 
    public List<Student> loadStudents(String fileName) {
        List<Student> students = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|");
                // Format: id|fullName|major|email|status (5 parts)
                if (parts.length == 5) {
                    students.add(new Student(
                        parts[0], 
                        parts[1], 
                        parts[2], 
                        parts[3], 
                        StudentStatus.valueOf(parts[4]) // Parse Enum
                    ));
                }
            }
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("ERROR loading Students from " + fileName + ": " + e.getMessage());
        }
        return students;
    }

    // 2. loadSubjects 
    public List<Subject> loadSubjects(String fileName) {
        List<Subject> subjects = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|");
                // Format: id|name|credits|prereqs (4 parts)
                if (parts.length >= 3) {
                    Subject subject = new Subject(
                        parts[0], 
                        parts[1], 
                        Integer.parseInt(parts[2]) // Parse int
                    );
                    // Xử lý tiền quyết (Prerequisites)
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
    
    // 3. loadCourseSections 
    public List<CourseSection> loadCourseSections(String fileName) {
        List<CourseSection> sections = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|");
                // Format: courseId|subjectId|semester|maxStudents|currentCount|dayOfWeek|startSlot|endSlot (8 parts)
                if (parts.length == 8) {
                    sections.add(new CourseSection(
                        parts[0], 
                        parts[1], 
                        Integer.parseInt(parts[2]), // int semester
                        Integer.parseInt(parts[3]), // int maxStudents
                        Integer.parseInt(parts[4]), // int currentStudentCount
                        parts[5], 
                        Integer.parseInt(parts[6]), // int startSlot
                        Integer.parseInt(parts[7])  // int endSlot
                    ));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("ERROR loading CourseSections from " + fileName + ": " + e.getMessage());
        }
        return sections;
    }

    // 4. loadRegistrations [cite: 4, 5]
    public List<Registration> loadRegistrations(String fileName) {
        List<Registration> registrations = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|");
                // Format: studentId|courseSectionId|grade|status (4 parts)
                if (parts.length == 4) {
                    registrations.add(new Registration(
                        parts[0], 
                        parts[1], 
                        Double.parseDouble(parts[2]), // Parse double
                        RegistrationStatus.valueOf(parts[3]) // Parse Enum
                    ));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("ERROR loading Registrations from " + fileName + ": " + e.getMessage());
        }
        return registrations;
    }
}