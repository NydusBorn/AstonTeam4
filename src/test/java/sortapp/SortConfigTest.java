package sortapp;

import org.junit.jupiter.api.*;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("SortConfig Tests")
class SortConfigTest {

    @Test
    @DisplayName("creates config with ascending direction and ALL filter")
    void ascendingAllConfig() {
        SortConfig config = new SortConfig(
                SortConfig.SortDirection.ASCENDING,
                SortConfig.IntFilter.ALL
        );

        assertEquals(SortConfig.SortDirection.ASCENDING, config.getDirection());
        assertEquals(SortConfig.IntFilter.ALL, config.getIntFilter());
    }

    @Test
    @DisplayName("creates config with descending direction and ODD filter")
    void descendingOddConfig() {
        SortConfig config = new SortConfig(
                SortConfig.SortDirection.DESCENDING,
                SortConfig.IntFilter.ODD
        );

        assertEquals(SortConfig.SortDirection.DESCENDING, config.getDirection());
        assertEquals(SortConfig.IntFilter.ODD, config.getIntFilter());
    }

    @Test
    @DisplayName("creates config with ignore direction and EVEN filter")
    void ignoreEvenConfig() {
        SortConfig config = new SortConfig(
                SortConfig.SortDirection.IGNORE,
                SortConfig.IntFilter.EVEN
        );

        assertEquals(SortConfig.SortDirection.IGNORE, config.getDirection());
        assertEquals(SortConfig.IntFilter.EVEN, config.getIntFilter());
    }

    @Test
    @DisplayName("toString contains direction and filter")
    void toStringContainsInfo() {
        SortConfig config = new SortConfig(
                SortConfig.SortDirection.ASCENDING,
                SortConfig.IntFilter.ALL
        );

        String str = config.toString();
        assertTrue(str.contains("ASCENDING"));
        assertTrue(str.contains("ALL"));
    }

    @Test
    @DisplayName("direction enum has all expected values")
    void directionEnumHasAllValues() {
        SortConfig.SortDirection[] directions = SortConfig.SortDirection.values();
        assertEquals(3, directions.length);
        assertTrue(Arrays.asList(directions).contains(SortConfig.SortDirection.ASCENDING));
        assertTrue(Arrays.asList(directions).contains(SortConfig.SortDirection.DESCENDING));
        assertTrue(Arrays.asList(directions).contains(SortConfig.SortDirection.IGNORE));
    }

    @Test
    @DisplayName("intFilter enum has all expected values")
    void intFilterEnumHasAllValues() {
        SortConfig.IntFilter[] filters = SortConfig.IntFilter.values();
        assertEquals(3, filters.length);
        assertTrue(Arrays.asList(filters).contains(SortConfig.IntFilter.ALL));
        assertTrue(Arrays.asList(filters).contains(SortConfig.IntFilter.ODD));
        assertTrue(Arrays.asList(filters).contains(SortConfig.IntFilter.EVEN));
    }
}
