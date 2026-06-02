package sorts;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class QuickSort extends GSort {
    @Override
    public <T extends Comparable<T>> List<T> execute(List<T> arr) {
        if (arr == null) return new ArrayList<>();
        if (arr.size() <= 1) return new ArrayList<>(arr);

        List<T> result = new ArrayList<>(arr);
        quickSortList(result, 0, result.size() - 1, Comparator.naturalOrder());
        return result;
    }

    @Override
    public <T> List<T> execute(List<T> arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) return new ArrayList<>(arr == null ? new ArrayList<>() : arr);
        if (arr.size() <= 1) return new ArrayList<>(arr);

        List<T> result = new ArrayList<>(arr);
        quickSortList(result, 0, result.size() - 1, comparator);
        return result;
    }

    
    private <T> void quickSortList(List<T> list, int start, int end, Comparator<T> comparator) {
        if (start >= end) return;

        int pivotIndex = start + (end - start) / 2;
        T pivotValue = list.get(pivotIndex);
        int left = start;
        int right = end;

        while (left <= right) {
            while (comparator.compare(list.get(left), pivotValue) < 0) left++;
            while (comparator.compare(list.get(right), pivotValue) > 0) right--;
            if (left <= right) {
                swap(list, left, right);
                left++;
                right--;
            }
        }
        if (start < right) quickSortList(list, start, right, comparator);
        if (left < end) quickSortList(list, left, end, comparator);
    }

    private <T> void swap(List<T> list, int i, int j) {
        T temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}
