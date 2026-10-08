
/*
 * File: Main.java
 * Project: Name Sorter
 * Author: Howard Barde
 *
 * Description:
 * Runs the name sorter application by reading names from
 * an input file, sorting them, and saving the results.
 */

package com.howard.namesorter;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Entry point for the name sorter application.
 *
 * Reads names from a text file, sorts them alphabetically
 * by last name and given names, then prints and saves
 * the results.
 */
public class Main {

    // All sorted results are saved in the output directory.
    private static final Path OUTPUT_FILE =
            Path.of("output/sorted-names-list.txt");

    /**
     * Runs the application using the input file provided
     * as a command-line argument.
     *
     * @param args command-line arguments containing the input file path
     */
    public static void main(String[] args) {

        if (args.length != 1) {
            System.err.println("Usage: name-sorter <input-file>");
            System.exit(1);
            return;
        }

        NameReader reader = new NameReader();

        NameSorter sorter = new DefaultNameSorter(
                NameComparators.BY_LAST_NAME
        );

        NameWriter writer = new NameWriter();

        try {
            List<Name> names = reader.read(Path.of(args[0]));

            List<Name> sortedNames = sorter.sort(names);

            // Print the sorted names to the terminal.
            sortedNames.stream()
                    .map(Name::getFullName)
                    .forEach(System.out::println);

            // Save the results in the output directory.
            writer.write(OUTPUT_FILE, sortedNames);

        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }
}
