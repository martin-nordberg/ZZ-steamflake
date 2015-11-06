//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.parser.impl;

import org.steamflake.core.domain.base.model.api.elements.ESteamflakeAbstractness;
import org.steamflake.core.domain.base.model.api.elements.ESteamflakeAccessibility;
import org.steamflake.core.persistence.ioutilities.fileio.FileScanner;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmAbstractPackage;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmPackage;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmRootPackage;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmTemplate;
import org.steamflake.templates.domain.parser.api.SteamflakeTmParser;

import java.util.Optional;

/**
 * Implementation of Steamflake template parsing
 */
public class SteamflakeTmParserImpl {

    /**
     * Consrtucts a new parser for the given input code and output model.
     *
     * @param rootPackage the root of the model to parse the result into.
     * @param code        the code to parse.
     * @param fileName    the file name containing the code.
     */
    public SteamflakeTmParserImpl( ISteamflakeTmRootPackage rootPackage, String code, String fileName ) {
        this.rootPackage = rootPackage;
        this.scanner = new FileScanner( code, fileName );
    }

    /**
     * Parses the entire code.
     *
     * @return the template found in the code.
     *
     * @throws SteamflakeTmParser.SteamflakeTmParserException
     */
    public ISteamflakeTmTemplate parse() throws SteamflakeTmParser.SteamflakeTmParserException {

        try {
            return this.parseTemplate();
        }
        catch ( FileScanner.FileScannerException e ) {
            throw new SteamflakeTmParser.SteamflakeTmParserException( e.getMessage(), e.getOrigin() );
        }

    }

    /**
     * Parses an abstractness keyword.
     *
     * @return the abstractness parsed or concrete by default
     */
    private ESteamflakeAbstractness parseAbstractness() throws FileScanner.FileScannerException {

        this.scanner.acceptWhitespace();

        for ( ESteamflakeAbstractness abstractness : ESteamflakeAbstractness.values() ) {
            if ( this.scanner.accept( abstractness.getKeyWord() ).isPresent() ) {
                this.scanner.scanWhitespace();
                return abstractness;
            }
        }

        return ESteamflakeAbstractness.CONCRETE;

    }

    /**
     * Parses an accessibility keyword.
     *
     * @return the accessibility parsed or module by default.
     */
    private ESteamflakeAccessibility parseAccessibility() throws FileScanner.FileScannerException {

        this.scanner.acceptWhitespace();

        for ( ESteamflakeAccessibility accessibility : ESteamflakeAccessibility.values() ) {
            if ( this.scanner.accept( accessibility.getKeyWord() ).isPresent() ) {
                this.scanner.scanWhitespace();
                return accessibility;
            }
        }

        return ESteamflakeAccessibility.MODULE;

    }

    /**
     * Parses a package declaration.
     *
     * @return the package parsed and created in the model.
     *
     * @throws FileScanner.FileScannerException if the package declaration has a syntax error.
     */
    private ISteamflakeTmAbstractPackage parsePackageDecl() throws FileScanner.FileScannerException {

        this.scanner.scanWhitespace();

        String parentPackagePath = this.parsePath();

        this.scanner.scan( ";" );

        return this.rootPackage.findOrCreatePackage( parentPackagePath );

    }

    /**
     * Parses a path (a "."-separated sequence of identifiers).
     *
     * @return the path that has been read.
     *
     * @throws FileScanner.FileScannerException
     */
    private String parsePath() throws FileScanner.FileScannerException {

        // Scan the first identifier of the path.
        StringBuilder path = new StringBuilder( this.scanner.scanIdentifier().getText() );

        this.scanner.acceptWhitespace();

        // While the next character of input is "." scan and append the next identifier of the path.
        while ( this.scanner.accept( "." ).isPresent() ) {

            path.append( "." );

            this.scanner.acceptWhitespace();

            path.append( this.scanner.scanIdentifier().getText() );

            this.scanner.acceptWhitespace();

        }

        return path.toString();

    }

    /**
     * Parses a template file to produce a new template in the model.
     *
     * @return the template parsed.
     *
     * @throws FileScanner.FileScannerException
     */
    private ISteamflakeTmTemplate parseTemplate()
        throws FileScanner.FileScannerException {

        this.scanner.acceptWhitespace();

        ISteamflakeTmAbstractPackage parentPackage = this.rootPackage;

        // Parse the package declaration to get the parent package
        if ( this.scanner.accept( "package" ).isPresent() ) {
            parentPackage = this.parsePackageDecl();
        }

        // TODO: Parse import declarations ...

        // TODO: documentation
        Optional<String> description = Optional.empty();

        this.scanner.acceptWhitespace();

        ESteamflakeAccessibility accessibility = this.parseAccessibility();

        ESteamflakeAbstractness abstractness = this.parseAbstractness();

        this.scanner.scan( "template" );
        this.scanner.scanWhitespace();

        FileScanner.Token templateToken = this.scanner.scanIdentifier();

        // TODO: base template
        Optional<ISteamflakeTmTemplate> baseTemplate = Optional.empty();

        this.scanner.acceptWhitespace();

        this.scanner.scan( "{" );

        // TODO: Parse the template contents ...
        this.scanner.acceptWhitespace();

        this.scanner.scan( "}" );

        this.scanner.acceptWhitespace();

        this.scanner.scanEndOfInput();

        return ( (ISteamflakeTmPackage) parentPackage ).addTemplate(
            Optional.of( templateToken.getOrigin() ),
            templateToken.getText(),
            description,
            accessibility,
            abstractness,
            baseTemplate
        );

    }

    private final ISteamflakeTmRootPackage rootPackage;

    private final FileScanner scanner;

}
