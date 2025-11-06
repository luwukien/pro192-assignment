package data;

import interfaces.FileSerializable;
import interfaces.Identifiable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        this.prerequisiteSubjectIds = new ArrayList<>();
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

    public List<String> getPrerequisiteSubjectIds() {
        return prerequisiteSubjectIds;
    }

    public void setPrerequisiteSubjectIds(List<String> prerequisiteSubjectIds) {
        this.prerequisiteSubjectIds = prerequisiteSubjectIds;
    }

    public void addPrerequisite(String subjectId) {
        if (!prerequisiteSubjectIds.contains(subjectId)) {
            prerequisiteSubjectIds.add(subjectId);
        }
    }

    @Override
    public String getId() {
        return this.subjectId;
    }

    @Override
    public String toFileString() {
        String prereqString = prerequisiteSubjectIds.isEmpty() ? "" : 
                              prerequisiteSubjectIds.stream().collect(Collectors.joining(","));
        return String.format("%s|%s|%d|%s",
            subjectId, subjectName, credit, prereqString);
    }
    
    @Override
    public String toString() { 
        String prereqString = prerequisiteSubjectIds.isEmpty() ? "" : 
                              prerequisiteSubjectIds.stream().collect(Collectors.joining(","));
        return String.format("%s|%s|%d|%s",
            subjectId, subjectName, credit, prereqString);
    }
}