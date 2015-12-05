//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.persistence.codeio.scanning;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implementation of a scanner designed for use by a recursive descent parser.
 */
@SuppressWarnings( { "ImplicitNumericConversion", "HardcodedLineSeparator" } )
public class FileScanner {

    /**
     * Exception thrown for file scanner errors.
     */
    public static final class FileScannerException
        extends Exception {

        private FileScannerException( String message, String fileName, int line, int column ) {
            super( message );
            this.fileName = fileName;
            this.line = line;
            this.column = column;
        }

        public int getColumn() {
            return this.column;
        }

        public String getFileName() {
            return this.fileName;
        }

        public int getLine() {
            return this.line;
        }

        private static final long serialVersionUID = 1;

        private final int column;

        private final String fileName;

        private final int line;

    }

    /**
     * Class representing a token returned from a scanner - its text and its origin.
     */
    public static final class Token {

        private Token( String text, String fileName, int line, int column ) {
            this.text = text;
            this.fileName = fileName;
            this.line = line;
            this.column = column;
        }

        public int getColumn() {
            return this.column;
        }

        public String getFileName() {
            return this.fileName;
        }

        public int getLine() {
            return this.line;
        }

        public String getText() {
            return this.text;
        }

        private final int column;

        private final String fileName;

        private final int line;

        private final String text;

    }

    /**
     * Constructs a new file scanner.
     *
     * @param text     the entire input to scan.
     * @param fileName the associated file name for the input.
     */
    public FileScanner( String text, String fileName ) {
        this( text, fileName, 1, 1 );
    }

    /**
     * Constructs a new file scanner.
     *
     * @param text     the entire input to scan.
     * @param fileName the associated file name for the input.
     */
    public FileScanner( String text, String fileName, int firstLine, int firstColumn ) {

        // Validate the arguments.
        if ( FileScanner.isNullOrEmpty( text ) ) {
            throw new IllegalArgumentException( "Input text must be non-empty." );
        }
        if ( FileScanner.isNullOrEmpty( fileName ) ) {
            throw new IllegalArgumentException( "File name must be non-empty." );
        }

        this.fileName = fileName;
        this.text = text;

        this.state = new FileScannerState( firstLine, firstColumn, 0, Optional.empty() );

    }

    /**
     * Tries to read the given token from the current location in the input.
     *
     * @param token the token that may be present in the input.
     *
     * @return the text and origin of the token if it has been read successfully.
     */
    public Optional<Token> accept( String token ) { //throws FileScannerException {

        // Validate the argument.
        if ( FileScanner.isNullOrEmpty( token ) ) {
            throw new IllegalArgumentException( "Token must be non-empty." );
        }
        if ( token.indexOf( '\n' ) >= 0 ) {
            throw new IllegalArgumentException(
                "Token may not contain a new line character: '" +
                    FileScanner.whitespaceEscaped( token ) + "'"
            );
        }

        final int endIndex = this.state.nextCharIndex + token.length();

        // If there are not enough characters available, then quit early.
        if ( endIndex > this.text.length() ) {
            return Optional.empty();
        }

        // Compare the input to the token character by character.
        if ( !token.equals( this.text.substring( this.state.nextCharIndex, endIndex ) ) ) {
            return Optional.empty();
        }

        // Capture the result before advancing.
        Optional<Token> result = Optional.of( new Token( token, this.fileName, this.state.line, this.state.column ) );

        // Move the column and current location.
        this.state.column += token.length();
        this.state.nextCharIndex += token.length();

        return result;
    }

    /**
     * Accepts the end of input.
     *
     * @return the file location of the end of input if recognized.
     */
    public Optional<Token> acceptEndOfInput() {

        if ( !this.canRead() ) {
            return Optional.of( new Token( "", this.fileName, this.state.line, this.state.column ) );
        }

        return Optional.empty();

    }

    /**
     * Tries to read a Java-compatible identifier.
     *
     * @return the identifier that was scanned.
     */
    public Optional<Token> acceptIdentifier() {

        // Quit early if there is no more input.
        if ( !this.canRead() ) {
            return Optional.empty();
        }

        // Read the first character.
        int index = this.state.nextCharIndex;
        char idChar = this.text.charAt( index );

        // Check for an acceptable starting character.
        if ( !Character.isJavaIdentifierStart( idChar ) ) {
            return Optional.empty();
        }

        // Read the second character or use a space for EOI.
        index += 1;
        if ( index < this.text.length() ) {
            idChar = this.text.charAt( index );
        }
        else {
            idChar = ' ';
        }

        // Loop for as long as we see more identifier characters.
        while ( Character.isJavaIdentifierPart( idChar ) ) {
            index += 1;

            if ( index >= this.text.length() ) {
                break;
            }

            idChar = this.text.charAt( index );
        }

        // Pack up the result.
        Optional<Token> result = Optional.of(
            new Token(
                this.text.substring(
                    this.state.nextCharIndex,
                    index
                ),
                this.fileName, this.state.line, this.state.column
            )
        );

        // Move the column and current location.
        this.state.column += index - this.state.nextCharIndex;
        this.state.nextCharIndex = index;

        return result;

    }

