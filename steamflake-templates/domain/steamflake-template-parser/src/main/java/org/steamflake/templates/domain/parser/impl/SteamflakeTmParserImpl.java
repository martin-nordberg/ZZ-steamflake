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
 * Implementation of Steamflake template parsing.
 */
public class SteamflakeTmParserImpl {

    /**
     * Constructs a new parser for the given input code and output model.
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
            throw new SteamflakeTmParser.SteamflakeTmParserException( e.getMessage(), e.getOrigin(), e );
        }

    }

    /**
     * Simple data structure to temporarily capture the attributes of an import declaration.
     */
    @SuppressWarnings( "InstanceVariableMayNotBeInitialized" )
    private static class ImportAttributes {

        private Optional<String> alias;

        private Optional<FileOrigin> origin;

        private String typeName;
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
     * Parses a documentation comment.
     *
     * @return the documentation parsed.
     */
    private Optional<String> parseDocumentation() {

        this.scanner.acceptWhitespace();

        // TODO: documentation
        return Optional.empty();

    }

    /**
     * Parses zero or more import declarations.
     *
     * @return an array of import declaration attributes for later addition to the template.
     *
     * @throws FileScanner.FileScannerException if the parsing fails.
     */
    private Collection<ImportAttributes> parseImports() throws FileScanner.FileScannerException {

        this.scanner.acceptWhitespace();

        // Start with an empty result.
        Collection<ImportAttributes> imps = new ArrayList<>();

        // Scan the keyword.
        Optional<FileScanner.Token> impToken = this.scanner.accept( "import" );

        // While there is another import found...
        while ( impToken.isPresent() ) {

            // Build the attributes.
            ImportAttributes imp = new ImportAttributes();
            imp.origin = Optional.of( impToken.get().getOrigin() );

            this.scanner.scanWhitespace();

            // Scan the name of the imported type.
            imp.typeName = this.parsePath();

            this.scanner.acceptWhitespace();

            // Scan the optional alias.
            imp.alias = Optional.empty();
            if ( this.scanner.accept( "as" ).isPresent() ) {
                this.scanner.scanWhitespace();
                imp.alias = Optional.of( this.scanner.scanIdentifier().getText() );
            }

            this.scanner.scan( ";" );

            imps.add( imp );

            this.scanner.acceptWhitespace();

            impToken = this.scanner.accept( "import" );

        }

        return imps;

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

        // Parse the package declaration to get the parent package.
        if ( this.scanner.accept( "package" ).isPresent() ) {
            parentPackage = this.parsePackageDecl();
        }

        // Parse the import declarations.
        Collection<ImportAttributes> imps = this.parseImports();

        // Parse any documentation.
        Optional<String> description = this.parseDocumentation();

        // Parse the template's accessibility.
        ESteamflakeAccessibility accessibility = this.parseAccessibility();

        // Parse the template's abstractness.
        ESteamflakeAbstractness abstractness = this.parseAbstractness();

        // Parse the template keyword.
        this.scanner.scan( "template" );
        this.scanner.scanWhitespace();

        // Parse the template name.
        FileScanner.Token templateToken = this.scanner.scanIdentifier();

        // TODO: base template
        Optional<ISteamflakeTmTemplate> baseTemplate = Optional.empty();

        // Build the result so far.
        ISteamflakeTmTemplate result = ( (ISteamflakeTmPackage) parentPackage ).addTemplate(
            Optional.of( templateToken.getOrigin() ),
            templateToken.getText(),
            description,
            accessibility,
            abstractness,
            baseTemplate
        );

        // Add the earlier imports.
        for ( ImportAttributes imp : imps ) {
            result.addImport( imp.origin, imp.typeName, imp.alias );
        }

        // Parse the template contents.
        this.parseTemplateBody( result );

        // Make sure there is nothing extraneous at the end.
        this.scanner.acceptWhitespace();
        this.scanner.scanEndOfInput();

        return result;
    }

    /**
     * Parses the body of a template (from "{" to "}", including the rules in between).
     *
     * @param template the template to add parsed rules to.
     *
     * @throws FileScanner.FileScannerException if a syntax error is encountered.
     */
    private void parseTemplateBody( ISteamflakeTmTemplate template ) throws FileScanner.FileScannerException {

        this.scanner.acceptWhitespace();

        this.scanner.scan( "{" );

        this.scanner.acceptWhitespace();

        while ( !this.scanner.accept( "}" ).isPresent() ) {

            // Parse any documentation.
            Optional<String> documentation = this.parseDocumentation();

            // Parse the rule's accessibility.
            ESteamflakeAccessibility accessibility = this.parseAccessibility();

            // Parse the rule's abstractness.
            ESteamflakeAbstractness abstractness = this.parseAbstractness();

            // Parse the rule keyword.
            this.scanner.scan( "rule" );
            this.scanner.scanWhitespace();

            // Parse the rule name.
            FileScanner.Token ruleToken = this.scanner.scanIdentifier();

            // Build the rule.
            ISteamflakeTmRule rule = template.addRule(
                Optional.of(
                    ruleToken.getOrigin()
                ),
                ruleToken.getText(),
                documentation,
                accessibility,
                abstractness
            );

            // TODO: Parse the parameter
            this.scanner.scan( "()" );

            this.scanner.acceptWhitespace();

            this.scanner.scan( "{{{" );  // TODO: flexible delimiters

            this.scanner.acceptWhitespace();  // TODO: parse rule tokens

            this.scanner.scan( "}}}" );  // TODO: matching end delimiter

            this.scanner.acceptWhitespace();

        }

    }

    /** The root package of the model to which the parsed template is to be added. */
    private final ISteamflakeTmRootPackage rootPackage;

    /** The scanner of template file input. */
    private final FileScanner scanner;

}
