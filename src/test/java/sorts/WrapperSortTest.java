package sorts;


import auto.Auto;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WrapperSortTest {
    //keepEven = true - сортирует нечетные, четные остаются на местах
    private static Stream<Arguments> provideWrapperSorters_ODD() {
        return Stream.of(
                Arguments.of(new WrapperSort(new QuickSort(), true)),
                Arguments.of(new WrapperSort(new MergeSort(), true)),
                Arguments.of(new WrapperSort(new InsertionSort(), true)),
                Arguments.of(new WrapperSort(new SelectionSort(), true)),
                Arguments.of(new WrapperSort(new HeapSort(), true))
        );
    }
    //keepEven = false - сортирует четные, нечетные остаются на местах
    private static Stream<Arguments> provideWrapperSorters_EVEN() {
        return Stream.of(
                Arguments.of(new WrapperSort(new QuickSort(), false)),
                Arguments.of(new WrapperSort(new MergeSort(), false)),
                Arguments.of(new WrapperSort(new InsertionSort(), false)),
                Arguments.of(new WrapperSort(new SelectionSort(), false)),
                Arguments.of(new WrapperSort(new HeapSort(), false))
        );
    }
    @ParameterizedTest(name = "Integer сортировка нечетных (keepEven=true)")
    @MethodSource("provideWrapperSorters_ODD")
    void testSortOdd(WrapperSort sorter) {
        List<Integer> input = List.of(5, 4, 9, 6 , 7);
        List<Integer> expected = List.of(5, 4, 7, 6, 9);
        assertEquals(expected, sorter.execute(input));
    }
    @ParameterizedTest(name = "Integer сортировка четных (keepEven=false)")
    @MethodSource("provideWrapperSorters_EVEN")
    void testSortEven(WrapperSort sorter) {
        List<Integer> input = List.of(5, 6, 9 , 4, 7);
        List<Integer> expected = List.of(5, 4, 9, 6 , 7);
        assertEquals(expected, sorter.execute(input));
    }
    @ParameterizedTest(name = "Auto: фильтрация по productionYear (keepEven=true")
    @MethodSource("provideWrapperSorters_ODD")
    void testSortAuto(WrapperSort sorter) {
        List<Auto> input = List.of(
                new Auto("BMW", 2000, 100.0),
                new Auto("Audi", 1999, 100.0),
                new Auto("Mercedes", 2002, 100.0),
                new Auto("Opel", 1997, 100.0)
        );
        List<Auto> result = sorter.execute(input, Comparator.comparingInt(Auto::getProductionYear));
        List<Integer> years =result.stream()
                .map(Auto::getProductionYear)
                .collect(Collectors.toList());
        assertEquals(List.of(2000, 1997, 2002, 1999), years);
    }
}
