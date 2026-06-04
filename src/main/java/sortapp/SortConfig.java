package sortapp;

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

    private final SortDirection direction;
    private final IntFilter intFilter;

    public SortConfig(SortDirection direction, IntFilter intFilter) {
        this.direction = direction;
        this.intFilter = intFilter;
    }

    public SortDirection getDirection() {
        return direction;
    }

    public IntFilter getIntFilter() {
        return intFilter;
    }

    @Override
    public String toString() {
        return "SortConfig{direction=" + direction + ", intFilter=" + intFilter + "}";
    }
}