    /**
     * Tries to read the given regular expression from the current location in the input.
     *
     * @param regex the token that may be present in the input.
     *
     * @return the text and origin of the token if it has been read successfully.
     */
    public Optional<Token> acceptRegex( String regex ) { //throws FileScannerException {

        // Validate the argument.
        if ( FileScanner.isNullOrEmpty( regex ) ) {
            throw new IllegalArgumentException( "Regular expression must be non-empty." );
        }
        if ( regex.indexOf( '\n' ) >= 0 ) {
            throw new IllegalArgumentException(
                "Regular expression may not contain a new line character: '" +
                    FileScanner.whitespaceEscaped( regex ) + "'"
            );
        }

        Pattern regexPattern = Pattern.compile( "^" + regex );

        Matcher matcher = regexPattern.matcher( this.text.subSequence( this.state.nextCharIndex, this.text.length() ) );

        if ( !matcher.find() ) {
            return Optional.empty();
        }

        String token = matcher.group( 0 );

        // Capture the result before advancing.
        Optional<Token> result = Optional.of( new Token( token, this.fileName, this.state.line, this.state.column ) );

        // Move the column and current location.
        this.state.column += token.length();
        this.state.nextCharIndex += token.length();

        return result;
    }

    /**
     * Consumes the input from the current location up to but excluding the given delimiter.
     *
     * @param delimiter the delimiter to look for (must be non-empty).
     *
     * @return the origin and text from the current location up to but excluding the delimiter.
     *
     * @throws IllegalArgumentException if the delimiter is null or empty.
     */
    public Optional<Token> acceptUntil( String delimiter ) {

        // Validate the argument.
        if ( FileScanner.isNullOrEmpty( delimiter ) ) {
            throw new IllegalArgumentException( "Delimiter must be non-empty." );
        }

        // Quit early if there is no more input.
        if ( !this.canRead() ) {
            return Optional.empty();
        }

        // Search for the delimiter.
        int endIndex = this.text.indexOf( delimiter, this.state.nextCharIndex );

        // Return an empty result if not found.
        if ( endIndex < 0 ) {
            return Optional.empty();
        }

        // Pack up the result.
        Optional<Token> result = Optional.of(
            new Token(
                this.text.substring(
                    this.state.nextCharIndex,
                    endIndex
                ),
                this.fileName, this.state.line, this.state.column
            )
        );

        // Advance through the recognized text, counting lines and columns.
        for ( int i = this.state.nextCharIndex; i < endIndex; i += 1 ) {

            if ( this.text.charAt( i ) == '\n' ) {
                this.state.line += 1;
                this.state.column = 1;
            }
            else {
                this.state.column += 1;
            }

        }

        // Move the current location.
        this.state.nextCharIndex = endIndex;

        return result;

    }

    /**
     * Tries to read any amount of whitespace starting with the next available character.
     *
     * @return the whitespace that was found and consumed.
     */
    public Optional<Token> acceptWhitespace() {

        // Quit early if there is no more input.
        if ( !this.canRead() ) {
            return Optional.empty();
        }

        // capture the starting position of the token
        int startingLine = this.state.line;
        int startingColumn = this.state.column;

        // Loop for as long as we see whitespace.
        int index = this.state.nextCharIndex;
        char wsChar = this.text.charAt( index );
        while ( Character.isWhitespace( wsChar ) ) {
            index += 1;

            if ( wsChar == '\n' ) {
                this.state.line += 1;
                this.state.column = 1;
            }
            else {
                this.state.column += 1;
            }

            if ( index >= this.text.length() ) {
                break;
            }

            wsChar = this.text.charAt( index );
        }

        // Return an empty result if no whitespace was found.
        if ( index == this.state.nextCharIndex ) {
            return Optional.empty();
        }

        // Pack up the result.
        Optional<Token> result = Optional.of(
            new Token(
                this.text.substring(
                    this.state.nextCharIndex,
                    index
                ),
                this.fileName, startingLine, startingColumn
            )
        );

        // Move the current location
        this.state.nextCharIndex = index;

        return result;

    }

