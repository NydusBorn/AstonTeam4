package sortapp;

import sorts.*;

import java.util.ArrayList;
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

            // Step 2: Sort configuration — multi-field
            List<Integer> fieldOrder = selectFieldOrder();
            List<SortCriterion> criteria = selectCriteria(fieldOrder);
            SortConfig config = new SortConfig(criteria);

            // Display selected criteria
            IO.println("\nSorting with criteria:");
            for (int i = 0; i < criteria.size(); i++) {
                SortCriterion c = criteria.get(i);
                IO.println("  " + (i + 1) + ". " + c.getField() + " — " + c.getDirection()
                        + (c.getField() == SortCriterion.Field.PRODUCTION_YEAR ? ", filter=" + c.getIntFilter() : ""));
            }

            // Step 3: Sort execution
            // Run with each sorter and let user choose
            IO.println("Which sort algorithm to use?");
            IO.println("1. Heap Sort");
            IO.println("2. Merge Sort");
            IO.println("3. Quick Sort");
            IO.println("4. Insertion Sort");
            IO.println("5. Selection Sort");

            int sorterChoice = IO.parseInt("> ");
            GSort sorter = switch (sorterChoice) {
                case 1 -> new HeapSort();
                case 2 -> new MergeSort();
                case 3 -> new QuickSort();
                case 4 -> new InsertionSort();
                case 5 -> new SelectionSort();
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

    private List<Integer> selectFieldOrder() {
        while (true) {
            IO.println("\nSelect fields to sort by (comma-separated numbers 1-3):");
            IO.println("  1 = model, 2 = productionYear, 3 = power");
            IO.println("  Example: 1,3,2 or 2,1 or 3");
            String input = IO.readLine("> ");
            if (input.isEmpty()) {
                IO.println("Defaulting to ascending sort by productionYear.");
                return List.of(2);
            }

            try {
                String[] parts = input.split(",");
                List<Integer> fields = new ArrayList<>();
                for (String part : parts) {
                    int val = Integer.parseInt(part.trim());
                    if (val < 1 || val > 3) {
                        IO.println("Invalid field number: " + val + ". Must be 1, 2, or 3. Please try again.");
                        fields.clear();
                        break;
                    }
                    if (fields.contains(val)) {
                        IO.println("Duplicate field number: " + val + ". Please try again.");
                        fields.clear();
                        break;
                    }
                    fields.add(val);
                }
                if (!fields.isEmpty()) {
                    return fields;
                }
            } catch (NumberFormatException e) {
                IO.println("Invalid input. Please enter comma-separated numbers (e.g., 1,3,2).");
            }
        }
    }

    private List<SortCriterion> selectCriteria(List<Integer> fieldOrder) {
        List<SortCriterion> criteria = new ArrayList<>();
        for (Integer fieldNum : fieldOrder) {
            SortCriterion.Field field = switch (fieldNum) {
                case 1 -> SortCriterion.Field.MODEL;
                case 2 -> SortCriterion.Field.PRODUCTION_YEAR;
                case 3 -> SortCriterion.Field.POWER;
                default -> throw new IllegalStateException("Unexpected field: " + fieldNum);
            };

            SortConfig.SortDirection direction = selectFieldDirection();
            SortConfig.IntFilter intFilter = SortConfig.IntFilter.ALL;

            // Only ask intFilter for PRODUCTION_YEAR
            if (field == SortCriterion.Field.PRODUCTION_YEAR) {
                intFilter = selectIntFilter();
            }

            criteria.add(new SortCriterion(field, direction, intFilter));
        }
        return criteria;
    }

    private SortConfig.SortDirection selectFieldDirection() {
        IO.println("\nSelect direction:");
        IO.println("1. Ascending");
        IO.println("2. Descending");
        IO.println("3. Ignore");

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
