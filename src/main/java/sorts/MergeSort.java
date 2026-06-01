package sorts;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MergeSort extends GSort {

    @Override
    public <T extends Comparable<T>> List<T> execute(List<T> list) {
        if (list == null) return List.of();
        if (list.isEmpty()) return List.of();

        return mergeSort(new ArrayList<>(list), Comparator.naturalOrder());
    }

    @Override
    public <T> List<T> execute(List<T> list, Comparator<T> comparator) {
        if (list == null) return List.of();
        if (list.isEmpty()) return List.of();

        return mergeSort(new ArrayList<>(list), comparator);
    }

    private <T> List<T> mergeSort(List<T> list, Comparator<T> comparator) {
        if (list.size() <= 1) {
            return list;
        }

        int mid = list.size() / 2;

        List<T> left = mergeSort(
                new ArrayList<>(list.subList(0, mid)),
                comparator
        );

        List<T> right = mergeSort(
                new ArrayList<>(list.subList(mid, list.size())),
                comparator
        );

        return merge(left, right, comparator);
    }

    private <T> List<T> merge(
            List<T> left,
            List<T> right,
            Comparator<T> comparator
    ) {
        List<T> result = new ArrayList<>();

        int i = 0;
        int j = 0;

        while (i < left.size() && j < right.size()) {
            if (comparator.compare(left.get(i), right.get(j)) <= 0) {
                result.add(left.get(i++));
            } else {
                result.add(right.get(j++));
            }
        }

        while (i < left.size()) {
            result.add(left.get(i++));
        }

        while (j < right.size()) {
            result.add(right.get(j++));
        }

        return result;
    }
}