    /**
     * Abandons the previously marked input location. (Keeps anything marked before that.)
     *
     * @throws FileScannerException if a previous location has not been marked.
     */
    public void forgetMark() throws FileScannerException {
        if ( this.state.priorState.isPresent() ) {
            this.state.priorState = this.state.priorState.get().priorState;
        }
        else {
            throw new FileScannerException(
                "No prior state marked.",
                this.fileName,
                this.state.line,
                this.state.column
            );
        }
    }

    /**
     * Looks ahead in the input at the current location to see if the given token is present. Does not change the
     * current location.
     *
     * @param token the token that may be present in the input.
     *
     * @return true f the given token is present.
     */
    public boolean hasLookAhead( String token ) {

        // Validate the argument.
        if ( FileScanner.isNullOrEmpty( token ) ) {
            throw new IllegalArgumentException( "Token must be non-empty." );
        }

        final int endIndex = this.state.nextCharIndex + token.length();

        // If there are not enough characters available, then quit early.
        if ( endIndex > this.text.length() ) {
            return false;
        }

        // Compare the input to the token character by character.
        return token.equals( this.text.substring( this.state.nextCharIndex, endIndex ) );
    }

    /**
     * Marks the current location in the input for later back-tracking.
     */
    public void mark() {
        this.state = new FileScannerState(
            this.state.line,
            this.state.column,
            this.state.nextCharIndex,
            Optional.of( this.state )
        );
    }

    /**
     * Reverts the scanner back to the previously marked input location.
     *
     * @throws FileScannerException if a previous location has not been marked.
     */
    public void revertToMark() throws FileScannerException {
        if ( this.state.priorState.isPresent() ) {
            this.state = this.state.priorState.get();
        }
        else {
            throw new FileScannerException(
                "No prior state marked.",
                this.fileName,
                this.state.line,
                this.state.column
            );
        }
    }

    /**
     * Reads the given token from the input at the current location. Throws an exception if the token is not there.
     *
     * @param token the token to read.
     *
     * @return the read token.
     *
     * @throws FileScannerException if the token is not present of some unexpected error occurs.
     */
    public Token scan( String token ) throws FileScannerException {

        final Optional<Token> result = this.accept( token );

        if ( !result.isPresent() ) {
            String found = "[End of Input]";
            if ( this.canRead() ) {
                found = this.text.substring( this.state.nextCharIndex );
                if ( found.contains( "\n" ) ) {
                    found = found.substring( 0, found.indexOf( '\n' ) );
                }
                if ( found.length() > 10 ) {
                    found = found.substring( 0, 10 );
                }
                found = "\"" + found + "\"...";
            }
            throw new FileScannerException(
                "Expected \"" + token + "\"; found " + found + ".",
                this.fileName, this.state.line, this.state.column
            );
        }

        return result.get();

    }

    /**
     * Reads the end of input at the current location. Throws an exception if there is more to read.
     *
     * @return the origin of the end of input.
     *
     * @throws FileScannerException if some unexpected error occurs.
     */
    public Token scanEndOfInput() throws FileScannerException {

        final Optional<Token> result = this.acceptEndOfInput();

        if ( !result.isPresent() ) {
            throw new FileScannerException(
                "Expected end of input.",
                this.fileName,
                this.state.line,
                this.state.column
            );
        }

        return result.get();

    }

    /**
     * Reads a Java-compatible identifier from the input at the current location. Throws an exception if there is no
     * identifier.
     *
     * @return the identifier just read.
     *
     * @throws FileScannerException if some unexpected error occurs.
     */
    public Token scanIdentifier() throws FileScannerException {

        final Optional<Token> result = this.acceptIdentifier();

        if ( !result.isPresent() ) {
            throw new FileScannerException( "Expected identifier.", this.fileName, this.state.line, this.state.column );
        }

        return result.get();

    }

    /**
     * Scans an empty string from the input. (Useful for getting the current line & column from the scanner.)
     *
     * @return a token with empty text and current line & column set.
     */
    public Token scanNothing() {
        return new Token( "", this.fileName, this.state.line, this.state.column );
    }

    /**
     * Scans a token matching a regular expression.
     *
     * @param regex the pattern to match
     *
     * @return the token scanned
     *
     * @throws FileScannerException if the regular expression does not match the input.
     */
    public Token scanRegex( String regex ) throws FileScannerException {

        final Optional<Token> result = this.acceptRegex( regex );

        if ( !result.isPresent() ) {
            String found = "[End of Input]";
            if ( this.canRead() ) {
                found = this.text.substring( this.state.nextCharIndex );
                if ( found.contains( "\n" ) ) {
                    found = found.substring( 0, found.indexOf( '\n' ) );
                }
                if ( found.length() > 10 ) {
                    found = found.substring( 0, 10 );
                }
                found = "\"" + found + "\"...";
            }
            throw new FileScannerException(
                "Expected /" + regex + "/; found " + found + ".",
                this.fileName, this.state.line, this.state.column
            );
        }

        return result.get();

    }

