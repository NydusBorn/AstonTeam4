package sortapp;

import auto.Auto;

import java.util.List;

public class OutputManager {

    public void printToStdout(List<Auto> sorted) {
        if (sorted == null || sorted.isEmpty()) {
            IO.println("No entries to display.");
            return;
        }

        IO.println("\n========== Sorted Results ==========");
        for (int i = 0; i < sorted.size(); i++) {
            Auto auto = sorted.get(i);
            IO.println((i + 1) + ". " + auto);
        }
        IO.println("====================================");
        IO.println("Total: " + sorted.size() + " entries");
    }

    public void selectOutput(List<Auto> sorted) {
        IO.println("\nOutput option:");
        IO.println("1. Stdout (console)");
        IO.println("2. File (not implemented)");

        int choice = IO.parseInt("> ");

        switch (choice) {
            case 1 -> printToStdout(sorted);
            case 2 -> IO.println("File output is not implemented in this version.");
            default -> IO.println("Invalid output option.");
        }
    }
}
