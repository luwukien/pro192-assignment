package Test;

import manager.SubjectManager;
import data.Subject;
import interfaces.Displayable;
import java.util.ArrayList;
import java.util.List;

public class SubjectManagerTest {

    public static void runTest(SubjectManager manager) {
        System.out.println("\n=== KIỂM THỬ: SUBJECT MANAGER ===");

        // 1. Dữ liệu mẫu
        Subject sub1 = new Subject("IT101", "Lập trình cơ bản", 3);
        Subject sub2 = new Subject("MA101", "Toán rời rạc", 4);
        sub2.addPrerequisite("IT101");

        // 2. Test ADD và Trùng lặp
        System.out.println("\n--- Test 1: Thêm môn học ---");
        System.out.println("Thêm IT101: " + manager.add(sub1)); 
        System.out.println("Thêm MA101: " + manager.add(sub2)); 
        System.out.println("Thêm IT101 Lần 2: " + manager.add(sub1));

        // 3. Test DISPLAY 
        System.out.println("\n--- Test 2: Hiển thị (displayAll) ---");
        manager.displayAll();

        // 4. Test FIND
        System.out.println("\n--- Test 3: Tìm kiếm (findById) ---");
        Subject foundSub = manager.findById("MA101");
        System.out.println("Tìm MA101: " + (foundSub != null ? foundSub.getSubjectName() : "Không tìm thấy"));

        // 5. Test DELETE
        System.out.println("\n--- Test 4: Xóa ---");
        System.out.println("Xóa IT101: " + manager.delete("IT101")); 
        System.out.println("Xóa môn không tồn tại (CS000): " + manager.delete("CS000"));
        manager.displayAll();
    }

    // HÀM MAIN ĐỂ CHẠY TEST
    public static void main(String[] args) {
        // Khởi tạo SubjectManager với danh sách rỗng (hoặc ban đầu)
        SubjectManager manager = new SubjectManager(new ArrayList<>());
        runTest(manager);
    }
}