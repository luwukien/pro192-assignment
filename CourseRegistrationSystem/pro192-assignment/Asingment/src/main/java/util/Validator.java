/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;
import java.util.Scanner;

/**
 *
 * @author vuhuy
 */
public class Validator {
    private static final Scanner sc = new Scanner(System.in);

    public static int getInt(String prompt, int min, int max) {
        int value;
        while (true) {
            try {
                System.out.print(prompt);
                value = Integer.parseInt(sc.nextLine());
                if (value < min || value > max)
                    throw new NumberFormatException();
                return value;
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input! Please enter a number between " + min + " and " + max + ".");
            }
        }
    }

    public static String getString(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }
}
