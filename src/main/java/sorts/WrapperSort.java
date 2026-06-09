package sorts;

import auto.Auto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


/**
 * Враппер(декоратор) для сортировщиков, сортирует четные и нечетные
 * числа отдельно, но сохраняет их исходные позиции в списке
 */
public class WrapperSort extends GSort {

    private final GSort delegate;
    private final boolean keepEven;

    public WrapperSort(GSort delegate, boolean keepEven) {
        this.delegate = delegate;
        this.keepEven = keepEven;
    }

    @Override
    public <T extends Comparable<T>> List<T> execute(List<T> arr) {
        return executeWithParity(arr, Comparator.naturalOrder());
    }

    @Override
    public <T> List<T> execute(List<T> arr, Comparator<T> comparator) {
        return executeWithParity(arr, comparator);
    }

    @SuppressWarnings("unchecked")
    private <T> List<T> executeWithParity(List<T> arr, Comparator<T> comparator) {
        if (arr == null || arr.isEmpty()) {
            return new ArrayList<>(arr == null ? List.of() : arr);
        }

        List<Boolean> isOddMask = new ArrayList<>();

        if (arr.getFirst() instanceof Auto) {
            for (T item : arr) {
                Auto autoItem = (Auto) item;
                int parityValue = autoItem.getProductionYear();
                boolean isOdd = parityValue % 2 != 0;
                isOddMask.add(isOdd);
            }
        } else if (arr.get(0) instanceof Integer) {
            for (T item : arr) {
                Integer num = (Integer) item;
                boolean isOdd = num % 2 != 0;
                isOddMask.add(isOdd);
            }
        } else {
            return delegate.execute(arr, comparator);
        }

        List<T> unsortedPart = new ArrayList();
        List<T> sortedPart = new ArrayList();
        for (int i = 0; i < arr.size(); i++) {
            if (keepEven) {
                if (Boolean.TRUE.equals(isOddMask.get(i))) {
                    sortedPart.add(arr.get(i));
                } else {
                    unsortedPart.add(arr.get(i));
                }
            } else {
                if (Boolean.TRUE.equals(isOddMask.get(i))) {
                    unsortedPart.add(arr.get(i));
                } else {
                    sortedPart.add(arr.get(i));
                }
            }
        }

        sortedPart = delegate.execute(sortedPart, comparator);

        // Rebuild result by walking parity mask
        List<T> result = new ArrayList<>(arr.size());
        int oddIdx = 0, evenIdx = 0;

        for (int i = 0; i < isOddMask.size(); i++) {
            boolean wasOdd = isOddMask.get(i);
            if (wasOdd) {
                if (keepEven) {
                    result.add((T) sortedPart.get(oddIdx++));
                } else {
                    result.add((T) unsortedPart.get(oddIdx++));
                }
            } else {
                if (keepEven) {
                    result.add((T) unsortedPart.get(evenIdx++));
                } else {
                    result.add((T) sortedPart.get(evenIdx++));
                }
            }
        }

        return result;
    }
}