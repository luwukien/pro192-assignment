package util;

import java.io.*;
import java.util.*;
import interfaces.FileSerializable;
import data.*; // Import tất cả các Model
import enums.*; // Import tất cả các Enum

public class FileHandler {
    // 4 Hằng số tên file (ReadOnly)
    public static final String STUDENT_FILE = "students.txt"; 
    public static final String SUBJECT_FILE = "subjects.txt";
    public static final String COURSE_FILE = "courses.txt";
    public static final String REG_FILE = "registrations.txt";


    public static <T extends FileSerializable> void saveDataToFile(List<T> list, String fileName) {
        // Ghi đè file
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
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|");
                // LỖI: Chỉ có 5 tham số được cung cấp, nhưng Student constructor cần 6
                if (parts.length == 5) {
                    students.add(new Student(
                        parts[0], 
                        parts[1], 
                        parts[2], 
                        parts[3], 
                        // THIẾU parts[4] (email)
                        StudentStatus.valueOf(parts[4]) // Lỗi: Vị trí của status sai
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
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|");
                if (parts.length >= 3) {
                    Subject subject = new Subject(
                        parts[0], 
                        parts[1], 
                        Integer.parseInt(parts[2])
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
    
    public static List<CourseSection> loadCourseSections(String fileName) {
        List<CourseSection> sections = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|");
                if (parts.length == 8) {
                    sections.add(new CourseSection(
                        parts[0], 
                        parts[1], 
                        Integer.parseInt(parts[2]), // int semester
                        Integer.parseInt(parts[3]), // int maxStudents
                        Integer.parseInt(parts[4]), // int currentStudentCount
                        DayOfWeek.valueOf(parts[5]), //value of dayofweek
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

    // 4. loadRegistrations 
    public List<Registration> loadRegistrations(String fileName) {
        List<Registration> registrations = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|");
                if (parts.length == 4) {
                    registrations.add(new Registration(
                        parts[0], 
                        parts[1], 
                        Double.parseDouble(parts[2]), 
                        RegistrationStatus.valueOf(parts[3]) // Có thể lỗi nếu status không viết hoa
                    ));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("ERROR loading Registrations from " + fileName + ": " + e.getMessage());
        }
        return registrations;
    }
}