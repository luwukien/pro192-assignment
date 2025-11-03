package util;

import java.util.Scanner;

public class Validator {

    // Use static final Scanner to avoid resource leaks and ensure efficiency
    private static final Scanner SC = new Scanner(System.in);
    
    // --- Common input helper methods ---

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

    public static String getString(String prompt, String errorMsg, String regex) {
        String input;
        do {
            input = getString(prompt, errorMsg);
            if (input.matches(regex)) {
                return input;
            }
            System.out.println(errorMsg + " (Invalid format)");
        } while (true);
    }
    
    // --- Integer input helper method ---

    public static int getInt(String prompt, String errorMsg, int lowerBound, int upperBound) {
        int number;
        do {
            try {
                String input = getString(prompt, errorMsg);
                number = Integer.parseInt(input);
                if (number >= lowerBound && number <= upperBound) {
                    return number;
                }
                System.out.println(errorMsg + " (Value must be within the range [" + lowerBound + ", " + upperBound + "])");
            } catch (NumberFormatException e) {
                System.out.println(errorMsg + " (Please enter an integer)");
            }
        } while (true);
    }
    
    // --- Double input helper method ---

    public static double getDouble(String prompt, String errorMsg, double min, double max) {
        double number;
        do {
            try {
                String input = getString(prompt, errorMsg);
                number = Double.parseDouble(input);
                if (number >= min && number <= max) {
                    return number;
                }
                System.out.println(errorMsg + " (Value must be within the range [" + min + ", " + max + "])");
            } catch (NumberFormatException e) {
                System.out.println(errorMsg + " (Please enter a decimal number)");
            }
        } while (true);
    }

    // --- Other utility methods ---

    public static boolean getYesNo(String prompt, String errorMsg) {
        String input;
        do {
            input = getString(prompt, errorMsg);
            if (input.equalsIgnoreCase("Y")) {
                return true;
            } else if (input.equalsIgnoreCase("N")) {
                return false;
            }
            System.out.println(errorMsg + " (Please enter 'Y' or 'N')");
        } while (true);
    }
}
