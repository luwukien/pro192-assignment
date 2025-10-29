package manager;

import data.Subject; 
import interfaces.Displayable;
import java.util.List;
import java.util.ArrayList;

// Kế thừa từ Management<Subject> và implement Displayable
public class SubjectManager extends Management<Subject> implements Displayable {

    /**
     * Constructor
     * @param initialList Danh sách môn học ban đầu.
     */
    public SubjectManager(List<Subject> initialList) {
        // Gọi constructor lớp cha để khởi tạo thuộc tính 'list'
        super(initialList);
    }

    /**
     * Hiển thị tất cả môn học.
     * Phương thức này là yêu cầu từ UML và interface Displayable.
     */
    @Override
    public void displayAll() {
        // Sử dụng thuộc tính 'list' kế thừa
        if (list.isEmpty()) {
            System.out.println("Danh sách môn học trống.");
        } else {
            System.out.println("=== DANH SÁCH MÔN HỌC ===");
            for (Subject s : list) {
                // Giả định lớp Subject có override phương thức toString()
                System.out.println(s); 
            }
        }
    }



    @Override
    public Subject findById(String id) {
        for (Subject s : list) {
            if (s.getId().equalsIgnoreCase(id)) {
                return s;
            }
        }
        return null;
    }

    @Override
    public boolean update(Subject itemToUpdate) {
        for (int i = 0; i < list.size(); i++) {
            Subject current = list.get(i);
            if (current.getId().equalsIgnoreCase(itemToUpdate.getId())) {
                list.set(i, itemToUpdate);
                return true;
            }
        }
        return false;
    }
    
    // Giả định Management là Abstract Class và các phương thức này cần được override
    @Override
    public List<Subject> getAll() {
        return new ArrayList<>(list);
    }
    
    @Override
    public boolean add(Subject item) {
        if (findById(item.getId()) == null) {
            return list.add(item);
        }
        return false; // Môn học đã tồn tại
    }

    @Override
    public boolean delete(Subject item) {
        return list.remove(item);
    }
}