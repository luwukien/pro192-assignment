package data;

import interfaces.Identifiable;
import interfaces.FileSerializable;

public class CourseSection implements Identifiable, FileSerializable {
    private String courseSectionId; // -courseSectionid [cite: 57]
    private String subjectId; // subjectid [cite: 58]
    private int semester; // -semester [cite: 59]
    private int maxStudents; // maxStudents [cite: 60]
    private int currentStudentCount; // -currentStudentCount [cite: 61]
    private String dayOfWeek; // -dayOfWeek [cite: 62]
    private int startSlot; // -startSlot [cite: 63]
    private int endSlot; // endSlot [cite: 64]

    // Constructor đầy đủ [cite: 66, 67, 68, 69]
    public CourseSection(String courseSectionId, String subjectId, int semester, int maxStudents,
                         int currentStudentCount, String dayOfWeek, int startSlot, int endSlot) {
        this.courseSectionId = courseSectionId;
        this.subjectId = subjectId;
        this.semester = semester;
        this.maxStudents = maxStudents;
        this.currentStudentCount = currentStudentCount;
        this.dayOfWeek = dayOfWeek;
        this.startSlot = startSlot;
        this.endSlot = endSlot;
    }

    // Getters/Setters [cite: 70]
    public String getCourseSectionId() { return courseSectionId; }
    public String getSubjectId() { return subjectId; }
    public int getSemester() { return semester; }
    public int getMaxStudents() { return maxStudents; }
    public int getCurrentStudentCount() { return currentStudentCount; }
    public String getDayOfWeek() { return dayOfWeek; }
    public int getStartSlot() { return startSlot; }
    public int getEndSlot() { return endSlot; }

    public void setMaxStudents(int maxStudents) { this.maxStudents = maxStudents; }
    // Setter cho currentStudentCount chỉ nên dùng cho mục đích load
    public void setCurrentStudentCount(int currentStudentCount) { this.currentStudentCount = currentStudentCount; }

    // Logic nghiệp vụ nhỏ
    public boolean isFull() { // [cite: 71]
        return currentStudentCount >= maxStudents;
    }

    public void incrementStudentCount() { // [cite: 72]
        if (currentStudentCount < maxStudents) {
            currentStudentCount++;
        }
    }

    public void decrementStudentCount() { // [cite: 73]
        if (currentStudentCount > 0) {
            currentStudentCount--;
        }
    }

    // Thực hiện Identifiable [cite: 76]
    @Override
    public String getId() {
        return this.courseSectionId;
    }

    // Thực hiện FileSerializable [cite: 75]
    @Override
    public String toFileString() {
        // courseSectionId|subjectId|semester|maxStudents|currentStudentCount|dayOfWeek|startSlot|endSlot
        return String.format("%s|%s|%d|%d|%d|%s|%d|%d",
            courseSectionId, subjectId, semester, maxStudents, currentStudentCount, 
            dayOfWeek, startSlot, endSlot);
    }
    
    @Override
    public String toString() { // [cite: 74]
        return "CourseSection{" +
               "id='" + courseSectionId + '\'' +
               ", subjectId='" + subjectId + '\'' +
               ", semester=" + semester +
               '}';
    }
}