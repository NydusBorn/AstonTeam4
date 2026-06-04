package sortapp;

import auto.Auto;
import sorts.GSort;
import sorts.HeapSort;
import sorts.InsertionSort;
import sorts.MergeSort;
import sorts.QuickSort;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("SortExecutor Tests")
class SortExecutorTest {

    private SortExecutor sortExecutor;

    static Stream<GSort> sortProviders() {
        return Stream.of(
                new HeapSort(),
                new MergeSort(),
                new QuickSort(),
                new InsertionSort()
        );
    }

    @BeforeEach
    void setUp() {
        sortExecutor = new SortExecutor();
    }

    @ParameterizedTest
    @MethodSource("sortProviders")
    @DisplayName("executes ascending sort correctly")
    void ascendingSort(GSort sorter) {
        List<Auto> input = List.of(
                new Auto("Car-Z", 2020, 150.0),
                new Auto("Car-A", 2000, 100.0),
                new Auto("Car-M", 2010, 120.0)
        );

        SortConfig config = new SortConfig(
                SortConfig.SortDirection.ASCENDING,
                SortConfig.IntFilter.ALL
        );

        List<Auto> result = sortExecutor.execute(input, config, sorter);

        assertEquals(2000, result.get(0).getProductionYear());
        assertEquals(2010, result.get(1).getProductionYear());
        assertEquals(2020, result.get(2).getProductionYear());
    }

    @ParameterizedTest
    @MethodSource("sortProviders")
    @DisplayName("executes descending sort correctly")
    void descendingSort(GSort sorter) {
        List<Auto> input = List.of(
                new Auto("Car-A", 2000, 100.0),
                new Auto("Car-Z", 2020, 150.0),
                new Auto("Car-M", 2010, 120.0)
        );

        SortConfig config = new SortConfig(
                SortConfig.SortDirection.DESCENDING,
                SortConfig.IntFilter.ALL
        );

        List<Auto> result = sortExecutor.execute(input, config, sorter);

        assertEquals(2020, result.get(0).getProductionYear());
        assertEquals(2010, result.get(1).getProductionYear());
        assertEquals(2000, result.get(2).getProductionYear());
    }

    @ParameterizedTest
    @MethodSource("sortProviders")
    @DisplayName("ignore direction preserves original order")
    void ignoreDirection(GSort sorter) {
        List<Auto> input = List.of(
                new Auto("Car-X", 2015, 130.0),
                new Auto("Car-Y", 2018, 140.0),
                new Auto("Car-Z", 2010, 120.0)
        );

        SortConfig config = new SortConfig(
                SortConfig.SortDirection.IGNORE,
                SortConfig.IntFilter.ALL
        );

        List<Auto> result = sortExecutor.execute(input, config, sorter);

        assertEquals(input, result);
    }

    @ParameterizedTest
    @MethodSource("sortProviders")
    @DisplayName("ALL filter sorts all entries")
    void allFilterSortsAll(GSort sorter) {
        List<Auto> input = List.of(
                new Auto("Car-C", 2015, 130.0),
                new Auto("Car-A", 2000, 100.0),
                new Auto("Car-B", 2005, 110.0)
        );

        SortConfig config = new SortConfig(
                SortConfig.SortDirection.ASCENDING,
                SortConfig.IntFilter.ALL
        );

        List<Auto> result = sortExecutor.execute(input, config, sorter);

        assertEquals(3, result.size());
        assertEquals(2000, result.get(0).getProductionYear());
        assertEquals(2005, result.get(1).getProductionYear());
        assertEquals(2015, result.get(2).getProductionYear());
    }

    @ParameterizedTest
    @MethodSource("sortProviders")
    @DisplayName("ODD filter is UI-only and sorts all entries")
    void oddFilterUIOnly(GSort sorter) {
        List<Auto> input = List.of(
                new Auto("Car-C", 2015, 130.0),
                new Auto("Car-A", 2000, 100.0),
                new Auto("Car-B", 2004, 110.0)
        );

        SortConfig config = new SortConfig(
                SortConfig.SortDirection.ASCENDING,
                SortConfig.IntFilter.ODD
        );

        // ODD filter should not affect behavior - should sort all as ALL
        List<Auto> result = sortExecutor.execute(input, config, sorter);

        assertEquals(3, result.size());
        assertEquals(2000, result.get(0).getProductionYear());
        assertEquals(2004, result.get(1).getProductionYear());
        assertEquals(2015, result.get(2).getProductionYear());
    }

