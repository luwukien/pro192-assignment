package manager;
import java.util.List;
import interfaces.Displayable;
import data.Subject;

public class SubjectManager extends Management<Subject> implements Displayable{
    private List<Subject> subjects;
    
    public SubjectManager(List<Subject> initialList) {
        super(initialList);
        this.subjects = this.list;
    }
    
    @Override
    public void displayAll() {
        if (this.subjects.isEmpty()) {
            System.out.println("Empty list");
            return;
        }
        for (Subject subject : subjects) {
            System.out.println(subject);
        }
    }
}
