//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.parser.api;

import org.steamflake.core.infrastructure.utilities.files.FileOrigin;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmRootPackage;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmTemplate;
import org.steamflake.templates.domain.parser.impl.SteamflakeTmParserImpl;

/**
 * Class parsing a Steamflake template into a template model.
 */
public final class SteamflakeTmParser {

    /**
     * Exception representing a parsing error.
     */
    public static class SteamflakeTmParserException
        extends Exception {

        public SteamflakeTmParserException( String message, FileOrigin origin ) {
            super( message );
            this.origin = origin;
        }

        public FileOrigin getOrigin() {
            return this.origin;
        }

        private static final long serialVersionUID = 1L;

        private final FileOrigin origin;
    }

    private SteamflakeTmParser() {
    }

    /**
     * Parses a steamflake template file.
     *
     * @param rootPackage the root package of the model the template is to be parsed into.
     * @param code        the code of the template to be parsed.
     * @param fileName    the name of the file from which the template has been read (for error messages).
     *
     * @return the parsed template model element.
     *
     * @throws SteamflakeTmParserException if the input is not a valid Steamflake template.
     */
    public static ISteamflakeTmTemplate parse( ISteamflakeTmRootPackage rootPackage, String code, String fileName )
        throws SteamflakeTmParserException {

        return new SteamflakeTmParserImpl( rootPackage, code, fileName ).parse();
    }

}
