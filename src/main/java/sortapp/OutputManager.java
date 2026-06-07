package sortapp;

import auto.Auto;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class OutputManager {

    private final Scanner scanner = new Scanner(System.in);

    // =========================
    // CONSOLE OUTPUT
    // =========================
    public void printToStdout(List<Auto> sorted) {
        if (sorted == null || sorted.isEmpty()) {
            IO.println("No entries to display.");
            return;
        }

        IO.println("\n========== Sorted Results ==========");

        for (int i = 0; i < sorted.size(); i++) {
            IO.println((i + 1) + ". " + sorted.get(i));
        }

        IO.println("====================================");
        IO.println("Total: " + sorted.size() + " entries");
    }

    // =========================
    // FILE OUTPUT (TASK #16)
    // =========================
    private void printToFile(List<Auto> sorted, String path) {
        if (sorted == null || sorted.isEmpty()) {
            IO.println("No entries to save.");
            return;
        }

        try (FileWriter writer = new FileWriter(path, true)) { // append mode

            writer.write("\n========== Sorted Results ==========\n");

            for (int i = 0; i < sorted.size(); i++) {
                writer.write((i + 1) + ". " + sorted.get(i) + "\n");
            }

            writer.write("====================================\n");
            writer.write("Total: " + sorted.size() + " entries\n");

            IO.println("Results saved to: " + path);

        } catch (IOException e) {
            IO.println("Error writing file: " + e.getMessage());
        }
    }

    // =========================
    // MENU SELECT OUTPUT
    // =========================
    public void selectOutput(List<Auto> sorted) {
        IO.println("\nOutput option:");
        IO.println("1. Stdout (console)");
        IO.println("2. File");

        int choice = IO.parseInt("> ");

        switch (choice) {
            case 1 -> printToStdout(sorted);

            case 2 -> {
                IO.println("Enter file path (default: results.txt): ");
                String path = scanner.nextLine();

                if (path == null || path.trim().isEmpty()) {
                    path = "results.txt";
                }

                printToFile(sorted, path);
            }

            default -> IO.println("Invalid output option.");
        }
    }
}