    /**
     * Reads the input from the current location until the given delimiter (exclusive). Throws an exception if the
     * delimiter is not there.
     *
     * @param delimiter the delimiter to look for.
     *
     * @return the text read.
     *
     * @throws FileScannerException if the delimiter is not present or some unexpected error occurs.
     */
    public Token scanUntil( String delimiter ) throws FileScannerException {

        final Optional<Token> result = this.acceptUntil( delimiter );

        if ( !result.isPresent() ) {
            throw new FileScannerException(
                "Expected \"" + delimiter + "\".",
                this.fileName,
                this.state.line,
                this.state.column
            );
        }

        return result.get();

    }

    /**
     * Reads the input from the current location until the end of input.
     *
     * @return the text read.
     */
    public Token scanUntilEndOfInput() {

        // Consume to the end
        int endIndex = this.text.length();

        // Quit early if there is no more input.
        if ( this.state.nextCharIndex >= endIndex ) {
            return new Token( "", this.fileName, this.state.line, this.state.column );
        }

        // Pack up the result.
        Token result = new Token(
            this.text.substring( this.state.nextCharIndex, endIndex ),
            this.fileName, this.state.line, this.state.column
        );

        // Advance through the recognized text, counting lines and columns.
        for ( int i = this.state.nextCharIndex; i < endIndex; i += 1 ) {

            if ( this.text.charAt( i ) == '\n' ) {
                this.state.line += 1;
                this.state.column = 1;
            }
            else {
                this.state.column += 1;
            }

        }

        // Move the current location.
        this.state.nextCharIndex = endIndex;

        return result;

    }

    /**
     * Reads whitespace from the input at the current location. Throws an exception if there is no whitespace.
     *
     * @return the whitespace just read.
     *
     * @throws FileScannerException if some unexpected error occurs.
     */
    public Token scanWhitespace() throws FileScannerException {

        final Optional<Token> result = this.acceptWhitespace();

        if ( !result.isPresent() ) {
            throw new FileScannerException( "Expected whitespace.", this.fileName, this.state.line, this.state.column );
        }

        return result.get();

    }

    /**
     * Internal state of a file scanner, including link to remembered prior state.
     */
    private static final class FileScannerState {

        private FileScannerState(
            int line,
            int column,
            int nextCharIndex,
            Optional<FileScannerState> priorState
        ) {
            this.column = column;
            this.line = line;
            this.nextCharIndex = nextCharIndex;
            this.priorState = priorState;
        }

        private int column;

        private int line;

        private int nextCharIndex;

        private Optional<FileScannerState> priorState;

    }

    /**
     * Checks for a null or empty string.
     *
     * @param str the string to check.
     *
     * @return true if the string is null or empty.
     */
    private static boolean isNullOrEmpty( String str ) {
        return str == null || str.isEmpty();
    }

    /**
     * Replaces tab, carriage return, form feed, and new line characters by their escaped equivalents.
     *
     * @param str the input string
     *
     * @return the string with whitespace characters escaped or null if the input is null.
     */
    @SuppressWarnings( "ReturnOfNull" )
    private static String whitespaceEscaped( CharSequence str ) {

        if ( str == null ) {
            return null;
        }

        String result = FileScanner.TAB_CHAR.matcher( str ).replaceAll( "\\t" );
        result = FileScanner.NL_CHAR.matcher( result ).replaceAll( "\\n" );
        result = FileScanner.CR_CHAR.matcher( result ).replaceAll( "\\r" );
        result = FileScanner.FF_CHAR.matcher( result ).replaceAll( "\\f" );

        return result;

    }

    /**
     * @return whether there is another input character waiting to be read.
     */
    private boolean canRead() {
        return this.state.nextCharIndex < this.text.length();
    }

    private static final Pattern CR_CHAR = Pattern.compile( "\\r" );

    private static final Pattern FF_CHAR = Pattern.compile( "\\f" );

    private static final Pattern NL_CHAR = Pattern.compile( "\\n" );

    private static final Pattern TAB_CHAR = Pattern.compile( "\\t" );

    /** The name of the file being scanned. */
    private final String fileName;

    /** The current state of the scanner. */
    private FileScannerState state;

    /** The entire contents of the file being scanned. */
    private final String text;

}
