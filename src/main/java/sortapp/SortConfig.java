package sortapp;

import java.util.List;

public class SortConfig {

    public enum SortDirection {
        ASCENDING,
        DESCENDING,
        IGNORE
    }

    public enum IntFilter {
        ALL,
        ODD,
        EVEN
    }

    private final List<SortCriterion> criteria;

    public SortConfig(SortDirection direction, IntFilter intFilter) {
        this.criteria = List.of(
                new SortCriterion(SortCriterion.Field.PRODUCTION_YEAR, direction, intFilter)
        );
    }

    public SortConfig(List<SortCriterion> criteria) {
        this.criteria = criteria;
    }

    public List<SortCriterion> getCriteria() {
        return criteria;
    }

    @Override
    public String toString() {
        return "SortConfig{criteria=" + criteria + "}";
    }
}
