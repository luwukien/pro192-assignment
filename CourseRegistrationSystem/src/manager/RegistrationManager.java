package manager;

import java.util.List;
import interfaces.Displayable;
import data.Registration;
                                                                                                                                                                                                                                                                                                     
public class RegistrationManager extends Management<Registration> implements Displayable {

    private List<Registration> registrations;

    public RegistrationManager(List<Registration> initialList) {
        super(initialList);
        this.registrations = this.list;
    }
    
    public boolean registerCourse(String studentId, String courseSectionId) {
        
    }

    @Override
    public void displayAll() {
        if (this.registrations.isEmpty()) {
            System.out.println("Empty list");
            return;
        }
        for (Registration registration : registrations) {
            System.out.println(registration);
        }
    }
}
