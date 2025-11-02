package Test;

import manager.CourseManager;
import data.CourseSection;
import java.util.ArrayList;

public class CourseManagerTest {

    public static void runTest(CourseManager manager) {
        System.out.println("\n=== KIỂM THỬ: COURSE MANAGER ===");

        // 1. Dữ liệu mẫu (Giả định IT101 là Subject ID)
        CourseSection cs1 = new CourseSection("IT101-L1", "IT101", 1, 50, 45, "Mon", 1, 3);
        CourseSection cs2 = new CourseSection("IT101-L2", "IT101", 1, 30, 29, "Wed", 4, 6);
        CourseSection cs3 = new CourseSection("MA201-L1", "MA201", 2, 40, 40, "Tue", 1, 3);

        // 2. Test ADD
        System.out.println("\n--- Test 1: Thêm học phần ---");
        System.out.println("Thêm IT101-L1: " + manager.add(cs1)); 
        System.out.println("Thêm MA201-L1: " + manager.add(cs3)); 
        
        // 3. Test LOGIC (isFull)
        System.out.println("\n--- Test 2: Kiểm tra Logic và Cập nhật ---");
        System.out.println("MA201-L1 đã đầy? " + cs3.isFull()); // Expected: true
        
        // 4. Test UPDATE (Tăng số lượng sinh viên trong cs1)
        cs1.incrementStudentCount(); // 45 -> 46
        System.out.println("Cập nhật IT101-L1: " + manager.update(cs1)); // Expected: true
        
        // 5. Test FIND và FIND BY SUBJECT ID
        System.out.println("\n--- Test 3: Tìm kiếm ---");
        CourseSection foundCS = manager.findById("IT101-L1");
        System.out.println("Tìm IT101-L1: " + (foundCS != null ? "Tìm thấy" : "Không tìm thấy")); // Expected: Tìm thấy
        
        // 6. Test DISPLAY
        System.out.println("\n--- Test 4: Hiển thị ---");
        manager.displayAll();
        
        // 7. Test DELETE
        System.out.println("\n--- Test 5: Xóa ---");
        System.out.println("Xóa IT101-L1: " + manager.delete("IT101-L1")); // Expected: true
        manager.displayAll(); // Expected: Chỉ còn MA201-L1
    }

    // HÀM MAIN ĐỂ CHẠY TEST
    public static void main(String[] args) {
        CourseManager manager = new CourseManager(new ArrayList<>());
        runTest(manager);
    }
}