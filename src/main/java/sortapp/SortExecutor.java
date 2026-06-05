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

        // Build comparator from criteria list
        List<SortCriterion> criteria = config.getCriteria();

        // Empty criteria defaults to ascending productionYear
        if (criteria.isEmpty()) {
            Comparator<Auto> comparator = Comparator.comparing(Auto::getProductionYear);
            return sorter.execute(input, comparator);
        }

        // Handle if all criteria are IGNORE — return input unchanged
        boolean allIgnore = criteria.stream().allMatch(c -> c.getDirection() == SortConfig.SortDirection.IGNORE);
        if (allIgnore) {
            return new ArrayList<>(input);
        }

        Comparator<Auto> comparator = buildComparator(criteria);

        // Sort using the sorter with the comparator
        List<Auto> sorted = sorter.execute(input, comparator);

        // IntFilter is UI-only (ODD/EVEN) - sorting proceeds as ALL
        // No selective sorting is implemented per the plan

        return sorted;
    }

    private Comparator<Auto> buildComparator(List<SortCriterion> criteria) {
        // Empty criteria defaults to ascending productionYear
        if (criteria.isEmpty()) {
            return Comparator.comparing(Auto::getProductionYear);
        }

        Comparator<Auto> comp = buildSingleComparator(criteria.get(0));

        for (int i = 1; i < criteria.size(); i++) {
            comp = comp.thenComparing(buildSingleComparator(criteria.get(i)));
        }

        return comp;
    }

    private Comparator<Auto> buildSingleComparator(SortCriterion c) {
        Comparator<Auto> comp = switch (c.getField()) {
            case MODEL -> Comparator.comparing(Auto::getModel);
            case PRODUCTION_YEAR -> Comparator.comparing(Auto::getProductionYear);
            case POWER -> Comparator.comparing(Auto::getPower);
        };

        if (c.getDirection() == SortConfig.SortDirection.DESCENDING) {
            comp = comp.reversed();
        }

        return comp;
    }
}
