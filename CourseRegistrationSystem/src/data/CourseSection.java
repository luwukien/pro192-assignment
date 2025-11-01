package data;

import interfaces.Identifiable;
import interfaces.FileSerializable;
import data.enums.DayOfWeek;

public class CourseSection implements Identifiable, FileSerializable {
    private String courseSectionId; 
    private String subjectId; 
    private int semester; 
    private int maxStudents; 
    private int currentStudentCount; 
    private DayOfWeek dayOfWeek;
    private int startSlot;
    private int endSlot; 

    public CourseSection() {
    }

    public CourseSection(String courseSectionId, String subjectId, int semester, int maxStudents,
                         int currentStudentCount, DayOfWeek dayOfWeek, int startSlot, int endSlot) {
        this.courseSectionId = courseSectionId;
        this.subjectId = subjectId;
        this.semester = semester;
        this.maxStudents = maxStudents;
        this.currentStudentCount = currentStudentCount;
        this.dayOfWeek = dayOfWeek;
        this.startSlot = startSlot;
        this.endSlot = endSlot;
    }

    public String getCourseSectionId() {
        return courseSectionId;
    }

    public void setCourseSectionId(String courseSectionId) {
        this.courseSectionId = courseSectionId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getMaxStudents() {
        return maxStudents;
    }

    public void setMaxStudents(int maxStudents) {
        this.maxStudents = maxStudents;
    }

    public int getCurrentStudentCount() {
        return currentStudentCount;
    }

    public void setCurrentStudentCount(int currentStudentCount) {
        this.currentStudentCount = currentStudentCount;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getStartSlot() {
        return startSlot;
    }

    public void setStartSlot(int startSlot) {
        this.startSlot = startSlot;
    }

    public int getEndSlot() {
        return endSlot;
    }

    public void setEndSlot(int endSlot) {
        this.endSlot = endSlot;
    }

    public boolean isFull() { 
        return currentStudentCount >= maxStudents;
    }

    public void incrementStudentCount() {
        if (currentStudentCount < maxStudents) {
            currentStudentCount++;
        }
    }

    public void decrementStudentCount() { 
        if (currentStudentCount > 0) {
            currentStudentCount--;
        }
    }

    @Override
    public String getId() {
        return this.courseSectionId;
    }

    @Override
    public String toFileString() {
        return String.format("%s|%s|%d|%d|%d|%s|%d|%d",
            courseSectionId, subjectId, semester, maxStudents, currentStudentCount, 
            dayOfWeek, startSlot, endSlot);
    }
    
    @Override
    public String toString() {
        return "CourseSection{" +
               "id='" + courseSectionId + '\'' +
               ", subjectId='" + subjectId + '\'' +
               ", semester=" + semester +
               '}';
    }
}