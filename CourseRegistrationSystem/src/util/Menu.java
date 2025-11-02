package util;

import java.util.ArrayList;
import java.util.List;

/**
 * Lớp tiện ích tĩnh quản lý việc hiển thị các tùy chọn menu 
 * và lấy lựa chọn hợp lệ từ người dùng.
 */
public class Menu {

    // --- Phương thức hiển thị menu chung ---

    /**
     * Hiển thị danh sách các tùy chọn và lấy lựa chọn hợp lệ của người dùng.
     * @param title Tiêu đề của menu.
     * @param options Danh sách các tùy chọn (String).
     * @return Lựa chọn của người dùng (số nguyên, bắt đầu từ 1).
     */
    public static int getChoice(String title, List<String> options) {
        System.out.println("\n=============================================");
        System.out.println("|| " + title.toUpperCase());
        System.out.println("=============================================");
        
        for (int i = 0; i < options.size(); i++) {
            System.out.printf("|| %2d. %s\n", (i + 1), options.get(i));
        }
        System.out.println("=============================================");

        int min = 1;
        int max = options.size();
        
        // Sử dụng Validator để đảm bảo đầu vào là một số nguyên hợp lệ trong phạm vi
        return Validator.getInt(
                "Nhập lựa chọn của bạn [" + min + "-" + max + "]: ",
                "Lựa chọn không hợp lệ!",
                min,
                max
        );
    }
    
    // --- Phương thức hiển thị Menu Chính ---

    /**
     * Hiển thị menu chính của hệ thống.
     * @return Lựa chọn của người dùng.
     */
    public static int showMainMenu() {
        List<String> options = new ArrayList<>();
        options.add("Quản lý Sinh viên");
        options.add("Quản lý Môn học");
        options.add("Quản lý Học phần mở");
        options.add("Quản lý Đăng ký/Điểm");
        options.add("Báo cáo và Thống kê");
        options.add("Lưu và Thoát");
        
        return getChoice("HỆ THỐNG ĐĂNG KÝ HỌC PHẦN", options);
    }
    
    // --- Phương thức hiển thị Menu Sinh viên ---

    /**
     * Hiển thị menu quản lý sinh viên.
     * @return Lựa chọn của người dùng.
     */
    public static int showStudentManagementMenu() {
        List<String> options = new ArrayList<>();
        options.add("Thêm sinh viên mới");
        options.add("Tìm kiếm sinh viên theo ID");
        options.add("Tìm kiếm sinh viên theo Tên");
        options.add("Cập nhật thông tin sinh viên");
        options.add("Xóa sinh viên");
        options.add("Hiển thị tất cả sinh viên (Sắp xếp theo tên)");
        options.add("Sắp xếp theo GPA tổng");
        options.add("Quay lại Menu chính");

        return getChoice("QUẢN LÝ SINH VIÊN", options); 
    }

    // --- Phương thức hiển thị Menu Môn học ---

    /**
     * Hiển thị menu quản lý môn học.
     * @return Lựa chọn của người dùng.
     */
    public static int showSubjectManagementMenu() {
        List<String> options = new ArrayList<>();
        options.add("Thêm môn học mới");
        options.add("Tìm kiếm môn học theo ID");
        options.add("Cập nhật thông tin môn học");
        options.add("Xóa môn học");
        options.add("Hiển thị tất cả môn học");
        options.add("Quay lại Menu chính");

        return getChoice("QUẢN LÝ MÔN HỌC", options);
    }

    // --- Phương thức hiển thị Menu Học phần ---
    
    /**
     * Hiển thị menu quản lý học phần mở.
     * @return Lựa chọn của người dùng.
     */
    public static int showCourseManagementMenu() {
        List<String> options = new ArrayList<>();
        options.add("Thêm học phần mới");
        options.add("Tìm kiếm học phần theo ID");
        options.add("Tìm kiếm học phần theo ID môn học");
        options.add("Cập nhật thông tin học phần");
        options.add("Xóa học phần");
        options.add("Hiển thị tất cả học phần");
        options.add("Quay lại Menu chính");

        return getChoice("QUẢN LÝ HỌC PHẦN MỞ", options);
    }
    
    // --- Phương thức hiển thị Menu Đăng ký/Điểm ---
    
    /**
     * Hiển thị menu quản lý đăng ký và điểm.
     * @return Lựa chọn của người dùng.
     */
    public static int showRegistrationManagementMenu() {
        List<String> options = new ArrayList<>();
        options.add("Đăng ký học phần");
        options.add("Hủy đăng ký học phần");
        options.add("Nhập điểm môn học");
        options.add("Xem danh sách đăng ký của sinh viên");
        options.add("Xem danh sách sinh viên theo học phần");
        options.add("Quay lại Menu chính");
        
        return getChoice("QUẢN LÝ ĐĂNG KÝ VÀ ĐIỂM", options);
    }

    // --- Phương thức hiển thị Menu Báo cáo ---
    
    /**
     * Hiển thị menu báo cáo và thống kê.
     * @return Lựa chọn của người dùng.
     */
    public static int showReportMenu() {
        List<String> options = new ArrayList<>();
        options.add("Tính GPA tổng cho sinh viên");
        options.add("Tính GPA theo học kỳ");
        options.add("Quay lại Menu chính");
        
        return getChoice("BÁO CÁO VÀ THỐNG KÊ", options);
    }
}