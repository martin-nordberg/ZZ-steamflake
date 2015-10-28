//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.parser.api;

import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmTemplate;

import java.io.Reader;

/**
 * Class parsing a Steamflake template into a template model.
 */
public interface IParser {

    /**
     * Parses a steamflake template file.
     *
     * @param reader   a reader for the code of the template.
     * @param fileName the name of the file from which the template has been read (for error messages).
     *
     * @return the parsed template model element.
     */
    ISteamflakeTmTemplate parse( Reader reader, String fileName );

}
