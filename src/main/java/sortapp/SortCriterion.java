package sortapp;

public class SortCriterion {

    public enum Field {
        MODEL,
        PRODUCTION_YEAR,
        POWER
    }

    private final Field field;
    private final SortConfig.SortDirection direction;
    private final SortConfig.IntFilter intFilter;

    public SortCriterion(Field field, SortConfig.SortDirection direction, SortConfig.IntFilter intFilter) {
        this.field = field;
        this.direction = direction;
        this.intFilter = intFilter;
    }

    public Field getField() {
        return field;
    }

    public SortConfig.SortDirection getDirection() {
        return direction;
    }

    public SortConfig.IntFilter getIntFilter() {
        return intFilter;
    }

    @Override
    public String toString() {
        return "SortCriterion{field=" + field + ", direction=" + direction + ", intFilter=" + intFilter + "}";
    }
}
