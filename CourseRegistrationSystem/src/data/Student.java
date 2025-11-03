package data;

import enums.StudentStatus;
import interfaces.FileSerializable;
import interfaces.Identifiable;

public class Student extends Person implements Identifiable, FileSerializable {

    private String studentId;
    private String major;
    private StudentStatus status;

    public Student() {
    }

    public Student(String studentId, String fullName, String major, String email, StudentStatus status) {
        this.fullName = fullName;
        this.email = email;
        this.studentId = studentId;
        this.major = major;
        this.status = status;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public StudentStatus getStatus() {
        return status;
    }

    public void setStatus(StudentStatus status) {
        this.status = status;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public String toString() {
        return String.format("%s|%s|%s|%s|%s", studentId, fullName, major, email, status.name());
    }

    @Override
    public String toFileString() {
        return String.format("%s|%s|%s|%s|%s", studentId, fullName, major, email, status.name());
    }

    @Override
    public String getId() {
        return this.studentId;
    }
}
