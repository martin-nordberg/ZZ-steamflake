//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.parser.impl;

import org.steamflake.core.persistence.ioutilities.fileio.FileScanner;
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
        String tokenOpenDelimiter,
        String tokenCloseDelimiter
    ) {

        this.rule = rule;
        this.scanner = new FileScanner(
            ruleBody.getText(),
            ruleBody.getOrigin().getFileName(),
            ruleBody.getOrigin().getLine(),
            ruleBody.getOrigin().getColumn()
        );
        this.tokenOpenDelimiter = tokenOpenDelimiter;
        this.tokenCloseDelimiter = tokenCloseDelimiter;
    }

    /**
     * Parses the entire code of the rule body.
     *
     * @throws SteamflakeTmParser.SteamflakeTmParserException
     */
    public void parse() throws SteamflakeTmParser.SteamflakeTmParserException {

        try {
            this.parseRuleBody();
        }
        catch ( FileScanner.FileScannerException e ) {
            throw new SteamflakeTmParser.SteamflakeTmParserException( e.getMessage(), e.getOrigin(), e );
        }

    }

    private void parseRuleBody() throws FileScanner.FileScannerException,
                                        SteamflakeTmParser.SteamflakeTmParserException {

        Optional<FileScanner.Token> text = this.scanner.acceptUntil( this.tokenOpenDelimiter );

        while ( text.isPresent() ) {
            if ( !text.get().getText().isEmpty() ) {
                this.rule.addTextToken( of( text.get().getOrigin() ), text.get().getText() );
            }

            this.scanner.scan( this.tokenOpenDelimiter );

            FileScanner.Token tokenBody = this.scanner.scanUntil( this.tokenCloseDelimiter );

            this.scanner.scan( this.tokenCloseDelimiter );

            // TODO: Add the code token to the rule
            new SteamflakeTmTokenParser( this.rule, tokenBody ).parse();

            text = this.scanner.acceptUntil( this.tokenOpenDelimiter );
        }

        // Parse the remaining text into the final token for the rule.
        FileScanner.Token remainderText = this.scanner.scanUntilEndOfInput();

        if ( !remainderText.getText().isEmpty() ) {
            this.rule.addTextToken( of( remainderText.getOrigin() ), remainderText.getText() );
        }

    }

    /** The rule to which the parsed rule body tokens are to be added. */
    private final ISteamflakeTmRule rule;

    /** The scanner of template file input. */
    private final FileScanner scanner;

    private final String tokenCloseDelimiter;

    private final String tokenOpenDelimiter;
}
