package manager;

import data.CourseSection;
import interfaces.Displayable;
import java.util.List;
import java.util.ArrayList;

// Kế thừa Management<CourseSection> và implement Displayable
public class CourseManager extends Management<CourseSection> implements Displayable {

    public CourseManager(List<CourseSection> initialList) {
        super(initialList);
    }

    // implement Displayable
    @Override
    public void displayAll() {
        if (list.isEmpty()) {
            System.out.println("Danh sách học phần (Course Section) trống.");
        } else {
            System.out.println("=== DANH SÁCH HỌC PHẦN MỞ ===");
            for (CourseSection cs : list) {
                System.out.println(cs);
            }
        }
    }
    
    // Phương thức nghiệp vụ bổ sung (Giữ lại để tìm kiếm theo Subject ID)
    public List<CourseSection> findBySubjectId(String subjectId) {
        List<CourseSection> result = new ArrayList<>();
        for (CourseSection cs : list) {
            if (cs.getSubjectId().equalsIgnoreCase(subjectId)) {
                result.add(cs);
            }
        }
        return result;
    }
    
}