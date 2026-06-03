package sorts;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("GSort — Polymorphic Sort Contract")
class GSortTest {

    static Stream<Arguments> sortProviders() {
        return Stream.of(
                Arguments.of(new HeapSort()),
                Arguments.of(new MergeSort()),
                Arguments.of(new QuickSort()),
                Arguments.of(new InsertionSort())
        );
    }

    @ParameterizedTest
    @MethodSource("sortProviders")
    @DisplayName("extends GSort")
    void extendsGSort(GSort sorter) {
        assertInstanceOf(GSort.class, sorter);
    }

    @ParameterizedTest
    @MethodSource("sortProviders")
    @DisplayName("sorts a pre-sorted list")
    void sortPreSorted(GSort sorter) {
        List<Integer> list = new ArrayList<>(List.of(1, 2, 3, 4, 5));
        List<Integer> expected = new ArrayList<>(List.of(1, 2, 3, 4, 5));
        List<Integer> result = sorter.execute(list);
        assertEquals(expected, result);
        assertEquals(expected, list); // original should remain unchanged
    }

    @ParameterizedTest
    @MethodSource("sortProviders")
    @DisplayName("sorts a reverse-sorted list")
    void sortReverseSorted(GSort sorter) {
        List<Integer> list = new ArrayList<>(List.of(5, 4, 3, 2, 1));
        List<Integer> expected = new ArrayList<>(List.of(1, 2, 3, 4, 5));
        assertEquals(expected, sorter.execute(list));
    }

    @ParameterizedTest
    @MethodSource("sortProviders")
    @DisplayName("sorts a list with duplicates")
    void sortWithDuplicates(GSort sorter) {
        List<Integer> list = new ArrayList<>(List.of(3, 1, 4, 1, 5, 9, 2, 6, 5, 3));
        List<Integer> expected = new ArrayList<>(List.of(1, 1, 2, 3, 3, 4, 5, 5, 6, 9));
        assertEquals(expected, sorter.execute(list));
    }

    @ParameterizedTest
    @MethodSource("sortProviders")
    @DisplayName("sorts a small list")
    void sortSmall(GSort sorter) {
        List<Integer> list = List.of(3, 1, 2);
        List<Integer> expected = List.of(1, 2, 3);
        assertEquals(expected, sorter.execute(new ArrayList<>(list)));
    }

    @ParameterizedTest
    @MethodSource("sortProviders")
    @DisplayName("sorts a list with two elements")
    void sortTwoElements(GSort sorter) {
        assertEquals(List.of(1, 2), sorter.execute(new ArrayList<>(List.of(2, 1))));
        assertEquals(List.of(1, 2), sorter.execute(new ArrayList<>(List.of(1, 2))));
    }

    @ParameterizedTest
    @MethodSource("sortProviders")
    @DisplayName("sorts a list with one element")
    void sortOneElement(GSort sorter) {
        assertEquals(List.of(42), sorter.execute(new ArrayList<>(List.of(42))));
    }

    @ParameterizedTest
    @MethodSource("sortProviders")
    @DisplayName("returns null when input is null")
    void sortNull(GSort sorter) {
        assertNull(sorter.execute(null));
    }

    @ParameterizedTest
    @MethodSource("sortProviders")
    @DisplayName("returns empty list when input is empty")
    void sortEmpty(GSort sorter) {
        assertEquals(Collections.emptyList(), sorter.execute(new ArrayList<Integer>()));
    }

    @ParameterizedTest
    @MethodSource("sortProviders")
    @DisplayName("sorts a large list")
    void sortLarge(GSort sorter) {
        List<Integer> original = new ArrayList<>();
        for (int i = 100; i >= 1; i--) {
            original.add(i);
        }
        List<Integer> expected = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            expected.add(i);
        }
        assertEquals(expected, sorter.execute(new ArrayList<>(original)));
    }

    @ParameterizedTest
    @MethodSource("sortProviders")
    @DisplayName("sorts negative numbers correctly")
    void sortNegativeNumbers(GSort sorter) {
        List<Integer> list = new ArrayList<>(List.of(-5, 3, -1, 0, 2, -10, 7));
        List<Integer> expected = new ArrayList<>(List.of(-10, -5, -1, 0, 2, 3, 7));
        assertEquals(expected, sorter.execute(list));
    }

    @ParameterizedTest
    @MethodSource("sortProviders")
    @DisplayName("does not mutate the original list")
    void originalListUnchanged(GSort sorter) {
        List<Integer> original = new ArrayList<>(List.of(4, 2, 7, 1, 5));
        List<Integer> preserved = new ArrayList<>(original);
        List<Integer> result = sorter.execute(original);
        assertEquals(preserved, original);
        assertEquals(new ArrayList<>(List.of(1, 2, 4, 5, 7)), result);
    }

    @ParameterizedTest
    @MethodSource("sortProviders")
    @DisplayName("sorts using comparator")
    void sortWithComparator(GSort sorter) {
        List<Integer> list = new ArrayList<>(List.of(3, 1, 4, 1, 5));
        List<Integer> expected = new ArrayList<>(List.of(5, 4, 3, 1, 1));
        assertEquals(expected, sorter.execute(list, Comparator.reverseOrder()));
    }

    @ParameterizedTest
    @MethodSource("sortProviders")
    @DisplayName("sorts strings using comparator")
    void sortStringsWithComparator(GSort sorter) {
        List<String> list = new ArrayList<>(List.of("banana", "apple", "cherry"));
        List<String> expected = new ArrayList<>(List.of("cherry", "banana", "apple"));
        assertEquals(expected, sorter.execute(list, Comparator.reverseOrder()));
    }
}
