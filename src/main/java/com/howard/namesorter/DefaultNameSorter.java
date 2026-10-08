
/*
 * File: DefaultNameSorter.java
 * Project: Name Sorter
 * Author: Howard Barde
 *
 * Description:
 * Sorts names using a comparator supplied when the sorter
 * is created.
 */

package com.howard.namesorter;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Default implementation of NameSorter.
 *
 * Uses Java's built-in sorting functionality with a supplied
 * comparator, allowing different sorting rules to be used
 * without changing this class.
 */
public class DefaultNameSorter implements NameSorter {

    private final Comparator<Name> comparator;

    /**
     * Creates a sorter using the specified comparison rules.
     *
     * @param comparator rules used to compare two names
     * @throws NullPointerException if comparator is null
     */
    public DefaultNameSorter(Comparator<Name> comparator) {
        this.comparator = Objects.requireNonNull(
                comparator,
                "Comparator must not be null."
        );
    }

    /**
     * Sorts the names using the configured comparator.
     *
     * @param names names to sort
     * @return a new list in sorted order
     */
    @Override
    public List<Name> sort(List<Name> names) {
        return names.stream()
                .sorted(comparator)
                .toList();
    }
}
