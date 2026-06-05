package sorts;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Враппер для сортировщиков
 * Позволяет сортировать только элементы, удовлетворяющие условию
 * Элементы которые не прошли фильтр исключаются из результата
 */
public class WrapperSort extends GSort {

    private final GSort delegate;
    private final Predicate<Integer> filter;

    /**
     * @param delegate  базовый сортировщик (QuickSort, HeapSort и т.д.)
     * @param keepEven  true - оставить и сортировать только чётные,
     *                  false - оставить и сортировать только нечётные
     */
    public WrapperSort(GSort delegate, boolean keepEven) {
        this.delegate = delegate;
        this.filter = keepEven ? n -> n % 2 == 0 : n -> n % 2 != 0;
    }

    public WrapperSort (GSort delegate, Predicate<Integer> filter) {
        this.delegate = delegate;
        this.filter = filter;
    }

    @Override
    public <T extends Comparable<T>> List<T> execute(List<T> arr) {
        List<T> filtered = filterIntegers(arr);
        if (filtered.isEmpty()) return new ArrayList<>();
        return delegate.execute(filtered);
    }

    @Override
    public <T> List<T> execute(List<T> arr, Comparator<T> comparator) {
        List<T> filtered = filterIntegers(arr);
        if (filtered.isEmpty()) return new ArrayList<>();
        return delegate.execute(filtered, comparator);
    }

    @SuppressWarnings("unchecked")
    private <T> List<T> filterIntegers(List<T> arr) {
        return (List<T>) arr.stream()
                .filter(e -> e instanceof Integer)
                .filter(e -> filter.test((Integer) e))
                .collect(Collectors.toList());
    }
}