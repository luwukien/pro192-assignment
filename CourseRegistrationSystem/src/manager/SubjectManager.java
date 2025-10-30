package manager;

import data.Subject;
import interfaces.Displayable;
import java.util.List;

// Kế thừa Management<Subject> và implement Displayable
public class SubjectManager extends Management<Subject> implements Displayable {

    public SubjectManager(List<Subject> initialList) {
        super(initialList);
    }

    // implement Displayable
    @Override
    public void displayAll() {
        if (list.isEmpty()) {
            System.out.println("Danh sách môn học trống.");
        } else {
            System.out.println("=== DANH SÁCH MÔN HỌC ===");
            for (Subject s : list) {
                System.out.println(s);
            }
        }
    }
    
}