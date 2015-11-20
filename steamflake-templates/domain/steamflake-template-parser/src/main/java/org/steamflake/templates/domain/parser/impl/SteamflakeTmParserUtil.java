//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.parser.impl;

import org.steamflake.core.persistence.ioutilities.fileio.FileScanner;

/**
 * Shared methods for Steamflake parsing.
 */
public final class SteamflakeTmParserUtil {

    private SteamflakeTmParserUtil() {
    }

    /**
     * Parses a path (a "."-separated sequence of identifiers).
     *
     * @param scanner the input scanner to read from.
     *
     * @return the path that has been read.
     *
     * @throws FileScanner.FileScannerException
     */
    static String parsePath( FileScanner scanner ) throws FileScanner.FileScannerException {

        StringBuilder result = new StringBuilder();

        // Scan the first identifier of the path.
        result.append( scanner.scanIdentifier().getText() );

        scanner.acceptWhitespace();

        // While the next character of input is ".", scan and append the next identifier of the result.
        while ( scanner.accept( "." ).isPresent() ) {

            result.append( "." );

            scanner.acceptWhitespace();

            result.append( scanner.scanIdentifier().getText() );

            scanner.acceptWhitespace();

        }

        return result.toString();

    }

}
