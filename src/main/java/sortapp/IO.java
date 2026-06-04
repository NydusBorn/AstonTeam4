package sortapp;

import java.util.Scanner;

public class IO {

    private static final Scanner SCANNER = new Scanner(System.in);

    private IO() {
    }

    public static void println(String message) {
        System.out.println(message);
    }

    public static void print(String message) {
        System.out.print(message);
    }

    public static String readLine(String prompt) {
        print(prompt);
        return SCANNER.nextLine().trim();
    }

    public static int parseInt(String prompt) {
        while (true) {
            print(prompt);
            String input = SCANNER.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                println("Invalid number. Please try again.");
            }
        }
    }

    public static String readLine() {
        return SCANNER.nextLine().trim();
    }

    public static int parseInt() {
        while (true) {
            String input = SCANNER.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                println("Invalid number. Please try again.");
            }
        }
    }

    public static void printf(String format, Object... args) {
        System.out.printf(format, args);
        System.out.println();
    }
}
