
/*
 * File: NameSorter.java
 * Project: Name Sorter
 * Author: Howard Barde
 *
 * Description:
 * Defines the contract for sorting a collection of names.
 */

package com.howard.namesorter;

import java.util.List;

/**
 * Defines the sorting operation for a list of names.
 *
 * Implementations decide how the names are ordered.
 */
public interface NameSorter {

    /**
     * Returns the names in sorted order without modifying
     * the original list.
     *
     * @param names names to sort
     * @return a new list containing the sorted names
     */
    List<Name> sort(List<Name> names);
}
