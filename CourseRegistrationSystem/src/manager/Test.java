package manager;

import interfaces.Identifiable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author IdeaPad
 */
class MockItem implements interfaces.Identifiable {

    private String name;
    private String id;

    public MockItem(String id, String name) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MockItem{" + "name=" + name + ", id=" + id + '}';
    }
}

class MockManager extends Management {

    public MockManager(List initialList) {
        super(initialList);
    }

}

class Test {

    public static void main(String[] args) {
        List<MockItem> fakeList = new ArrayList<>();
        fakeList.add(new MockItem("S1", "Thang A"));
        fakeList.add(new MockItem("S2", "Thang B"));
        
        // Khởi tạo Manager "giả"
        MockManager manager = new MockManager(fakeList);
        
        System.out.println("List ban dau: " + manager.getAll());

        // --- TEST 1: ADD (Thành công) ---
        System.out.println("\n[TEST ADD S3]");
        boolean addSuccess = manager.add(new MockItem("S3", "Thang C"));
        System.out.println("Ket qua: " + addSuccess); // Phải là true
        System.out.println("List sau khi add: " + manager.getAll());

        // --- TEST 2: ADD (Thất bại - Trùng ID) ---
        System.out.println("\n[TEST ADD S1 (Trung)]");
        boolean addFail = manager.add(new MockItem("S1", "Thang A Fake"));
        // Mày sẽ thấy nó in "Found this id: S1 exist!"
        System.out.println("Ket qua: " + addFail); // Phải là false

        // --- TEST 3: FIND BY ID (Thành công) ---
        System.out.println("\n[TEST FIND S2]");
        MockItem found = (MockItem) manager.findById("S2");
        System.out.println("Tim thay: " + found); // Phải là Thang B

        // --- TEST 4: DELETE (Thành công) ---
        System.out.println("\n[TEST DELETE S2]");
        boolean deleteSuccess = manager.delete("S2");
        System.out.println("Ket qua: " + deleteSuccess); // Phải là true
        System.out.println("List sau khi xoa: " + manager.getAll());

        // --- TEST 5: DELETE (Thất bại - Đéo thấy) ---
        System.out.println("\n[TEST DELETE S99 (Khong co)]");
        boolean deleteFail = manager.delete("S99");
        // Mày sẽ thấy nó in "This this id: S99 is not exist!"
        System.out.println("Ket qua: " + deleteFail); // Phải là false

        // --- TEST 6: UPDATE (Thành công) ---
        System.out.println("\n[TEST UPDATE S1]");
        boolean updateSuccess = manager.update(new MockItem("S1", "Thang A DA UPDATE"));
        System.out.println("Ket qua: " + updateSuccess); // Phải là true
        System.out.println("Item S1 sau update: " + manager.findById("S1"));
    }
}