    @ParameterizedTest
    @MethodSource("sortProviders")
    @DisplayName("EVEN filter is UI-only and sorts all entries")
    void evenFilterUIOnly(GSort sorter) {
        List<Auto> input = List.of(
                new Auto("Car-C", 2014, 130.0),
                new Auto("Car-A", 2001, 100.0),
                new Auto("Car-B", 2008, 110.0)
        );

        SortConfig config = new SortConfig(
                SortConfig.SortDirection.ASCENDING,
                SortConfig.IntFilter.EVEN
        );

        // EVEN filter should not affect behavior - should sort all as ALL
        List<Auto> result = sortExecutor.execute(input, config, sorter);

        assertEquals(3, result.size());
        assertEquals(2001, result.get(0).getProductionYear());
        assertEquals(2008, result.get(1).getProductionYear());
        assertEquals(2014, result.get(2).getProductionYear());
    }

    @ParameterizedTest
    @MethodSource("sortProviders")
    @DisplayName("handles empty input list")
    void emptyInput(GSort sorter) {
        List<Auto> input = List.of();

        SortConfig config = new SortConfig(
                SortConfig.SortDirection.ASCENDING,
                SortConfig.IntFilter.ALL
        );

        List<Auto> result = sortExecutor.execute(input, config, sorter);

        assertTrue(result.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("sortProviders")
    @DisplayName("handles null input")
    void nullInput(GSort sorter) {
        SortConfig config = new SortConfig(
                SortConfig.SortDirection.ASCENDING,
                SortConfig.IntFilter.ALL
        );

        List<Auto> result = sortExecutor.execute(null, config, sorter);

        assertTrue(result == null || result.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("sortProviders")
    @DisplayName("handles single entry")
    void singleEntry(GSort sorter) {
        List<Auto> input = List.of(
                new Auto("Car-A", 2020, 150.0)
        );

        SortConfig config = new SortConfig(
                SortConfig.SortDirection.ASCENDING,
                SortConfig.IntFilter.ALL
        );

        List<Auto> result = sortExecutor.execute(input, config, sorter);

        assertEquals(1, result.size());
        assertEquals(2020, result.get(0).getProductionYear());
    }

    @ParameterizedTest
    @MethodSource("sortProviders")
    @DisplayName("handles duplicates in productionYear")
    void duplicateYears(GSort sorter) {
        List<Auto> input = List.of(
                new Auto("Car-C", 2010, 130.0),
                new Auto("Car-A", 2010, 100.0),
                new Auto("Car-B", 2010, 110.0)
        );

        SortConfig config = new SortConfig(
                SortConfig.SortDirection.ASCENDING,
                SortConfig.IntFilter.ALL
        );

        List<Auto> result = sortExecutor.execute(input, config, sorter);

        assertEquals(3, result.size());
        assertEquals(2010, result.get(0).getProductionYear());
        assertEquals(2010, result.get(1).getProductionYear());
        assertEquals(2010, result.get(2).getProductionYear());
    }

    @Test
    @DisplayName("sort direction IGNORE with ODD filter still returns original order")
    void ignoreWithOddFilter() {
        List<Auto> input = List.of(
                new Auto("Car-X", 2015, 130.0),
                new Auto("Car-Y", 2020, 140.0),
                new Auto("Car-Z", 2018, 120.0)
        );

        SortConfig config = new SortConfig(
                SortConfig.SortDirection.IGNORE,
                SortConfig.IntFilter.ODD
        );

        GSort sorter = new HeapSort();
        List<Auto> result = sortExecutor.execute(input, config, sorter);

        assertEquals(input, result);
    }
}
