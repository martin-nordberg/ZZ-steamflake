//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.persistence.ioutilities.fileio;

/**
 * Representation of a source location in a file.
 */
public class FileOrigin {

    /**
     * Constructs a new file origin instance.
     *
     * @param fileName the file name where a model element or token came from.
     * @param line     the line number in the file.
     * @param column   the column number where the token started.
     */
    public FileOrigin( String fileName, int line, int column ) {
        this.column = column;
        this.fileName = fileName;
        this.line = line;
    }

    /**
     * @return the column number in the file where the model element was parsed from.
     */
    public int getColumn() {
        return this.column;
    }

    /**
     * @return the name of the file where the model element was parsed from.
     */
    public String getFileName() {
        return this.fileName;
    }

    /**
     * @return the line number in the file where the model element was parsed from.
     */
    public int getLine() {
        return this.line;
    }

    private final int column;

    private final String fileName;

    private final int line;

}
