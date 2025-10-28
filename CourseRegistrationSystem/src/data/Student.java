package data;

import data.enums.StudentStatus;
import interfaces.Identifiable;
import interfaces.FileSerializable;

public class Student extends Person implements Identifiable, FileSerializable {
    private String studentId; // studentid [cite: 31]
    private String major;     // major [cite: 32]
    private StudentStatus status; // -status 

    // Constructor đầy đủ [cite: 36, 37, 38]
    public Student(String studentId, String fullName, String major, String email, StudentStatus status) {
        super(fullName, email); 
        this.studentId = studentId;
        this.major = major;
        this.status = status;
    }

    // Getters
    public String getStudentId() { return studentId; }
    public String getMajor() { return major; }
    public StudentStatus getStatus() { return status; }

    // Setters
    public void setMajor(String major) { this.major = major; }
    public void setStatus(StudentStatus status) { this.status = status; }

    // Thực hiện Identifiable [cite: 42]
    @Override
    public String getId() {
        return this.studentId;
    }

    // Thực hiện FileSerializable [cite: 41]
    @Override
    public String toFileString() {
        // studentId|fullName|major|email|status
        return String.format("%s|%s|%s|%s|%s",
            studentId, fullName, major, email, status.name());
    }

    @Override
    public String toString() { // [cite: 40]
        return "Student{" +
               "id='" + studentId + '\'' +
               ", name='" + fullName + '\'' +
               ", major='" + major + '\'' +
               ", status=" + status +
               '}';
    }
}