package manager;

import data.Student;
import interfaces.Displayable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StudentManager extends Management<Student> implements Displayable {


    // Constructor
    public StudentManager(List<Student> initialList) {
        // Gọi constructor lớp cha, thuộc tính 'list' sẽ được gán giá trị
        super(initialList); 
        // LƯU Ý: Nếu Management không tự khởi tạo list khi initialList là null, 
        // thì cần đảm bảo list được khởi tạo trong constructor Management.
    }

    // Tìm sinh viên theo tên
    public List<Student> findByName(String name) {
        List<Student> result = new ArrayList<>();
        // SỬA: Dùng 'list' thay vì 'students'
        for (Student s : list) {
            if (s.getFullName().toLowerCase().contains(name.toLowerCase())) {
                result.add(s);
            }
        }
        return result;
    }

    // Sắp xếp theo tên (alphabet)
    public void sortByName() {
        // SỬA: Dùng 'list' thay vì 'students'
        list.sort(Comparator.comparing(Student::getFullName, String.CASE_INSENSITIVE_ORDER));
    }

    // Sắp xếp theo GPA (giả sử sau này Student có trường GPA)
    public void sortByGPA() {
        System.out.println("Chưa có trường GPA để sắp xếp.");
    }

    // Lấy danh sách sinh viên theo GPA tổng (placeholder)
    public List<Student> getStudentsSortedByOverallGPA() {
        System.out.println("Chưa có dữ liệu GPA tổng.");
        // SỬA: Dùng 'list' thay vì 'students'
        return new ArrayList<>(list);
    }

    // Lấy danh sách sinh viên theo điểm một môn cụ thể (placeholder)
    public List<Student> getStudentsSortedBySubjectGrade(String subjectId) {
        System.out.println("Chưa có dữ liệu điểm môn học: " + subjectId);
        // SỬA: Dùng 'list' thay vì 'students'
        return new ArrayList<>(list);
    }

    // Hiển thị tất cả sinh viên
    @Override
    public void displayAll() {
        // SỬA: Dùng 'list' thay vì 'students'
        if (list.isEmpty()) {
            System.out.println("Danh sách sinh viên trống.");
        } else {
            System.out.println("=== DANH SÁCH SINH VIÊN ===");
            // SỬA: Dùng 'list' thay vì 'students'
            for (Student s : list) {
                System.out.println(s);
            }
        }
    }

    // Phương thức bắt buộc từ Management (tìm kiếm theo ID)
    @Override
    public Student findById(String id) {
        for (Student s : list) {
            if (s.getId().equalsIgnoreCase(id)) {
                return s;
            }
        }
        return null;
    }

    // Phương thức bắt buộc từ Management (cập nhật)
    @Override
    public boolean update(Student itemToUpdate) {
        for (int i = 0; i < list.size(); i++) {
            Student current = list.get(i);
            if (current.getId().equalsIgnoreCase(itemToUpdate.getId())) {
                list.set(i, itemToUpdate);
                return true;
            }
        }
        return false;
    }
}