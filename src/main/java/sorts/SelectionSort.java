package sorts;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SelectionSort extends GSort {

    @Override
    public <T extends Comparable<T>> List<T> execute(List<T> arr) {
        if (arr == null) return List.of();
        List<T> result = new ArrayList<>(arr);

        int n = result.size();
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (result.get(j).compareTo(result.get(minIndex)) < 0) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                T temp = result.get(i);
                result.set(i, result.get(minIndex));
                result.set(minIndex, temp);
            }
        }
        return result;
    }

    @Override
    public <T> List<T> execute(List<T> arr, Comparator<T> comparator) {
        if (arr == null) return List.of();
        List<T> result = new ArrayList<>(arr);

        int n = result.size();
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (comparator.compare(result.get(j), result.get(minIndex)) < 0) {
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                T temp = result.get(i);
                result.set(i, result.get(minIndex));
                result.set(minIndex, temp);
            }
        }
        return result;
    }
}