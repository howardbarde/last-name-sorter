
/*
 * File: NameWriter.java
 * Project: Name Sorter
 * Author: Howard Barde
 *
 * Description:
 * Writes a collection of names to a text file, with one
 * full name per line.
 */

package com.howard.namesorter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Writes names to a text file.
 *
 * Creates missing parent directories when needed and
 * replaces the file's contents if it already exists.
 */
public class NameWriter {

    /**
     * Writes the supplied names to the specified file.
     *
     * @param outputFile destination file
     * @param names names to write
     * @throws IOException if the file cannot be written
     */
    public void write(Path outputFile, List<Name> names)
            throws IOException {

        List<String> lines = names.stream()
                .map(Name::getFullName)
                .toList();

        Path parentDirectory = outputFile.getParent();

        // Files in the working directory may not have a parent path.
        if (parentDirectory != null) {
            Files.createDirectories(parentDirectory);
        }

        Files.write(outputFile, lines);
    }
}
