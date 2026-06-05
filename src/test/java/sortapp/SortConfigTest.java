package sortapp;

import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("SortConfig Tests")
class SortConfigTest {

    @Test
    @DisplayName("creates config with ascending direction and ALL filter via legacy constructor")
    void ascendingAllConfig() {
        SortConfig config = new SortConfig(
                SortConfig.SortDirection.ASCENDING,
                SortConfig.IntFilter.ALL
        );

        List<SortCriterion> criteria = config.getCriteria();
        assertEquals(1, criteria.size());

        SortCriterion criterion = criteria.get(0);
        assertEquals(SortCriterion.Field.PRODUCTION_YEAR, criterion.getField());
        assertEquals(SortConfig.SortDirection.ASCENDING, criterion.getDirection());
        assertEquals(SortConfig.IntFilter.ALL, criterion.getIntFilter());
    }

    @Test
    @DisplayName("creates config with descending direction and ODD filter via legacy constructor")
    void descendingOddConfig() {
        SortConfig config = new SortConfig(
                SortConfig.SortDirection.DESCENDING,
                SortConfig.IntFilter.ODD
        );

        List<SortCriterion> criteria = config.getCriteria();
        assertEquals(1, criteria.size());

        SortCriterion criterion = criteria.get(0);
        assertEquals(SortCriterion.Field.PRODUCTION_YEAR, criterion.getField());
        assertEquals(SortConfig.SortDirection.DESCENDING, criterion.getDirection());
        assertEquals(SortConfig.IntFilter.ODD, criterion.getIntFilter());
    }

    @Test
    @DisplayName("creates config with ignore direction and EVEN filter via legacy constructor")
    void ignoreEvenConfig() {
        SortConfig config = new SortConfig(
                SortConfig.SortDirection.IGNORE,
                SortConfig.IntFilter.EVEN
        );

        List<SortCriterion> criteria = config.getCriteria();
        assertEquals(1, criteria.size());

        SortCriterion criterion = criteria.get(0);
        assertEquals(SortCriterion.Field.PRODUCTION_YEAR, criterion.getField());
        assertEquals(SortConfig.SortDirection.IGNORE, criterion.getDirection());
        assertEquals(SortConfig.IntFilter.EVEN, criterion.getIntFilter());
    }

    @Test
    @DisplayName("creates config with criteria list")
    void configWithCriteriaList() {
        List<SortCriterion> criteria = List.of(
                new SortCriterion(SortCriterion.Field.MODEL, SortConfig.SortDirection.ASCENDING, SortConfig.IntFilter.ALL),
                new SortCriterion(SortCriterion.Field.PRODUCTION_YEAR, SortConfig.SortDirection.DESCENDING, SortConfig.IntFilter.ODD)
        );
        SortConfig config = new SortConfig(criteria);

        assertEquals(2, config.getCriteria().size());
        assertEquals(SortCriterion.Field.MODEL, config.getCriteria().get(0).getField());
        assertEquals(SortCriterion.Field.PRODUCTION_YEAR, config.getCriteria().get(1).getField());
    }

    @Test
    @DisplayName("toString contains criteria info")
    void toStringContainsInfo() {
        SortConfig config = new SortConfig(List.of(
                new SortCriterion(SortCriterion.Field.PRODUCTION_YEAR, SortConfig.SortDirection.ASCENDING, SortConfig.IntFilter.ALL)
        ));

        String str = config.toString();
        assertTrue(str.contains("criteria"));
        assertTrue(str.contains("PRODUCTION_YEAR"));
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

    @Test
    @DisplayName("field enum has all expected values")
    void fieldEnumHasAllValues() {
        SortCriterion.Field[] fields = SortCriterion.Field.values();
        assertEquals(3, fields.length);
        assertTrue(Arrays.asList(fields).contains(SortCriterion.Field.MODEL));
        assertTrue(Arrays.asList(fields).contains(SortCriterion.Field.PRODUCTION_YEAR));
        assertTrue(Arrays.asList(fields).contains(SortCriterion.Field.POWER));
    }
}
