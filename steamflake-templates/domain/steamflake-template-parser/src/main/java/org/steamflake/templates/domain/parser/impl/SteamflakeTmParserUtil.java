//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.parser.impl;

import org.steamflake.core.domain.base.model.api.elements.ESteamflakeAbstractness;
import org.steamflake.core.domain.base.model.api.elements.ESteamflakeAccessibility;
import org.steamflake.core.infrastructure.utilities.files.FileOrigin;
import org.steamflake.core.persistence.ioutilities.fileio.FileScanner;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmAbstractPackage;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmPackage;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmRootPackage;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmRule;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmTemplate;
import org.steamflake.templates.domain.parser.api.SteamflakeTmParser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * Shared methods for Steamflake parsing.
 */
public final class SteamflakeTmParserUtil {

    private SteamflakeTmParserUtil() {
    }

    /**
     * Parses a path (a "."-separated sequence of identifiers).
     *
     * @return the path that has been read.
     *
     * @throws FileScanner.FileScannerException
     */
    static String parsePath( FileScanner scanner ) throws FileScanner.FileScannerException {

        // Scan the first identifier of the path.
        StringBuilder result = new StringBuilder( scanner.scanIdentifier().getText() );

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
