//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.parser.impl;

import org.steamflake.core.domain.base.model.api.utilities.IFileOrigin;
import org.steamflake.core.persistence.codeio.scanning.FileScanner;
import org.steamflake.templates.domain.model.api.directives.ISteamflakeTmAbstractDirective;
import org.steamflake.templates.domain.model.api.directives.variables.ISteamflakeTmVariableDirective;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmDirectiveSequence;
import org.steamflake.templates.domain.parser.api.SteamflakeTmParser;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.steamflake.templates.domain.parser.impl.SteamflakeTmParserUtil.originOf;

/**
 * Implementation of Steamflake template rule token parsing.
 */
public class SteamflakeTmDirectiveParser {

    /**
     * Constructs a new parser for the given input token and output rule.
     *
     * @param container the rule to parse the result into.
     * @param tokenBody the code to parse.
     */
    public SteamflakeTmDirectiveParser(
        ISteamflakeTmDirectiveSequence container,
        FileScanner.Token tokenBody
    ) {

        this.container = container;
        this.scanner = new FileScanner(
            tokenBody.getText(),
            tokenBody.getFileName(),
            tokenBody.getLine(),
            tokenBody.getColumn()
        );
    }

    /**
     * Parses the entire code of the rule body.
     *
     * @throws SteamflakeTmParser.SteamflakeTmParserException
     */
    public ISteamflakeTmAbstractDirective parse() throws SteamflakeTmParser.SteamflakeTmParserException {

        try {
            return this.parseDirectiveBody();
        }
        catch ( FileScanner.FileScannerException e ) {
            throw new SteamflakeTmParser.SteamflakeTmParserException( e.getMessage(), e );
        }

    }

    private ISteamflakeTmAbstractDirective parseCommentDirective() {

        // Parse the comment's text.
        FileScanner.Token commentToken = this.scanner.scanUntilEndOfInput();

        // Add the token to the rule.
        return this.container.addCommentDirective( originOf( commentToken ), commentToken.getText() );

    }

    private ISteamflakeTmAbstractDirective parseDirectiveBody()
        throws FileScanner.FileScannerException, SteamflakeTmParser.SteamflakeTmParserException {

        ISteamflakeTmAbstractDirective result;

        if ( this.scanner.accept( "#" ).isPresent() ) {
            result = this.parseLogicDirective();
        }
        else if ( this.scanner.accept( "%" ).isPresent() ) {
            result = this.parseWhitespaceDirective();
        }
        else if ( this.scanner.accept( "@" ).isPresent() ) {
            result = this.parseParsingDirective();
        }
        else if ( this.scanner.accept( "!" ).isPresent() ) {
            result = this.parseCommentDirective();
        }
        else if ( this.scanner.accept( ">" ).isPresent() ) {
            result = this.parsePartialDirective();
        }
        else {
            result = this.parseVariableDirective();
        }

        this.scanner.scanEndOfInput();

        return result;
    }

    private ISteamflakeTmAbstractDirective parseLogicDirective()
        throws FileScanner.FileScannerException, SteamflakeTmParser.SteamflakeTmParserException {

        Optional<IFileOrigin> logicOrigin = originOf( this.scanner.scanNothing() );

        if ( this.scanner.accept( "if" ).isPresent() ) {
            this.scanner.scanWhitespace();
            String boolCondition = SteamflakeTmParserUtil.parsePath( this.scanner );
            this.scanner.acceptWhitespace();

            return this.container.addIfDirective( logicOrigin, boolCondition );
        }

        throw new SteamflakeTmParser.SteamflakeTmParserException(
            "Unrecognized logic directive.",
            this.scanner.scanNothing()
        );

    }

    private ISteamflakeTmAbstractDirective parseNewLineDirective( Optional<IFileOrigin> whitespaceOrigin )
        throws FileScanner.FileScannerException {

        boolean isSpaceNeededIfNoNewLine = this.scanner.accept( "_" ).isPresent();

        // If there is a "_" in the directive, then there must be a condition to test.
        if ( isSpaceNeededIfNoNewLine ) {
            this.scanner.scanWhitespace();
            String conditionPath = SteamflakeTmParserUtil.parsePath( this.scanner );
            this.scanner.acceptWhitespace();
            return this.container.addNewLineDirective( whitespaceOrigin, true, of( conditionPath ) );
        }

        // If there is a condition to test, then scan its path.
        if ( this.scanner.acceptWhitespace().isPresent() && !this.scanner.acceptEndOfInput().isPresent() ) {
            String conditionPath = SteamflakeTmParserUtil.parsePath( this.scanner );
            this.scanner.acceptWhitespace();
            return this.container.addNewLineDirective( whitespaceOrigin, false, of( conditionPath ) );
        }

        // Plain {{nl}} directive.
        return this.container.addNewLineDirective( whitespaceOrigin, false, empty() );

    }

    private ISteamflakeTmAbstractDirective parseParsingDirective() {
        // TODO
        return null;
    }

    private ISteamflakeTmAbstractDirective parsePartialDirective() {
        // TODO
        return null;
    }

    /**
     * Parses the body of a variable token.
     *
     * @throws FileScanner.FileScannerException if a syntax error occurs.
     */
    private ISteamflakeTmAbstractDirective parseVariableDirective() throws FileScanner.FileScannerException {

        this.scanner.acceptWhitespace();

        // Parse the variable's path.
        Optional<IFileOrigin> origin = originOf( this.scanner.scanNothing() );
        String path = SteamflakeTmParserUtil.parsePath( this.scanner );

        // Add the token to the rule.
        ISteamflakeTmVariableDirective result = this.container
            .addVariableDirective( origin, path );

        this.scanner.acceptWhitespace();

        return result;

    }

    private ISteamflakeTmAbstractDirective parseWhitespaceDirective()
        throws SteamflakeTmParser.SteamflakeTmParserException, FileScanner.FileScannerException {

        Optional<IFileOrigin> whitespaceOrigin = originOf( this.scanner.scanNothing() );

        if ( this.scanner.accept( "nl" ).isPresent() ) {
            return this.parseNewLineDirective( whitespaceOrigin );
        }

        throw new SteamflakeTmParser.SteamflakeTmParserException(
            "Unrecognized whitespace directive.",
            this.scanner.scanNothing()
        );

    }

    /** The rule to which the parsed rule body tokens are to be added. */
    private final ISteamflakeTmDirectiveSequence container;

    /** The scanner of template file input. */
    private final FileScanner scanner;

}
