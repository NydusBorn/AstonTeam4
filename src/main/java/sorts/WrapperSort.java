package sorts;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


/**
 * Враппер(декоратор) для сортировщиков, сортирует четные и нечетные
 * числа отдельно, но сохраняет их исходные позиции в списке
 */
public class WrapperSort extends GSort {

    private final GSort delegate;

    public WrapperSort(GSort delegate) {
        this.delegate = delegate;

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
        if (!(arr.get(0) instanceof Integer)) {
            return delegate.execute(arr, comparator);
        }

        List<Integer> odds = new ArrayList<>();
        List<Integer> evens = new ArrayList<>();
        List<Boolean> isOddMask = new ArrayList<>();

        for (T item : arr) {
            Integer num = (Integer) item;
            boolean isOdd = num % 2 != 0;
            isOddMask.add(isOdd);
            if (isOdd) {
                odds.add(num);
            } else {
                evens.add(num);
            }
        }
        // Сортируем каждую группу через делегата
        List<Integer> sortedOdds = (List<Integer>) delegate.execute((List<T>) new ArrayList<>(odds), comparator);
        List<Integer> sortedEvens = (List<Integer>) delegate.execute((List<T>) new ArrayList<>(evens), comparator);
        // Собираем результат, восстанавливая исходную структуру
        List<T> result = new ArrayList<>(arr.size());
        int oddIdx = 0, evenIdx = 0;
        for (boolean wasOdd : isOddMask) {
            if (wasOdd) {
                result.add((T) sortedOdds.get(oddIdx++));
            }
            else {
                result.add((T) sortedEvens.get(evenIdx++));
            }
        }
        return result;

    }
}