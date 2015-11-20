//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.parser.impl;

import org.steamflake.core.infrastructure.utilities.files.FileOrigin;
import org.steamflake.core.persistence.ioutilities.fileio.FileScanner;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmRule;
import org.steamflake.templates.domain.parser.api.SteamflakeTmParser;

import static java.util.Optional.of;

/**
 * Implementation of Steamflake template rule token parsing.
 */
public class SteamflakeTmTokenParser {

    /**
     * Constructs a new parser for the given input token and output rule.
     *
     * @param rule      the rule to parse the result into.
     * @param tokenBody the code to parse.
     */
    public SteamflakeTmTokenParser(
        ISteamflakeTmRule rule,
        FileScanner.Token tokenBody
    ) {

        this.rule = rule;
        this.scanner = new FileScanner(
            tokenBody.getText(),
            tokenBody.getOrigin().getFileName(),
            tokenBody.getOrigin().getLine(),
            tokenBody.getOrigin().getColumn()
        );
    }

    /**
     * Parses the entire code of the rule body.
     *
     * @throws SteamflakeTmParser.SteamflakeTmParserException
     */
    public void parse() throws SteamflakeTmParser.SteamflakeTmParserException {

        try {
            this.parseTokenBody();
        }
        catch ( FileScanner.FileScannerException e ) {
            throw new SteamflakeTmParser.SteamflakeTmParserException( e.getMessage(), e.getOrigin(), e );
        }

    }

    private void parseCodeToken() throws FileScanner.FileScannerException {
        this.scanner.acceptWhitespace();

        FileOrigin origin = this.scanner.getCurrentLocation();
        String path = SteamflakeTmParserUtil.parsePath( this.scanner );

        this.rule.addVariableToken( of( origin ), path );

        this.scanner.acceptWhitespace();

        this.scanner.scanEndOfInput();
    }

    private void parseCommentToken() {
        // TODO
    }

    private void parseLogicToken() {
        // TODO
    }

    private void parsePartialToken() {
        // TODO
    }

    private void parseTokenBody() throws FileScanner.FileScannerException {

        if ( this.scanner.accept( "#" ).isPresent() ) {
            this.parseLogicToken();
        }
        else if ( this.scanner.accept( "%" ).isPresent() ) {
            this.parseWhitespaceToken();
        }
        else if ( this.scanner.accept( "!" ).isPresent() ) {
            this.parseCommentToken();
        }
        else if ( this.scanner.accept( ">" ).isPresent() ) {
            this.parsePartialToken();
        }
        else {
            this.parseCodeToken();
        }

    }

    private void parseWhitespaceToken() {
        // TODO
    }

    /** The rule to which the parsed rule body tokens are to be added. */
    private final ISteamflakeTmRule rule;

    /** The scanner of template file input. */
    private final FileScanner scanner;

}
