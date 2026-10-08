
/*
 * File: NameComparators.java
 * Project: Name Sorter
 * Author: Howard Barde
 *
 * Description:
 * Provides the comparison rules used to sort names
 * alphabetically by last name and then given names.
 */

package com.howard.namesorter;

import java.util.Comparator;
import java.util.List;

/**
 * Provides comparison rules for Name objects.
 */
public final class NameComparators {

    private NameComparators() {
        // Utility class; no instances needed.
    }

    /**
     * Compares names alphabetically by last name, then by
     * each given name from left to right.
     *
     * Comparison is case-insensitive. If the shared given
     * names are identical, the shorter name comes first.
     */
    public static final Comparator<Name> BY_LAST_NAME =
            (first, second) -> {

        // Last names take priority when determining the order.
        int lastNameResult = first.getLastName()
                .compareToIgnoreCase(second.getLastName());

        if (lastNameResult != 0) {
            return lastNameResult;
        }

        // When last names match, compare the given names.
        List<String> firstGivenNames = first.getGivenNames();
        List<String> secondGivenNames = second.getGivenNames();

        int sharedCount = Math.min(
                firstGivenNames.size(),
                secondGivenNames.size()
        );

        for (int i = 0; i < sharedCount; i++) {

            int result = firstGivenNames.get(i)
                    .compareToIgnoreCase(secondGivenNames.get(i));

            if (result != 0) {
                return result;
            }
        }

        // For example, "Adam Smith" comes before "Adam James Smith".
        return Integer.compare(
                firstGivenNames.size(),
                secondGivenNames.size()
        );
    };
}
