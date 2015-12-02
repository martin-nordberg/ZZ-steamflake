//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.parser.impl;

import org.steamflake.core.persistence.ioutilities.fileio.FileScanner;
import org.steamflake.templates.domain.model.api.directives.ISteamflakeTmAbstractDirective;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmDirectiveContainer;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmRule;
import org.steamflake.templates.domain.parser.api.SteamflakeTmParser;

import java.util.Optional;

import static java.util.Optional.of;

/**
 * Implementation of Steamflake template rule body parsing.
 */
public class SteamflakeTmRuleBodyParser {

    /**
     * Constructs a new parser for the given input code and output rule.
     *
     * @param rule     the rule to parse the result into.
     * @param ruleBody the code to parse.
     */
    public SteamflakeTmRuleBodyParser(
        ISteamflakeTmRule rule,
        FileScanner.Token ruleBody,
        String directiveOpenDelimiter,
        String directiveCloseDelimiter
    ) {

        this.rule = rule;
        this.scanner = new FileScanner(
            ruleBody.getText(),
            ruleBody.getOrigin().getFileName(),
            ruleBody.getOrigin().getLine(),
            ruleBody.getOrigin().getColumn()
        );
        this.directiveOpenDelimiter = directiveOpenDelimiter;
        this.directiveCloseDelimiter = directiveCloseDelimiter;
    }

    /**
     * Parses the entire code of the rule body.
     *
     * @throws SteamflakeTmParser.SteamflakeTmParserException
     */
    public void parse() throws SteamflakeTmParser.SteamflakeTmParserException {

        try {
            this.parseDirectiveSequence( this.rule );
        }
        catch ( FileScanner.FileScannerException e ) {
            throw new SteamflakeTmParser.SteamflakeTmParserException( e.getMessage(), e.getOrigin(), e );
        }

    }

    /**
     * Parses the tokens in the body of a rule.
     *
     * @param container the rule or composite directive to contain the parsed results.
     *
     * @throws FileScanner.FileScannerException               if a syntax error is found.
     * @throws SteamflakeTmParser.SteamflakeTmParserException if a parsing error occurs.
     */
    private void parseDirectiveSequence( ISteamflakeTmDirectiveContainer container )
        throws FileScanner.FileScannerException,
               SteamflakeTmParser.SteamflakeTmParserException {

        // Look for text up to the next opening delimiter.
        Optional<FileScanner.Token> text = this.scanner.acceptUntil( this.directiveOpenDelimiter );

        // While there is text followed by an opening delimiter...
        while ( text.isPresent() ) {

            // Add the text to the rule if not empty.
            if ( !text.get().getText().isEmpty() ) {
                container.addTextDirective( of( text.get().getOrigin() ), text.get().getText() );
            }

            this.scanner.scan( this.directiveOpenDelimiter );

            // Handle a closing tag if present.
            if ( this.scanner.accept( "/" ).isPresent() ) {
                this.scanner.scan( container.getKeyword() );
                this.scanner.acceptWhitespace();
                this.scanner.scan( this.directiveCloseDelimiter );
                return;
            }

            // Scan the body of the directive.
            FileScanner.Token directiveBody = this.scanner.scanUntil( this.directiveCloseDelimiter );

            // Parse the code directive into the rule or composite directive.
            ISteamflakeTmAbstractDirective directive = new SteamflakeTmDirectiveParser(
                container,
                directiveBody
            ).parse();

            this.scanner.scan( this.directiveCloseDelimiter );

            // If it was a composite directive...
            if ( directive.isComposite() ) {
                // Recursively parse until the closing directive.
                this.parseDirectiveSequence( (ISteamflakeTmDirectiveContainer) directive );
            }

            // Scan the next block of text up to an opening delimiter.
            text = this.scanner.acceptUntil( this.directiveOpenDelimiter );

        }

        // Trigger an error if we found no closing directive for a composite directive.
        if ( !( container instanceof ISteamflakeTmRule ) ) {
            throw new SteamflakeTmParser.SteamflakeTmParserException(
                "Missing closing directive: '" + this.directiveOpenDelimiter + "/" + container.getKeyword() + this.directiveCloseDelimiter + "'.",
                this.scanner.getCurrentLocation()
            );
        }

        // Parse the remaining text, if any, into the final directive for the rule.
        FileScanner.Token remainderText = this.scanner.scanUntilEndOfInput();

        if ( !remainderText.getText().isEmpty() ) {
            container.addTextDirective( of( remainderText.getOrigin() ), remainderText.getText() );
        }

    }

    /** The delimiter that indicates the end of a code directive (usually "}}"). */
    private final String directiveCloseDelimiter;

    /** The delimiter that indicates the start of a code directive (usually "{{"). */
    private final String directiveOpenDelimiter;

    /** The rule to which the parsed rule body tokens are to be added. */
    private final ISteamflakeTmRule rule;

    /** The scanner of template file input. */
    private final FileScanner scanner;

}
