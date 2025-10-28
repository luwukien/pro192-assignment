package data;

import data.enums.RegistrationStatus;
import interfaces.Identifiable;
import interfaces.FileSerializable;

public class Registration implements Identifiable, FileSerializable {
    private String studentId; // studentid [cite: 78]
    private String courseSectionId; // courseSectionid [cite: 79]
    private double grade; // -grade [cite: 80]
    private RegistrationStatus status; // status [cite: 81]

    public Registration() { // [cite: 84]
        this.status = RegistrationStatus.ENROLLED;
    }

    // Constructor đầy đủ [cite: 85, 86, 87]
    public Registration(String studentId, String courseSectionId, double grade, RegistrationStatus status) {
        this.studentId = studentId;
        this.courseSectionId = courseSectionId;
        this.grade = grade;
        this.status = status;
    }

    // Getters/Setters [cite: 88]
    public String getStudentId() { return studentId; }
    public String getCourseSectionId() { return courseSectionId; }
    public double getGrade() { return grade; }
    public RegistrationStatus getStatus() { return status; }
    
    public void setGrade(double grade) { this.grade = grade; }
    public void setStatus(RegistrationStatus status) { this.status = status; }
    
    // Logic nghiệp vụ nhỏ
    public void updateStatusByGrade() { // [cite: 89]
        if (grade >= 5.0) { // Giả định qua môn là 5.0
            this.status = RegistrationStatus.PASSED;
        } else if (grade > 0 && grade < 5.0) {
            this.status = RegistrationStatus.FAILED;
        }
        // ENROLLED và WITHDRAWN sẽ được set qua nghiệp vụ khác
    }

    // Thực hiện Identifiable [cite: 92]
    @Override
    public String getId() {
        // ID của Registration thường là sự kết hợp của 2 khóa ngoại
        return this.studentId + "_" + this.courseSectionId;
    }

    // Thực hiện FileSerializable [cite: 91]
    @Override
    public String toFileString() {
        // studentId|courseSectionId|grade|status
        return String.format("%s|%s|%.2f|%s",
            studentId, courseSectionId, grade, status.name());
    }
    
    @Override
    public String toString() { // [cite: 90]
        return "Registration{" +
               "studentId='" + studentId + '\'' +
               ", courseSectionId='" + courseSectionId + '\'' +
               ", grade=" + grade +
               ", status=" + status +
               '}';
    }
}