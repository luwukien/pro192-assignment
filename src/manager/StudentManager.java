package manager;

import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import data.Student;
import interfaces.Displayable;

public class StudentManager extends Management<Student> implements Displayable {

    public StudentManager(List<Student> initialList) {
        super(initialList);
    }

    public List<Student> findByName(String name) {
        if (name == null) {
            return null;
        }
        ArrayList<Student> resultList = new ArrayList<>();

        for (Student student : this.list) {
            if (student != null) {
                if (getLastName(student.getFullName()).toLowerCase().contains(name.toLowerCase())) {
                    resultList.add(student);
                }
            }
        }

        if (resultList.isEmpty()) {
            System.out.println("Not found any name in the list student!");
            return resultList;
        }

        return resultList;

    }

    private String getLastName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            return "";
        }
        String[] parts = fullName.split(" ");
        return parts[parts.length - 1];
    }

public List<Student> getStudentsSortedByName() {
    ArrayList<Student> resultList = new ArrayList<>(this.list);

    // SỬA: Sắp xếp KHÔNG phân biệt chữ hoa/chữ thường dựa trên Tên cuối cùng
    Collections.sort(
        resultList, 
        // Lấy tên cuối cùng, sau đó so sánh bằng String.CASE_INSENSITIVE_ORDER
        Comparator.comparing(
            student -> getLastName(student.getFullName()), 
            String.CASE_INSENSITIVE_ORDER
        )
    );
    return resultList;
}

    @Override
    public void displayAll() {
        if (this.list.isEmpty()) {
            System.out.println("Empty list");
            return;
        }
        for (Student student : this.list) {
            System.out.println(student);
        }
    }
}
