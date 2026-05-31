package sorts;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("HeapSort")
class HeapSortTest {

    private final HeapSort heapSort = new HeapSort();

    @Test
    @DisplayName("HeapSort extends GSort")
    void extendsGSort() {
        assertInstanceOf(GSort.class, heapSort);
    }

    @Test
    @DisplayName("sorts a pre-sorted list")
    void sortPreSorted() {
        List<Integer> list = new ArrayList<>(List.of(1, 2, 3, 4, 5));
        List<Integer> expected = new ArrayList<>(List.of(1, 2, 3, 4, 5));
        List<Integer> result = heapSort.execute(list);
        assertEquals(expected, result);
        assertEquals(expected, list); // original should remain unchanged
    }

    @Test
    @DisplayName("sorts a reverse-sorted list")
    void sortReverseSorted() {
        List<Integer> list = new ArrayList<>(List.of(5, 4, 3, 2, 1));
        List<Integer> expected = new ArrayList<>(List.of(1, 2, 3, 4, 5));
        assertEquals(expected, heapSort.execute(list));
    }

    @Test
    @DisplayName("sorts a list with duplicates")
    void sortWithDuplicates() {
        List<Integer> list = new ArrayList<>(List.of(3, 1, 4, 1, 5, 9, 2, 6, 5, 3));
        List<Integer> expected = new ArrayList<>(List.of(1, 1, 2, 3, 3, 4, 5, 5, 6, 9));
        assertEquals(expected, heapSort.execute(list));
    }

    @Test
    @DisplayName("sorts a small list")
    void sortSmall() {
        List<Integer> list = List.of(3, 1, 2);
        List<Integer> expected = List.of(1, 2, 3);
        assertEquals(expected, heapSort.execute(new ArrayList<>(list)));
    }

    @Test
    @DisplayName("sorts a list with two elements")
    void sortTwoElements() {
        assertEquals(List.of(1, 2), heapSort.execute(new ArrayList<>(List.of(2, 1))));
        assertEquals(List.of(1, 2), heapSort.execute(new ArrayList<>(List.of(1, 2))));
    }

    @Test
    @DisplayName("sorts a list with one element")
    void sortOneElement() {
        assertEquals(List.of(42), heapSort.execute(new ArrayList<>(List.of(42))));
    }

    @Test
    @DisplayName("returns null when input is null")
    void sortNull() {
        assertNull(heapSort.execute(null));
    }

    @Test
    @DisplayName("returns empty list when input is empty")
    void sortEmpty() {
        assertEquals(Collections.emptyList(), heapSort.execute(new ArrayList<Integer>()));
    }

    @Test
    @DisplayName("sorts a large list")
    void sortLarge() {
        List<Integer> original = new ArrayList<>();
        for (int i = 100; i >= 1; i--) {
            original.add(i);
        }
        List<Integer> expected = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            expected.add(i);
        }
        assertEquals(expected, heapSort.execute(new ArrayList<>(original)));
    }

    @Test
    @DisplayName("sorts negative numbers correctly")
    void sortNegativeNumbers() {
        List<Integer> list = new ArrayList<>(List.of(-5, 3, -1, 0, 2, -10, 7));
        List<Integer> expected = new ArrayList<>(List.of(-10, -5, -1, 0, 2, 3, 7));
        assertEquals(expected, heapSort.execute(list));
    }

    @Test
    @DisplayName("does not mutate the original list")
    void originalListUnchanged() {
        List<Integer> original = new ArrayList<>(List.of(4, 2, 7, 1, 5));
        List<Integer> preserved = new ArrayList<>(original);
        List<Integer> result = heapSort.execute(original);
        assertEquals(preserved, original);
        assertEquals(new ArrayList<>(List.of(1, 2, 4, 5, 7)), result);
    }

    @Test
    @DisplayName("sorts using comparator")
    void sortWithComparator() {
        List<Integer> list = new ArrayList<>(List.of(3, 1, 4, 1, 5));
        List<Integer> expected = new ArrayList<>(List.of(5, 4, 3, 1, 1));
        assertEquals(expected, heapSort.execute(list, Comparator.reverseOrder()));
    }

    @Test
    @DisplayName("sorts strings using comparator")
    void sortStringsWithComparator() {
        List<String> list = new ArrayList<>(List.of("banana", "apple", "cherry"));
        List<String> expected = new ArrayList<>(List.of("cherry", "banana", "apple"));
        assertEquals(expected, heapSort.execute(list, Comparator.reverseOrder()));
    }
}