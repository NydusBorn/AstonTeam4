package sortapp;

import sorts.GSort;
import sorts.HeapSort;
import sorts.InsertionSort;
import sorts.MergeSort;
import sorts.QuickSort;

import java.util.List;

public class SortApp {

    private final InputManager inputManager = new InputManager();
    private final SortExecutor sortExecutor = new SortExecutor();
    private final OutputManager outputManager = new OutputManager();

    public static void main(String[] args) {
        SortApp app = new SortApp();
        app.runLoop();
    }

    public void runLoop() {
        while (true) {
            IO.println("\n========== Auto Sorter ==========");
            IO.println("1. Interactive Sort");
            IO.println("2. Bench Mode");
            IO.println("0. Exit");
            IO.println("=================================");

            int choice = IO.parseInt("Choose an option: ");

            switch (choice) {
                case 1 -> runInteractiveSort();
                case 2 -> BenchMode.run();
                case 0 -> {
                    IO.println("Goodbye!");
                    return;
                }
                default -> IO.println("Invalid option. Please try again.");
            }
        }
    }

    private void runInteractiveSort() {
        try {
            // Step 1: Input source selection
            int sourceChoice = IO.parseInt("\nSelect input source:\n1. Random\n2. File\n3. Manual Input\n> ");

            List<auto.Auto> autos = switch (sourceChoice) {
                case 1 -> {
                    int count = IO.parseInt("How many entries to generate? ");
                    yield inputManager.generateRandom(count);
                }
                case 2 -> {
                    String path = IO.readLine("Enter file path: ");
                    int count = IO.parseInt("How many entries to read? ");
                    yield inputManager.loadFromFile(path, count);
                }
                case 3 -> {
                    int maxCount = IO.parseInt("Enter max entries (0 for no limit): ");
                    yield inputManager.manualInput(maxCount);
                }
                default -> {
                    IO.println("Invalid input source.");
                    yield List.of();
                }
            };

            if (autos.isEmpty()) {
                IO.println("No entries loaded. Returning to menu.");
                return;
            }

            // Step 2: Sort configuration
            SortConfig.SortDirection direction = selectDirection();
            SortConfig.IntFilter intFilter = selectIntFilter();

            SortConfig config = new SortConfig(direction, intFilter);

            // Step 3: Sort execution
            IO.println("\nSorting with:");
            IO.println("  Direction: " + direction);
            IO.println("  Int Filter: " + intFilter);

            // Run with each sorter and let user choose
            IO.println("Which sort algorithm to use?");
            IO.println("1. Heap Sort");
            IO.println("2. Merge Sort");
            IO.println("3. Quick Sort");
            IO.println("4. Insertion Sort");

            int sorterChoice = IO.parseInt("> ");
            GSort sorter = switch (sorterChoice) {
                case 1 -> new HeapSort();
                case 2 -> new MergeSort();
                case 3 -> new QuickSort();
                case 4 -> new InsertionSort();
                default -> {
                    IO.println("Invalid sorter. Defaulting to Heap Sort.");
                    yield new HeapSort();
                }
            };

            List<auto.Auto> sorted = sortExecutor.execute(autos, config, sorter);

            // Step 4: Output selection
            int outputChoice = IO.parseInt("\nOutput option:\n1. Stdout\n2. File (not implemented)\n> ");

            switch (outputChoice) {
                case 1 -> outputManager.printToStdout(sorted);
                case 2 -> IO.println("File output is not implemented in this version.");
                default -> IO.println("Invalid output option.");
            }

        } catch (Exception e) {
            IO.println("Error: " + e.getMessage());
        }
    }

    private SortConfig.SortDirection selectDirection() {
        IO.println("\nSelect sort direction:");
        IO.println("1. Ascending");
        IO.println("2. Descending");
        IO.println("3. Ignore (keep original order)");

        int choice = IO.parseInt("> ");
        return switch (choice) {
            case 1 -> SortConfig.SortDirection.ASCENDING;
            case 2 -> SortConfig.SortDirection.DESCENDING;
            case 3 -> SortConfig.SortDirection.IGNORE;
            default -> {
                IO.println("Invalid direction. Defaulting to Ascending.");
                yield SortConfig.SortDirection.ASCENDING;
            }
        };
    }

    private SortConfig.IntFilter selectIntFilter() {
        IO.println("\nSelect int filter (productionYear):");
        IO.println("1. All");
        IO.println("2. Odd");
        IO.println("3. Even");

        int choice = IO.parseInt("> ");
        return switch (choice) {
            case 1 -> SortConfig.IntFilter.ALL;
            case 2 -> SortConfig.IntFilter.ODD;
            case 3 -> SortConfig.IntFilter.EVEN;
            default -> {
                IO.println("Invalid filter. Defaulting to All.");
                yield SortConfig.IntFilter.ALL;
            }
        };
    }
}
