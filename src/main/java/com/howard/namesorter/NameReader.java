
/*
 * File: NameReader.java
 * Project: Name Sorter
 * Author: Howard Barde
 *
 * Description:
 * Reads names from a text file and converts each valid line
 * into a Name object.
 */

package com.howard.namesorter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Reads a text file containing one full name per line.
 *
 * Blank lines are ignored, and extra whitespace between
 * names is handled automatically.
 */
public class NameReader {

    /**
     * Reads names from the specified file.
     *
     * @param inputFile path to the input file
     * @return names in the same order as the input file
     * @throws IOException if the file cannot be read
     * @throws IllegalArgumentException if a name is invalid
     */
    public List<Name> read(Path inputFile) throws IOException {

        List<Name> names = new ArrayList<>();

        for (String line : Files.readAllLines(inputFile)) {

            if (line.isBlank()) {
                continue;
            }

            // Splitting on whitespace handles multiple spaces and tabs.
            String[] parts = line.trim().split("\\s+");

            // The final word is the last name; the rest are given names.
            String lastName = parts[parts.length - 1];

            List<String> givenNames = Arrays.asList(
                    Arrays.copyOf(parts, parts.length - 1)
            );

            // Name handles validation before storing the values.
            names.add(new Name(givenNames, lastName));
        }

        return names;
    }
}
