import java.util.Arrays;

public class MergeSort {

    public static void mergeSort(int[] array) {
        if (array.length < 2) {
            return;
        }

        int mid = array.length / 2;

        int[] left = new int[mid];
        int[] right = new int[array.length - mid];

        // копируем левую часть
        for (int i = 0; i < mid; i++) {
            left[i] = array[i];
        }

        // копируем правую часть
        for (int i = mid; i < array.length; i++) {
            right[i - mid] = array[i];
        }

        // рекурсивно сортируем
        mergeSort(left);
        mergeSort(right);

        // слияние
        merge(array, left, right);
    }

    private static void merge(int[] array, int[] left, int[] right) {

        int i = 0;
        int j = 0;
        int k = 0;

        while (i < left.length && j < right.length) {

            if (left[i] <= right[j]) {
                array[k] = left[i];
                i++;
            } else {
                array[k] = right[j];
                j++;
            }

            k++;
        }

        // остатки left
        while (i < left.length) {
            array[k] = left[i];
            i++;
            k++;
        }

        // остатки right
        while (j < right.length) {
            array[k] = right[j];
            j++;
            k++;
        }
    }

    public static void main(String[] args) {

        int[] numbers = {9, 2, 1, 14, 22, 10, 5};

        System.out.println("До сортировки:");
        System.out.println(Arrays.toString(numbers));

        mergeSort(numbers);

        System.out.println("После сортировки:");
        System.out.println(Arrays.toString(numbers));
    }
}