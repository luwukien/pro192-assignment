package manager;

import java.util.List;
import interfaces.Displayable;
import data.CourseSection;

public class CourseManager extends Management<CourseSection> implements Displayable {

    private List<CourseSection> courseSections;

    public CourseManager(List<CourseSection> initialList) {
        super(initialList);
        this.courseSections = this.list;
    }

    @Override
    public void displayAll() {
        if (this.courseSections.isEmpty()) {
            System.out.println("Empty list");
            return;
        }
        for (CourseSection course : courseSections) {
            System.out.println(course);
        }
    }
}
