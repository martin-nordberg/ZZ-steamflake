//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.parser.impl;

import org.steamflake.core.domain.base.model.api.elements.ESteamflakeAbstractness;
import org.steamflake.core.domain.base.model.api.elements.ESteamflakeAccessibility;
import org.steamflake.core.domain.base.model.api.utilities.IFileOrigin;
import org.steamflake.core.persistence.codeio.scanning.FileScanner;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmAbstractPackage;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmPackage;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmRootPackage;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmRule;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmTemplate;
import org.steamflake.templates.domain.parser.api.SteamflakeTmParser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.steamflake.templates.domain.parser.impl.SteamflakeTmParserUtil.originOf;
import static org.steamflake.templates.domain.parser.impl.SteamflakeTmParserUtil.parsePath;

/**
 * Implementation of Steamflake template parsing.
 */
public class SteamflakeTmTemplateParser {

    /**
     * Constructs a new parser for the given input code and output model.
     *
     * @param rootPackage the root of the model to parse the result into.
     * @param code        the code to parse.
     * @param fileName    the file name containing the code.
     */
    public SteamflakeTmTemplateParser( ISteamflakeTmRootPackage rootPackage, String code, String fileName ) {
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
            throw new SteamflakeTmParser.SteamflakeTmParserException( e.getMessage(), e );
        }

    }

    /**
     * Simple data structure to temporarily capture the attributes of an import declaration.
     */
    @SuppressWarnings( "InstanceVariableMayNotBeInitialized" )
    private static class ImportAttributes {

        private Optional<String> alias;

        private Optional<IFileOrigin> origin;

        private String typeName;
    }

    /**
     * Computes the mirror image of a rule opening delimiter.
     *
     * @param openDelimiter the opening delimiter.
     *
     * @return the mirror image closing delimiter.
     */
    private static String mirrorDelimiter( String openDelimiter ) {

        StringBuilder result = new StringBuilder( "" );

        for ( int i = openDelimiter.length() - 1; i >= 0; i -= 1 ) {
            switch ( openDelimiter.charAt( i ) ) {
                case '{':
                    result.append( '}' );
                    break;
                case '[':
                    result.append( ']' );
                    break;
                case '(':
                    result.append( ')' );
                    break;
                case '<':
                    result.append( '>' );
                    break;
                case '`':
                    result.append( '`' );
                    break;
                case '\'':
                    result.append( '\'' );
                    break;
                case '"':
                    result.append( '"' );
                    break;
                default:
                    throw new IllegalArgumentException(
                        "Unrecognized delimiter character: '" + openDelimiter
                            .charAt( i ) + "'."
                    );
            }
        }

        return result.toString();
    }

    /**
     * Parses an abstractness keyword.
     *
     * @return the abstractness parsed or concrete by default
     */
    private ESteamflakeAbstractness parseAbstractness() throws FileScanner.FileScannerException {

        this.scanner.acceptWhitespace();

        ESteamflakeAbstractness result = null;

        if ( this.scanner.accept( "abstract" ).isPresent() ) {
            result = ESteamflakeAbstractness.ABSTRACT;
        }
        else if ( this.scanner.accept( "concrete" ).isPresent() ) {
            result = ESteamflakeAbstractness.CONCRETE;
        }

        if ( result == null ) {
            result = ESteamflakeAbstractness.CONCRETE;
        }
        else {
            this.scanner.scanWhitespace();
        }

        return result;

    }

    /**
     * Parses an accessibility keyword.
     *
     * @return the accessibility parsed or module by default.
     */
    private ESteamflakeAccessibility parseAccessibility() throws FileScanner.FileScannerException {

        this.scanner.acceptWhitespace();

        ESteamflakeAccessibility result = null;

        if ( this.scanner.accept( "public" ).isPresent() ) {
            result = ESteamflakeAccessibility.PUBLIC;
        }
        else if ( this.scanner.accept( "protected" ).isPresent() ) {
            result = ESteamflakeAccessibility.PROTECTED;
        }
        else if ( this.scanner.accept( "module" ).isPresent() ) {
            result = ESteamflakeAccessibility.MODULE;
        }
        else if ( this.scanner.accept( "local" ).isPresent() ) {
            result = ESteamflakeAccessibility.LOCAL;
        }
        else if ( this.scanner.accept( "private" ).isPresent() ) {
            result = ESteamflakeAccessibility.PRIVATE;
        }

        if ( result == null ) {
            result = ESteamflakeAccessibility.MODULE;
        }
        else {
            this.scanner.scanWhitespace();
        }

        return result;

    }

    /**
     * Parses a documentation comment.
     *
     * @return the documentation parsed.
     */
    private Optional<String> parseDocumentation() {

        this.scanner.acceptWhitespace();

        // TODO: documentation
        return empty();

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
            imp.origin = originOf( impToken.get() );

            this.scanner.scanWhitespace();

            // Scan the name of the imported type.

            imp.typeName = parsePath( this.scanner );

            this.scanner.acceptWhitespace();

            // Scan the optional alias.
            imp.alias = empty();
            if ( this.scanner.accept( "as" ).isPresent() ) {
                this.scanner.scanWhitespace();
                imp.alias = of( this.scanner.scanIdentifier().getText() );
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

        String parentPackagePath = parsePath( this.scanner );

        this.scanner.scan( ";" );

        return this.rootPackage.findOrCreatePackage( parentPackagePath );

    }

    /**
     * Parses the parameters of a rule.
     *
     * @param rule the rule whose parameters are being parsed.
     *
     * @throws FileScanner.FileScannerException
     */
    private void parseParameters( ISteamflakeTmRule rule ) throws FileScanner.FileScannerException {

        this.scanner.scan( "(" );

        do {

            this.scanner.acceptWhitespace();

            FileScanner.Token paramName = this.scanner.scanIdentifier();

            this.scanner.acceptWhitespace();
            this.scanner.scan( ":" );
            this.scanner.acceptWhitespace();

            String paramTypeName = parsePath( this.scanner );

            rule.addParameter( originOf( paramName ), paramName.getText(), empty(), paramTypeName );

            this.scanner.acceptWhitespace();

        } while ( this.scanner.accept( "," ).isPresent() );

        this.scanner.scan( ")" );
        this.scanner.acceptWhitespace();

    }

    /**
     * Parses a template file to produce a new template in the model.
     *
     * @return the template parsed.
     *
     * @throws FileScanner.FileScannerException
     */
    private ISteamflakeTmTemplate parseTemplate()
        throws FileScanner.FileScannerException, SteamflakeTmParser.SteamflakeTmParserException {

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
        Optional<ISteamflakeTmTemplate> baseTemplate = empty();

        // Build the result so far.
        ISteamflakeTmTemplate result = ( (ISteamflakeTmPackage) parentPackage ).addTemplate(
            originOf( templateToken ),
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
    private void parseTemplateBody( ISteamflakeTmTemplate template )
        throws FileScanner.FileScannerException, SteamflakeTmParser.SteamflakeTmParserException {

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
                originOf( ruleToken ),
                ruleToken.getText(),
                documentation,
                accessibility,
                abstractness
            );

            // Parse the parameters
            this.parseParameters( rule );

            // Parse the opening delimiter of the rule.
            String ruleOpenDelimiter = this.scanner.scanRegex( SteamflakeTmTemplateParser.RULE_OPEN_DELIMITER_REGEX )
                                                   .getText();

            // Compute closing and token delimiters from the opening delimiter
            String ruleCloseDelimiter = SteamflakeTmTemplateParser.mirrorDelimiter( ruleOpenDelimiter );

            String tokenOpenDelimiter = ruleOpenDelimiter.substring( 0, 2 );
            String tokenCloseDelimiter = ruleCloseDelimiter.substring( 1, 3 );

            // Get the body of the rule as a chunk of text to be further parsed.
            FileScanner.Token ruleBody = this.scanner.scanUntil( ruleCloseDelimiter );

            // Parse the definition of the rule
            new SteamflakeTmRuleBodyParser( rule, ruleBody, tokenOpenDelimiter, tokenCloseDelimiter ).parse();

            // Parse the closing delimiter of the rule.
            this.scanner.scan( ruleCloseDelimiter );

            this.scanner.acceptWhitespace();

        }

    }

    /** Regular expression for the opening delimiter of a rule. */
    public static final String RULE_OPEN_DELIMITER_REGEX = "(\\{|\\[|\\(|<)(\\{|\\[|\\(|<)(\\{|\\[|\\(|<|'|\"|`)";

    /** The root package of the model to which the parsed template is to be added. */
    private final ISteamflakeTmRootPackage rootPackage;

    /** The scanner of template file input. */
    private final FileScanner scanner;

}
