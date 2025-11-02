package util;

import java.util.ArrayList;
import java.util.List;

public class Menu {

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
        
        return Validator.getInt(
                "Nhập lựa chọn của bạn [" + min + "-" + max + "]: ",
                "Lựa chọn không hợp lệ!",
                min,
                max
        );
    }
    
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

 
    public static int showReportMenu() {
        List<String> options = new ArrayList<>();
        options.add("Tính GPA tổng cho sinh viên");
        options.add("Tính GPA theo học kỳ");
        options.add("Quay lại Menu chính");
        
        return getChoice("BÁO CÁO VÀ THỐNG KÊ", options);
    }
}