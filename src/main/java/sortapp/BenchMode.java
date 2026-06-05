package sortapp;

import sorts.GSort;
import sorts.HeapSort;
import sorts.InsertionSort;
import sorts.MergeSort;
import sorts.QuickSort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class BenchMode {

    private static final Random RANDOM = new Random(42); // Fixed seed for reproducibility
    private static final int LIST_SIZE = 100;
    private static final int ITERATIONS = 10;

    public static void run() {
        IO.println("\n========== Benchmark Mode ==========");
        IO.println("Testing sort performance across multiple sizes (factor of 10)");
        IO.println("Each algorithm runs " + ITERATIONS + " times per size.");
        IO.println("Timing in microseconds (float).");
        IO.println("====================================\n");

        // Test sizes growing by factor of 10 until at least one sort exceeds 10ms
        int size = 10;
        boolean foundSlowEnough = false;

        while (!foundSlowEnough) {
            IO.println("\n--- Size: " + size + " ---");

            // Generate random integer list
            List<Integer> intList = generateRandomInts(size);

            benchmarkSort("Heap Sort", new HeapSort(), intList);
            benchmarkSort("Merge Sort", new MergeSort(), intList);
            benchmarkSort("Quick Sort", new QuickSort(), intList);
            benchmarkSort("Insertion Sort", new InsertionSort(), intList);

            // Check if any sort took more than 10ms
            if (testExceeds10ms(size)) {
                foundSlowEnough = true;
                IO.println("\nAt least one sort exceeded 10ms — stopping.\n");
            }

            size *= 10;
        }

        IO.println("Benchmark complete!\n");
    }

    private static List<Integer> generateRandomInts(int count) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(RANDOM.nextInt(10000));
        }
        return list;
    }

    private static void benchmarkSort(String name, GSort sorter, List<Integer> inputList) {
        List<Float> timings = new ArrayList<>();

        for (int i = 0; i < ITERATIONS; i++) {
            long startTime = System.nanoTime();
            sorter.execute(inputList, Comparator.naturalOrder());
            long endTime = System.nanoTime();

            float elapsedUs = (endTime - startTime) / 1_000.0f; // microseconds as float
            timings.add(elapsedUs);
        }

        double avg = timings.stream().mapToDouble(Float::doubleValue).average().orElse(0);
        double minD = timings.stream().mapToDouble(Float::doubleValue).min().orElse(0);
        double maxD = timings.stream().mapToDouble(Float::doubleValue).max().orElse(0);

        IO.printf("%-15s | Avg: %8.2f us | Min: %8.2f us | Max: %8.2f us%n",
                name, avg, minD, maxD);
    }

    private static boolean testExceeds10ms(int size) {
        // Run a single sort and check if it exceeds 10ms
        GSort sorter = new InsertionSort();
        List<Integer> testList = generateRandomInts(size);
        long startTime = System.nanoTime();
        sorter.execute(testList, Comparator.naturalOrder());
        long endTime = System.nanoTime();

        float elapsedMs = (endTime - startTime) / 1_000_000.0f;
        return elapsedMs > 10f;
    }
}
