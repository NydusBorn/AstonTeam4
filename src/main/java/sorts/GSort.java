package sorts;

import java.util.Comparator;
import java.util.List;

public abstract class GSort {
    abstract <T extends Comparable<T>> List<T> execute(List<T> arr);
    abstract <T> List<T> execute(List<T> arr, Comparator<T> comparator);
}
