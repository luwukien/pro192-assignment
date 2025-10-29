package manager;

import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import data.Student;
import data.TestStudent;
import manager.Management;

/**
 *
 * @author IdeaPad
 */
public class StudentManager extends Management<Student> {

    private List<Student> students;

    public StudentManager(List<Student> initialList) {
        super(initialList);
        this.students = this.list;
    }

    public List<TestStudent> findByName(String name) {
        if (name == null) {
            return null;
        }

        for (Student student : students) {
            if (name.equals(student.getFullName())) {
                return (List<TestStudent>) student;
            }
        }
        System.out.println("Not found any name in the list student!");
        return null;
    }

    public void sortByName() {
        System.out.println("---- After sorted by name of the list student ----");
        Collections.sort(this.students, Comparator.comparing(Student::getFullName));
    }

    public List<Student> getStudentsSortedByOverallGPA() {
        System.out.println("---- After sorted by overall GPA of the list student ----");
        //Collections.sort(students, Comparator.comparing(TestStudent::calculateOverallGPA));
        //TODO: làm sau sau khi hoàn thiện method calculateOverallGPA ở class RegistrationManager;
        return null;
    }
    
    public List<Student> getStudentsSortedBySubjectGPA(String subjectId) {
        System.out.println("---- After sorted by overall GPA of the list student ----");
        Collections.sort(students, Comparator.co);
    }
}
