package manager;

import data.Student;
import interfaces.Displayable;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

// Kế thừa Management<Student> và implement Displayable
public class StudentManager extends Management<Student> implements Displayable {

    private final RegistrationManager registrationManager;

    // Constructor MỚI (Cần RegistrationManager cho tính GPA)
    public StudentManager(List<Student> initialList, RegistrationManager registrationManager) {
        super(initialList);
        this.registrationManager = registrationManager;
    }

    // Tìm sinh viên theo tên
    public List<Student> findByName(String name) {
        List<Student> result = new ArrayList<>();
        for (Student s : list) {
            if (s.getFullName().toLowerCase().contains(name.toLowerCase())) {
                result.add(s);
            }
        }
        return result;
    }

    // Sắp xếp theo tên (alphabet)
    public void sortByName() {
        list.sort(Comparator.comparing(Student::getFullName, String.CASE_INSENSITIVE_ORDER));
    }

    // Sắp xếp theo GPA (sử dụng RegistrationManager)
    public void sortByGPA() {
        if (list.isEmpty()) {
            System.out.println("Danh sách sinh viên trống, không thể sắp xếp.");
            return;
        }

        list.sort((s1, s2) -> {
            double gpa1 = registrationManager.calculateOverallGPA(s1.getId());
            double gpa2 = registrationManager.calculateOverallGPA(s2.getId());
            return Double.compare(gpa2, gpa1); // Giảm dần
        });
        
        System.out.println("Danh sách sinh viên đã được sắp xếp theo GPA tổng giảm dần.");
    }
    
    // implement Displayable
    @Override
    public void displayAll() {
        if (list.isEmpty()) {
            System.out.println("Danh sách sinh viên trống.");
        } else {
            System.out.println("=== DANH SÁCH SINH VIÊN ===");
            for (Student s : list) {
                System.out.println(s);
            }
        }
    }
    
    // ĐÃ XÓA: findById(), update() vì chúng được kế thừa từ Management<T>
}