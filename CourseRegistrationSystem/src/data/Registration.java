package data;

import enums.RegistrationStatus;
import interfaces.Identifiable;
import interfaces.FileSerializable;

public class Registration implements Identifiable, FileSerializable {

    private String studentId; 
    private String courseSectionId; 
    private double grade; 
    private RegistrationStatus status; 

    public Registration() {
        this.status = RegistrationStatus.ENROLLED;
    }

    
    public Registration(String studentId, String courseSectionId, double grade, RegistrationStatus status) {
        this.studentId = studentId;
        this.courseSectionId = courseSectionId;
        this.grade = grade;
        this.status = status;
    }

    
    public String getStudentId() {
        return studentId;
    }

    public String getCourseSectionId() {
        return courseSectionId;
    }

    public double getGrade() {
        return grade;
    }

    public RegistrationStatus getStatus() {
        return status;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public void setStatus(RegistrationStatus status) {
        this.status = status;
    }

    
    public void updateStatusByGrade() { 
        if (grade >= 5.0) { 
            this.status = RegistrationStatus.PASSED;
        } else if (grade > 0 && grade < 5.0) {
            this.status = RegistrationStatus.FAILED;
        }
    
    }

    
    @Override
    public String getId() {
    
        return this.studentId + "_" + this.courseSectionId;
    }

    
    @Override
    public String toFileString() {
        return String.format("%s|%s|%.2f|%s",
                studentId, courseSectionId, grade, status.name());
    }

    @Override
    public String toString() { 
        return "Registration{"
                + "studentId='" + studentId + '\''
                + ", courseSectionId='" + courseSectionId + '\''
                + ", grade=" + grade
                + ", status=" + status
                + '}';
    }
}
