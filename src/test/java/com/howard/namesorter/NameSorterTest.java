
/*
 * File: NameSorterTest.java
 * Project: Name Sorter
 * Author: Howard Barde
 *
 * Description:
 * Tests name sorting, input parsing, and validation
 * using JUnit 5.
 */

package com.howard.namesorter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the name sorter application.
 */
class NameSorterTest {

    private final NameSorter sorter = new DefaultNameSorter(
            NameComparators.BY_LAST_NAME
    );

    private final NameReader reader = new NameReader();

    @Test
    void sortsByLastName() {

        List<Name> names = List.of(
                new Name(List.of("Janet"), "Parsons"),
                new Name(List.of("Vaughn"), "Lewis"),
                new Name(List.of("Marin"), "Alvarez")
        );

        List<String> result = sorter.sort(names)
                .stream()
                .map(Name::getFullName)
                .toList();

        assertEquals(
                List.of(
                        "Marin Alvarez",
                        "Vaughn Lewis",
                        "Janet Parsons"
                ),
                result
        );
    }

    @Test
    void sortsGivenNamesWhenLastNamesMatch() {

        List<Name> names = List.of(
                new Name(List.of("Zoe"), "Smith"),
                new Name(List.of("Adam"), "Smith"),
                new Name(List.of("Adam", "James"), "Smith")
        );

        List<String> result = sorter.sort(names)
                .stream()
                .map(Name::getFullName)
                .toList();

        assertEquals(
                List.of(
                        "Adam Smith",
                        "Adam James Smith",
                        "Zoe Smith"
                ),
                result
        );
    }

    @Test
    void supportsThreeGivenNames() {

        Name name = new Name(
                List.of("Hunter", "Uriah", "Mathew"),
                "Clarke"
        );

        assertEquals("Clarke", name.getLastName());

        assertEquals(
                List.of("Hunter", "Uriah", "Mathew"),
                name.getGivenNames()
        );

        assertEquals(
                "Hunter Uriah Mathew Clarke",
                name.getFullName()
        );
    }

    @Test
    void handlesExtraSpaces(@TempDir Path tempDir)
            throws IOException {

        Path inputFile = tempDir.resolve("names.txt");

        Files.writeString(inputFile, "  Marin    Alvarez  ");

        List<Name> names = reader.read(inputFile);

        assertEquals(1, names.size());
        assertEquals("Marin Alvarez", names.get(0).getFullName());
    }

    @Test
    void rejectsNameWithOnlyOneWord(@TempDir Path tempDir)
            throws IOException {

        Path inputFile = tempDir.resolve("names.txt");

        Files.writeString(inputFile, "Alvarez");

        assertThrows(
                IllegalArgumentException.class,
                () -> reader.read(inputFile)
        );
    }

    @Test
    void rejectsNameWithTooManyWords() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Name(
                        List.of("One", "Two", "Three", "Four"),
                        "Five"
                )
        );
    }

    @Test
    void rejectsEmptyGivenNames() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Name(List.of(), "Smith")
        );
    }

    @Test
    void rejectsEmptyLastName() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Name(List.of("John"), "")
        );
    }

    @Test
    void supportsReverseSorting() {

        List<Name> names = List.of(
                new Name(List.of("Janet"), "Parsons"),
                new Name(List.of("Vaughn"), "Lewis"),
                new Name(List.of("Marin"), "Alvarez")
        );

        // Reverse the existing comparator rather than changing the sorter.
        NameSorter reverseSorter = new DefaultNameSorter(
                NameComparators.BY_LAST_NAME.reversed()
        );

        List<String> result = reverseSorter.sort(names)
                .stream()
                .map(Name::getFullName)
                .toList();

        assertEquals(
                List.of(
                        "Janet Parsons",
                        "Vaughn Lewis",
                        "Marin Alvarez"
                ),
                result
        );
    }

    @Test
    void supportsSortingByFirstGivenName() {

        List<Name> names = List.of(
                new Name(List.of("Zoe"), "Alvarez"),
                new Name(List.of("Adam"), "Parsons"),
                new Name(List.of("Marin"), "Lewis")
        );

        // A custom comparator lets us sort by first given name.
        Comparator<Name> byFirstGivenName =
                (first, second) -> first.getGivenNames().get(0)
                        .compareToIgnoreCase(
                                second.getGivenNames().get(0)
                        );

        NameSorter firstNameSorter = new DefaultNameSorter(
                byFirstGivenName
        );

        List<String> result = firstNameSorter.sort(names)
                .stream()
                .map(Name::getFullName)
                .toList();

        assertEquals(
                List.of(
                        "Adam Parsons",
                        "Marin Lewis",
                        "Zoe Alvarez"
                ),
                result
        );
    }
}
