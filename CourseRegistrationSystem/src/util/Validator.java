package util;

import java.util.Scanner;

public class Validator { // [cite: 16]
    // Sử dụng static Scanner cho tiện ích chung
    private static final Scanner sc = new Scanner(System.in); 

    public static String getString(String inputMsg, String errorMsg) { 
        String input;
        while (true) {
            System.out.print(inputMsg);
            // Dùng sc.nextLine() để lấy toàn bộ dòng
            input = sc.nextLine().trim(); 
            if (!input.isEmpty()) {
                return input;
            }
            System.err.println(errorMsg);
        }
    }

    public static int getAnInteger(String inputMsg, String errorMsg, int lowerBound, int upperBound) { 
        int n;
        while (true) {
            try {
                System.out.print(inputMsg);
                n = Integer.parseInt(sc.nextLine().trim());
                if (n >= lowerBound && n <= upperBound) {
                    return n;
                }
                System.err.println(errorMsg + String.format(" (Phạm vi: %d - %d)", lowerBound, upperBound));
            } catch (NumberFormatException e) {
                System.err.println("Giá trị nhập vào phải là số nguyên.");
            }
        }
    }

    public static double getDoubleInRange(String prompt, double min, double max) { 
        double n;
        while (true) {
            try {
                System.out.print(prompt);
                n = Double.parseDouble(sc.nextLine().trim());
                if (n >= min && n <= max) {
                    return n;
                }
                System.err.println(String.format("Lỗi! Giá trị phải nằm trong khoảng từ %.2f đến %.2f.", min, max));
            } catch (NumberFormatException e) {
                System.err.println("Lỗi! Giá trị nhập vào phải là số thực.");
            }
        }
    }
}