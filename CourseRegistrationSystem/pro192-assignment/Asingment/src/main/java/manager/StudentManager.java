/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager;

import data.Student;
import data.Subject;
import interfaces.Displayable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author vuhuy
 */
public class StudentManager extends Management<Student> implements Displayable{
    private List<Student> students = new ArrayList<>();

    @Override
    public Student findById(String id) {
        for (Student s : students) {
            if (s.getStudentId().equalsIgnoreCase(id)) {
                return s;
            }
        }
        return null;
    }

    public List<Student> findByName(String name) {
        List<Student> result = new ArrayList<>();
        for (Student s : students) {
            if (s.getFullName().toLowerCase().contains(name.toLowerCase())) {
                result.add(s);
            }
        }
        return result;
    }

    @Override
    public boolean update(Student itemToUpdate) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudentId().equals(itemToUpdate.getStudentId())) {
                students.set(i, itemToUpdate);
                return true;
            }
        }
        return false;
    }

    public void sortByName() {
        students.sort(Comparator.comparing(Student::getFullName));
    }

    public void sortByGPA() {
        // giả sử chưa có GPA trong UML, để trống
        System.out.println("Chưa có thuộc tính GPA để sắp xếp.");
    }

    public List<Student> getStudentsSortedByOverallGPA() {
        // placeholder
        return new ArrayList<>(students);
    }
    @Override
    public void displayAll() {
            for (Student s : students) {
                System.out.println(s);
        }
    }
}
