//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.base.model.impl.utilities;

import org.steamflake.core.domain.base.model.api.utilities.IFileOrigin;

/**
 * Representation of a source location in a file.
 */
public class FileOrigin
    implements IFileOrigin {

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

    @Override
    public int getColumn() {
        return this.column;
    }

    @Override
    public String getFileName() {
        return this.fileName;
    }

    @Override
    public int getLine() {
        return this.line;
    }

    private final int column;

    private final String fileName;

    private final int line;

}
