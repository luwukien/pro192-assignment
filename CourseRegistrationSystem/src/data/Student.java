package data;

import enums.StudentStatus;
import interfaces.FileSerializable;
import interfaces.Identifiable;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author vuhuy
 */
public class Student extends Person implements Identifiable, FileSerializable {
    private String studentId;
    private String major;
    private StudentStatus status;

    public Student() {}

    public Student(String studentId, String major, StudentStatus status) {
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
        return "Student{" + "studentId=" + studentId + ", major=" + major + ", status=" + status + '}';
    }



    @Override
    public String toFileString() { 
        return ""; 
    }

    @Override
    public String getId() { 
        return ""; 
    }
}
