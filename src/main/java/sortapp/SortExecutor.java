package sortapp;

import auto.Auto;
import sorts.GSort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortExecutor {

    public List<Auto> execute(List<Auto> input, SortConfig config, GSort sorter) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        // Handle IGNORE direction — return input unchanged
        if (config.getDirection() == SortConfig.SortDirection.IGNORE) {
            return new ArrayList<>(input);
        }

        // Build comparator based on direction
        Comparator<Auto> comparator = buildComparator(config.getDirection());

        // Sort using the sorter with the comparator
        List<Auto> sorted = sorter.execute(input, comparator);

        // IntFilter is UI-only (ODD/EVEN) - sorting proceeds as ALL
        // No selective sorting is implemented per the plan

        return sorted;
    }

    private Comparator<Auto> buildComparator(SortConfig.SortDirection direction) {
        return switch (direction) {
            case ASCENDING -> Comparator.comparing(Auto::getProductionYear);
            case DESCENDING -> Comparator.comparing(Auto::getProductionYear).reversed();
            default -> throw new IllegalArgumentException("Unexpected direction: " + direction);
        };
    }
}
