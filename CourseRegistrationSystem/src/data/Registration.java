package data;

import interfaces.FileSerializable;
import interfaces.Identifiable;
import enums.RegistrationStatus;

public class Registration implements Identifiable, FileSerializable {

    private String studentId;
    private String courseSectionId;
    private double grade;
    private RegistrationStatus status;

    // Constructors
    public Registration() {
    }

    public Registration(String studentId, String courseSectionId, double grade, RegistrationStatus status) {
        this.studentId = studentId;
        this.courseSectionId = courseSectionId;
        this.grade = grade;
        this.status = status;
    }

    // Getters & Setters
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseSectionId() {
        return courseSectionId;
    }

    public void setCourseSectionId(String courseSectionId) {
        this.courseSectionId = courseSectionId;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public RegistrationStatus getStatus() {
        return status;
    }

    public void setStatus(RegistrationStatus status) {
        this.status = status;
    }

    public void updateStatusByGrade() {
        if (grade >= 5.0) {
            status = RegistrationStatus.PASSED;
        } else if (grade < 5.0 && grade >= 0) {
            status = RegistrationStatus.FAILED;
        }
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

    @Override
    public String toFileString() {
        return studentId + "," + courseSectionId + "," + grade + "," + status;
    }

    @Override
    public String getId() {
        return studentId + "_" + courseSectionId;
    }
}
