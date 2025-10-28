package data;

import interfaces.Identifiable;
import interfaces.FileSerializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Subject implements Identifiable, FileSerializable {
    private String subjectId; // -subjectid [cite: 43]
    private String subjectName; // subjectName [cite: 45]
    private int credits; // -credits [cite: 46]
    private List<String> prerequisiteSubjectIds; // prerequisiteSubjectids [cite: 47]

    public Subject(String subjectId, String subjectName, int credits) { // [cite: 49, 50]
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.credits = credits;
        this.prerequisiteSubjectIds = new ArrayList<>();
    }
    
    // Getters/Setters [cite: 51]
    public String getSubjectId() { return subjectId; }
    public String getSubjectName() { return subjectName; }
    public int getCredits() { return credits; }
    public List<String> getPrerequisiteSubjectIds() { return prerequisiteSubjectIds; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }
    public void setCredits(int credits) { this.credits = credits; }

    // Logic nghiệp vụ nhỏ
    public void addPrerequisite(String subjectId) { // [cite: 52]
        if (!prerequisiteSubjectIds.contains(subjectId)) {
            prerequisiteSubjectIds.add(subjectId);
        }
    }

    // Thực hiện Identifiable [cite: 55]
    @Override
    public String getId() {
        return this.subjectId;
    }

    // Thực hiện FileSerializable [cite: 54]
    @Override
    public String toFileString() {
        // subjectId|subjectName|credits|prerequisite1,prerequisite2,...
        String prereqString = prerequisiteSubjectIds.isEmpty() ? "" : 
                              prerequisiteSubjectIds.stream().collect(Collectors.joining(","));
        return String.format("%s|%s|%d|%s",
            subjectId, subjectName, credits, prereqString);
    }
    
    @Override
    public String toString() { // [cite: 53]
        return "Subject{" +
               "id='" + subjectId + '\'' +
               ", name='" + subjectName + '\'' +
               ", credits=" + credits +
               '}';
    }
}