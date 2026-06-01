package auto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import sorts.GSort;
import sorts.HeapSort;
import sorts.MergeSort;
import sorts.QuickSort;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AutoSortingTest — Sorting Autocomparable by field")
class AutoSortingTest {

    static Stream<Arguments> autoSortProviders() {
        return Stream.of(
                Arguments.of(new HeapSort()),
                Arguments.of(new MergeSort()),
                Arguments.of(new QuickSort())
        );
    }

    private static List<Auto> sampleAutos() {
        return List.of(
                new Auto("Toyota", 2020, 150.5),
                new Auto("Honda", 2018, 180.0),
                new Auto("BMW", 2022, 300.0),
                new Auto("Audi", 2020, 200.0),
                new Auto("Ford", 2015, 120.0),
                new Auto("Chevrolet", 2019, 250.0),
                new Auto("Honda", 2018, 150.0)
        );
    }

    // ---------- composite sort: all three fields ----------

    @ParameterizedTest
    @MethodSource("autoSortProviders")
    @DisplayName("sorts Auto list by model, then year, then power")
    void sortByAllThreeFields(GSort sorter) {
        List<Auto> list = new ArrayList<>(sampleAutos());
        List<Auto> expected = new ArrayList<>(List.of(
                new Auto("Audi", 2020, 200.0),
                new Auto("BMW", 2022, 300.0),
                new Auto("Chevrolet", 2019, 250.0),
                new Auto("Ford", 2015, 120.0),
                new Auto("Honda", 2018, 150.0),
                new Auto("Honda", 2018, 180.0),
                new Auto("Toyota", 2020, 150.5)
        ));
        List<Auto> result = sorter.execute(list,
                Comparator.comparing(Auto::getModel)
                        .thenComparing(Auto::getProductionYear)
                        .thenComparing(Auto::getPower));
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("autoSortProviders")
    @DisplayName("sorts Auto list by all three fields descending")
    void sortByAllThreeFieldsDesc(GSort sorter) {
        List<Auto> list = new ArrayList<>(sampleAutos());
        List<Auto> expected = new ArrayList<>(List.of(
                new Auto("Toyota", 2020, 150.5),
                new Auto("Honda", 2018, 180.0),
                new Auto("Honda", 2018, 150.0),
                new Auto("Ford", 2015, 120.0),
                new Auto("Chevrolet", 2019, 250.0),
                new Auto("BMW", 2022, 300.0),
                new Auto("Audi", 2020, 200.0)
        ));
        List<Auto> result = sorter.execute(list,
                Comparator.comparing(Auto::getModel, Comparator.reverseOrder())
                        .thenComparing(Auto::getProductionYear, Comparator.reverseOrder())
                        .thenComparing(Auto::getPower, Comparator.reverseOrder()));
        assertEquals(expected, result);
    }

    // ---------- edge cases ----------

    @ParameterizedTest
    @MethodSource("autoSortProviders")
    @DisplayName("sorts empty list")
    void sortEmpty(GSort sorter) {
        assertEquals(Collections.emptyList(), sorter.execute(new ArrayList<>(), Comparator.comparing(Auto::getModel)));
    }

    @ParameterizedTest
    @MethodSource("autoSortProviders")
    @DisplayName("sorts single element list")
    void sortSingleElement(GSort sorter) {
        List<Auto> list = new ArrayList<>(List.of(new Auto("Tesla", 2023, 500.0)));
        List<Auto> expected = new ArrayList<>(List.of(new Auto("Tesla", 2023, 500.0)));
        assertEquals(expected, sorter.execute(list, Comparator.comparing(Auto::getModel)));
    }

    @ParameterizedTest
    @MethodSource("autoSortProviders")
    @DisplayName("sorts two element list")
    void sortTwoElements(GSort sorter) {
        List<Auto> list = new ArrayList<>(List.of(
                new Auto("BMW", 2022, 300.0),
                new Auto("Audi", 2020, 200.0)
        ));
        List<Auto> expected = new ArrayList<>(List.of(
                new Auto("Audi", 2020, 200.0),
                new Auto("BMW", 2022, 300.0)
        ));
        assertEquals(expected, sorter.execute(list, Comparator.comparing(Auto::getModel)));
    }

    @ParameterizedTest
    @MethodSource("autoSortProviders")
    @DisplayName("does not mutate original list")
    void originalListUnchanged(GSort sorter) {
        List<Auto> original = new ArrayList<>(sampleAutos());
        List<Auto> preserved = new ArrayList<>(original);
        sorter.execute(original, Comparator.comparing(Auto::getModel));
        assertEquals(preserved, original);
    }

    @ParameterizedTest
    @MethodSource("autoSortProviders")
    @DisplayName("returns empty list when input is null")
    void sortNull(GSort sorter) {
        assertNotNull(sorter.execute(null, Comparator.comparing(Auto::getModel)));
        assertTrue(sorter.execute(null, Comparator.comparing(Auto::getModel)).isEmpty());
    }

    // ---------- large list ----------

    @ParameterizedTest
    @MethodSource("autoSortProviders")
    @DisplayName("sorts large Auto list by Power")
    void sortLargeAutoList(GSort sorter) {
        List<Auto> original = new ArrayList<>();
        for (int i = 100; i >= 1; i--) {
            original.add(new Auto("Model" + i, i, i * 10.0));
        }
        List<Auto> expected = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            expected.add(new Auto("Model" + i, i, i * 10.0));
        }
        assertEquals(expected, sorter.execute(new ArrayList<>(original), Comparator.comparing(Auto::getPower)));
    }

    @ParameterizedTest
    @MethodSource("autoSortProviders")
    @DisplayName("sorts large Auto list by year")
    void sortLargeAutoListByYear(GSort sorter) {
        List<Auto> original = new ArrayList<>();
        for (int i = 100; i >= 1; i--) {
            original.add(new Auto("Model" + i, i, i * 10.0));
        }
        List<Auto> expected = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            expected.add(new Auto("Model" + i, i, i * 10.0));
        }
        assertEquals(expected, sorter.execute(new ArrayList<>(original), Comparator.comparing(Auto::getProductionYear)));
    }
}
