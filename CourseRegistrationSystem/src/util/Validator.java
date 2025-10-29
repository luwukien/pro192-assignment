package util;

import java.util.Scanner;

/**
 * Lớp tiện ích tĩnh dùng để quản lý nhập liệu từ người dùng 
 * và xác thực tính hợp lệ của dữ liệu.
 */
public class Validator {

    // Sử dụng static final Scanner để tránh rò rỉ tài nguyên và đảm bảo hiệu suất
    private static final Scanner SC = new Scanner(System.in);
    
    // --- Phương thức hỗ trợ nhập liệu chung ---
    
    /**
     * Lấy một chuỗi từ người dùng, đảm bảo chuỗi không rỗng.
     * Khớp với: +getString(String prompt, String errorMsg): String
     * @param prompt Thông điệp hiển thị cho người dùng.
     * @param errorMsg Thông báo lỗi nếu người dùng nhập rỗng.
     * @return Chuỗi hợp lệ được nhập từ console.
     */
    public static String getString(String prompt, String errorMsg) {
        String input;
        do {
            System.out.print(prompt);
            input = SC.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println(errorMsg);
        } while (true);
    }

    /**
     * Lấy một chuỗi từ người dùng, đảm bảo chuỗi khớp với biểu thức chính quy (regex).
     * Khớp với: +getString(String prompt, String errorMsg, String regex): String
     * @param prompt Thông điệp hiển thị cho người dùng.
     * @param errorMsg Thông báo lỗi chung.
     * @param regex Biểu thức chính quy để kiểm tra định dạng.
     * @return Chuỗi hợp lệ được nhập từ console.
     */
    public static String getString(String prompt, String errorMsg, String regex) {
        String input;
        do {
            input = getString(prompt, errorMsg);
            if (input.matches(regex)) {
                return input;
            }
            System.out.println(errorMsg + " (Định dạng sai)");
        } while (true);
    }
    
    // --- Phương thức hỗ trợ nhập số nguyên (Integer) ---

    /**
     * Lấy một số nguyên từ người dùng, đảm bảo số nằm trong khoảng [lowerBound, upperBound].
     * Khớp với: +getInt(String prompt, String errorMsg, int lowerBound, int upperBound): int
     * @param prompt Thông điệp hiển thị cho người dùng.
     * @param errorMsg Thông báo lỗi nếu nhập sai định dạng hoặc ngoài phạm vi.
     * @param lowerBound Giới hạn dưới (bao gồm).
     * @param upperBound Giới hạn trên (bao gồm).
     * @return Số nguyên hợp lệ được nhập từ console.
     */
    public static int getInt(String prompt, String errorMsg, int lowerBound, int upperBound) {
        int number;
        do {
            try {
                String input = getString(prompt, errorMsg);
                number = Integer.parseInt(input);
                if (number >= lowerBound && number <= upperBound) {
                    return number;
                }
                System.out.println(errorMsg + " (Giá trị phải nằm trong khoảng [" + lowerBound + ", " + upperBound + "])");
            } catch (NumberFormatException e) {
                System.out.println(errorMsg + " (Vui lòng nhập một số nguyên)");
            }
        } while (true);
    }
    
    // --- Phương thức hỗ trợ nhập số thực (Double) ---

    /**
     * Lấy một số thực từ người dùng, đảm bảo số nằm trong khoảng [min, max].
     * Khớp với: +getDouble(String prompt, String errorMsg, double min, double max): double
     * @param prompt Thông điệp hiển thị cho người dùng.
     * @param errorMsg Thông báo lỗi nếu nhập sai định dạng hoặc ngoài phạm vi.
     * @param min Giới hạn dưới (bao gồm).
     * @param max Giới hạn trên (bao gồm).
     * @return Số thực hợp lệ được nhập từ console.
     */
    public static double getDouble(String prompt, String errorMsg, double min, double max) {
        double number;
        do {
            try {
                String input = getString(prompt, errorMsg);
                number = Double.parseDouble(input);
                if (number >= min && number <= max) {
                    return number;
                }
                System.out.println(errorMsg + " (Giá trị phải nằm trong khoảng [" + min + ", " + max + "])");
            } catch (NumberFormatException e) {
                System.out.println(errorMsg + " (Vui lòng nhập một số thực)");
            }
        } while (true);
    }

    // --- Phương thức tiện ích khác ---

    /**
     * Đọc một giá trị boolean (Y/N) từ người dùng.
     * @param prompt Thông điệp hiển thị cho người dùng.
     * @param errorMsg Thông báo lỗi.
     * @return true nếu người dùng nhập 'Y' hoặc 'y', false nếu nhập 'N' hoặc 'n'.
     */
    public static boolean getYesNo(String prompt, String errorMsg) {
        String input;
        do {
            input = getString(prompt, errorMsg);
            if (input.equalsIgnoreCase("Y")) {
                return true;
            } else if (input.equalsIgnoreCase("N")) {
                return false;
            }
            System.out.println(errorMsg + " (Vui lòng nhập 'Y' hoặc 'N')");
        } while (true);
    }
}