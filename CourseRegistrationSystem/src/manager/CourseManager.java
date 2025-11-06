package manager;

import data.CourseSection;
import interfaces.Displayable;
import java.util.List;
import java.util.ArrayList;

public class CourseManager extends Management<CourseSection> implements Displayable {

    public CourseManager(List<CourseSection> initialList) {
        super(initialList);
    }

    public List<CourseSection> getSectionsBySubject(String subjectId) {
        subjectId = subjectId.toUpperCase();
        ArrayList<CourseSection> resultList = new ArrayList<>();
        for (CourseSection section : this.list) {
            if (section.getSubjectId().equals(subjectId)) {
                resultList.add(section);
            }
        }
        return resultList;
    }

    @Override
    public void displayAll() {
        if (this.list.isEmpty()) {
            System.out.println("Empty list");
            return;
        }
        for (CourseSection course : this.list) {
            System.out.println(course);
        }
    }
}
