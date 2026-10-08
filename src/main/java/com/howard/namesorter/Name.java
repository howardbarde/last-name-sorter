
/*
 * File: Name.java
 * Project: Name Sorter
 * Author: Howard Barde
 *
 * Description:
 * Represents a person's name and validates its given names
 * and last name.
 */

package com.howard.namesorter;

import java.util.List;

/**
 * Represents a person's name, consisting of one to three given
 * names followed by a last name.
 *
 * Name objects are immutable once created.
 */
public class Name {

    private final List<String> givenNames;
    private final String lastName;

    /**
     * Creates a name using the supplied given names and last name.
     *
     * @param givenNames one to three given names
     * @param lastName the person's last name
     * @throws IllegalArgumentException if any part of the name is invalid
     */
    public Name(List<String> givenNames, String lastName) {

        if (givenNames == null ||
                givenNames.isEmpty() ||
                givenNames.size() > 3) {
            throw new IllegalArgumentException(
                    "A name must have between 1 and 3 given names."
            );
        }

        // Each given name must be a single, non-empty word.
        for (String givenName : givenNames) {
            if (givenName == null ||
                    givenName.isBlank() ||
                    givenName.trim().split("\\s+").length != 1) {
                throw new IllegalArgumentException(
                        "Each given name must be a single non-empty word."
                );
            }
        }

        if (lastName == null ||
                lastName.isBlank() ||
                lastName.trim().split("\\s+").length != 1) {
            throw new IllegalArgumentException(
                    "Last name must be a single non-empty word."
            );
        }

        // Copy the list so it cannot be changed from outside this object.
        this.givenNames = List.copyOf(givenNames);
        this.lastName = lastName;
    }

    /**
     * Returns the given names in their original order.
     *
     * @return an unmodifiable list of given names
     */
    public List<String> getGivenNames() {
        return givenNames;
    }

    /**
     * Returns the person's last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the full name with spaces between each part.
     *
     * @return the formatted full name
     */
    public String getFullName() {
        return String.join(" ", givenNames) + " " + lastName;
    }
}
