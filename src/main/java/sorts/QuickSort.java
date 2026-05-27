package sorts;

import java.util.Comparator;
import java.util.Arrays;

public class QuickSort {
    public static <T> void quickSort(T[] array, int start, int end, Comparator <T> comparator) {
        if (array == null || comparator == null) {
            throw new IllegalArgumentException("Массив и компоратор не может быть пустым");
        }
        if (start >= end) {
            return;
        }
        int pivotIndex = start + (end-start)/2;
        T pivotValue = array[pivotIndex];
        int left = start;
        int right = end;

        while (left <= right) {
            while (comparator.compare(array[left],pivotValue) < 0) {
                left++;
            }
            while (comparator.compare(array[right], pivotValue) > 0 ) {
                right--;
            }
            if (left <= right) {
                swap(array, left, right);
                left++;
                right--;
            }

        }
        if (start < right) {
            quickSort(array, start, right, comparator);
        }
        if (left < end) {
            quickSort(array, left, end, comparator);
        }
    }
    private static <T> void swap(T[] array, int left, int right) {
        T temp = array[right];
        array[right] = array[left];
        array[left] = temp;

    }

    public static void main(String[] args) {
        Integer[] array = {2,3,4,6,7,1,5};
        System.out.println("Данные(Integer) до сортировки: " + Arrays.toString(array));
        quickSort(array, 0, array.length - 1, Comparator.naturalOrder());
        System.out.println("Данные(Integer) после сортировки: " + Arrays.toString(array));

        String[] array2 = {"a", "abcd", "cd", "bcd"};
        System.out.println("Данные(String) до сортировки: " + Arrays.toString(array2));
        quickSort(array2, 0 , array2.length - 1, Comparator.comparingInt(String::length));
        System.out.println("Данные(String) после сортировки: " + Arrays.toString(array2));
    }
}