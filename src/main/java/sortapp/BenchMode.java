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
        IO.println("Sorting " + LIST_SIZE + " random integers using each algorithm.");
        IO.println("Each algorithm will run " + ITERATIONS + " times.");
        IO.println("====================================\n");

        // Generate random integer list
        List<Integer> intList = generateRandomInts(LIST_SIZE);

        // Benchmark each sort
        benchmarkSort("Heap Sort", new HeapSort(), intList);
        benchmarkSort("Merge Sort", new MergeSort(), intList);
        benchmarkSort("Quick Sort", new QuickSort(), intList);
        benchmarkSort("Insertion Sort", new InsertionSort(), intList);

        IO.println("\nBenchmark complete!\n");
    }

    private static List<Integer> generateRandomInts(int count) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(RANDOM.nextInt(10000));
        }
        return list;
    }

    private static void benchmarkSort(String name, GSort sorter, List<Integer> inputList) {
        List<Long> timings = new ArrayList<>();

        for (int i = 0; i < ITERATIONS; i++) {
            long startTime = System.nanoTime();
            sorter.execute(inputList, Comparator.naturalOrder());
            long endTime = System.nanoTime();

            long elapsedMs = (endTime - startTime) / 1_000_000;
            timings.add(elapsedMs);
        }

        long averageMs = (long) timings.stream().mapToLong(Long::longValue).average().orElse(0);
        long minMs = timings.stream().mapToLong(Long::longValue).min().orElse(0);
        long maxMs = timings.stream().mapToLong(Long::longValue).max().orElse(0);

        IO.printf("%-15s | Avg: %6d ms | Min: %6d ms | Max: %6d ms%n",
                name, averageMs, minMs, maxMs);
    }
}
