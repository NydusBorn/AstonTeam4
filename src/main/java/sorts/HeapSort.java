package sorts;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HeapSort extends GSort {

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Comparable<T>> List<T> execute(List<T> list) {
        if (list == null) return null;
        if (list.isEmpty()) return List.of();

        List<T> sorted = new ArrayList<>(list);
        int n = sorted.size();

        // Build max heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapifyComparable(sorted, n, i);
        }

        // Extract elements one by one
        for (int i = n - 1; i > 0; i--) {
            T max = sorted.get(0);
            sorted.set(0, sorted.get(i));
            sorted.set(i, max);
            heapifyComparable(sorted, i, 0);
        }

        return sorted;
    }

    @Override
    public <T> List<T> execute(List<T> list, Comparator<T> comparator) {
        if (list == null) return null;
        if (list.isEmpty()) return List.of();

        List<T> sorted = new ArrayList<>(list);
        int n = sorted.size();

        // Build max heap using comparator
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapifyComparator(sorted, n, i, comparator);
        }

        // Extract elements one by one
        for (int i = n - 1; i > 0; i--) {
            T max = sorted.get(0);
            sorted.set(0, sorted.get(i));
            sorted.set(i, max);
            heapifyComparator(sorted, i, 0, comparator);
        }

        return sorted;
    }

    private static <T extends Comparable<T>> void heapifyComparable(List<T> arr, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && arr.get(left).compareTo(arr.get(largest)) > 0) {
            largest = left;
        }

        if (right < n && arr.get(right).compareTo(arr.get(largest)) > 0) {
            largest = right;
        }

        if (largest != i) {
            T swap = arr.get(i);
            arr.set(i, arr.get(largest));
            arr.set(largest, swap);
            heapifyComparable(arr, n, largest);
        }
    }

    private static <T> void heapifyComparator(List<T> arr, int n, int i, Comparator<T> comparator) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && comparator.compare(arr.get(left), arr.get(largest)) > 0) {
            largest = left;
        }

        if (right < n && comparator.compare(arr.get(right), arr.get(largest)) > 0) {
            largest = right;
        }

        if (largest != i) {
            T swap = arr.get(i);
            arr.set(i, arr.get(largest));
            arr.set(largest, swap);
            heapifyComparator(arr, n, largest, comparator);
        }
    }
}
