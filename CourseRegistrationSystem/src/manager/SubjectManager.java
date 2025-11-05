package manager;
import java.util.List;
import interfaces.Displayable;
import data.Subject;

public class SubjectManager extends Management<Subject> implements Displayable{
    
    public SubjectManager(List<Subject> initialList) {
        super(initialList);
    }
    @Override
    public void displayAll() {
        if (this.list.isEmpty()) {
            System.out.println("Empty list");
            return;
        }
        for (Subject subject : this.list) {
            System.out.println(subject);
        }
    }
}
