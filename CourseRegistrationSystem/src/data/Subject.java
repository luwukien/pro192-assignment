package data;

import interfaces.Identifiable;
import interfaces.FileSerializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Subject implements Identifiable, FileSerializable {
    private String subjectId; 
    private String subjectName;
    private int credits;
    private List<String> prerequisiteSubjectIds; 

    public Subject() {
    }

    public Subject(String subjectId, String subjectName, int credits) { 
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.credits = credits;
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

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
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
            subjectId, subjectName, credits, prereqString);
    }
    
    @Override
    public String toString() { 
        return "Subject{" +
               "id='" + subjectId + '\'' +
               ", name='" + subjectName + '\'' +
               ", credits=" + credits +
               '}';
    }
}