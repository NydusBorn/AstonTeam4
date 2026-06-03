package sorts;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class InsertionSort extends GSort {

    @Override
    public <T extends Comparable<T>> List<T> execute(List<T> arr) {
        List<T> result = new ArrayList<>(arr);
        for (int i = 1; i < result.size(); i++) {
            T key = result.get(i);
            int j = i - 1;
            while (j >= 0 && result.get(j).compareTo(key) > 0) {
                result.set(j + 1, result.get(j));
                j--;
            }
            result.set(j + 1, key);
        }
        return result;
    }

    @Override
    public <T> List<T> execute(List<T> arr, Comparator<T> comparator) {
        List<T> result = new ArrayList<>(arr);
        for (int i = 1; i < result.size(); i++) {
            T key = result.get(i);
            int j = i - 1;
            while (j >= 0 && comparator.compare(result.get(j), key) > 0) {
                result.set(j + 1, result.get(j));
                j--;
            }
            result.set(j + 1, key);
        }
        return result;
    }
}