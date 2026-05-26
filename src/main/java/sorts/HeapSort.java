package sorts;

import java.util.ArrayList;
import java.util.List;

public class HeapSort {
    public static List<Integer> sort(List<Integer> list) {
        if (list == null) {
            return null;
        }
        if (list.isEmpty()) {
            return List.of();
        }

        List<Integer> sorted = new ArrayList<>(list);

        int n = sorted.size();

        // Build max heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(sorted, n, i);
        }

        // Extract elements one by one
        for (int i = n - 1; i > 0; i--) {
            // Swap root (max) with the last element of the heap
            int max = sorted.get(0);
            sorted.set(0, sorted.get(i));
            sorted.set(i, max);
            heapify(sorted, i, 0);
        }

        return sorted;
    }

    private static void heapify(List<Integer> arr, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && arr.get(left) > arr.get(largest)) {
            largest = left;
        }

        if (right < n && arr.get(right) > arr.get(largest)) {
            largest = right;
        }

        if (largest != i) {
            int swap = arr.get(i);
            arr.set(i, arr.get(largest));
            arr.set(largest, swap);
            heapify(arr, n, largest);
        }
    }
}
