package data;

import interfaces.FileSerializable;
import interfaces.Identifiable;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author vuhuy
 */
public class Subject implements Identifiable, FileSerializable {
    private String subjectId;
    private String subjectName;
    private int credit;
    private List<String> prerequisiteSubjectIds;
    

    public Subject() {
    }

    public Subject(String subjectId, String subjectName, int credit) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.credit = credit;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }
    public void addPrerequisite(String subjectId){
        prerequisiteSubjectIds.add(subjectId);
    }
            
    @Override
    public String toString() {
        return "Subject{" + "subjectId=" + subjectId + ", subjectName=" + subjectName + ", credit=" + credit + '}';
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