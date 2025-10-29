package manager;

import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import data.Student;
import java.util.ArrayList;
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

    public List<Student> findByName(String name) {
        if (name == null) {
            return null;
        }

        for (Student student : students) {
            if (name.equals(student.getFullName())) {
                return (List<Student>) student;
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
        //Collections.sort(this.students, Comparator.comparing(Student::calculateOverallGPA));
        //TODO: làm sau sau khi hoàn thiện method calculateOverallGPA ở class RegistrationManager;
        return null;
    }

    public List<Student> getStudentsSortedBySubjectGPA(String subjectId) {
        System.out.println("---- After sorted by overall GPA of the list student ----");
        //Collections.sort(this.students, Comparator.comparing(Student));
        return null;
    }
    // TODO
    public double calculateOverallGPA(String studentId) {
        return -1;
    }
    // TODO
    public double calculateSemesterGPA(String studentId, String semester) {
        return -1;
    }

    public void displayAll() {
        if (this.list.isEmpty()) {
            System.out.println("Empty list");
            return;
        }
        for (Student student : students) {
            System.out.println(student);
        }
    }

    //Using the main method to test
    /*
    public static void main(String[] args) {
        System.out.println("--- TEST LOP LOGIC: STUDENT MANAGER ---");

        List<Student> fakeList = new ArrayList<>();
        fakeList.add(new Student("S3", "Thang C", 8.0));
        fakeList.add(new Student("S1", "Thang A", 9.5));
        fakeList.add(new Student("S2", "Thang B", 7.5));

        TestStudentManager manager = new TestStudentManager(fakeList);

        System.out.println("\n[TEST displayAll ban dau]");
        manager.displayAll();

        System.out.println("\n[TEST sortByName()]");
        manager.sortByName();
        manager.displayAll(); // A -> B -> C

        System.out.println("\n[TEST sortByGPA()]");
        manager.sortByGPA();
        manager.displayAll(); // 9.5 -> 8.0 -> 7.5

        System.out.println("\n[TEST add(S4)]");
        manager.add(new TestStudent("S4", "Thang D", 5.0));
        manager.displayAll();
    }
    */
}
