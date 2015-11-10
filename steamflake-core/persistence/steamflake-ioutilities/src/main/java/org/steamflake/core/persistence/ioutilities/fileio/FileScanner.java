//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.persistence.ioutilities.fileio;

import org.steamflake.core.infrastructure.utilities.files.FileOrigin;
import org.steamflake.core.infrastructure.utilities.text.Strings;

import java.util.Optional;

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

        private FileScannerException( String message, FileOrigin origin ) {
            super( message );
            this.origin = origin;
        }

        public FileOrigin getOrigin() {
            return this.origin;
        }

        private static final long serialVersionUID = 1;

        private final FileOrigin origin;

    }

    /**
     * Class representing a token returned from a scanner - its text and its origin.
     */
    public static final class Token {

        private Token( String text, FileOrigin origin ) {
            this.text = text;
            this.origin = origin;
        }

        public FileOrigin getOrigin() {
            return this.origin;
        }

        public String getText() {
            return this.text;
        }

        private final FileOrigin origin;

        private final String text;

    }

    /**
     * Constructs a new file scanner.
     *
     * @param text     the entire input to scan.
     * @param fileName the associated file name for the input.
     */
    public FileScanner( String text, String fileName ) {

        // Validate the arguments.
        if ( Strings.isNullOrEmpty( text ) ) {
            throw new IllegalArgumentException( "Input text must be non-empty." );
        }
        if ( Strings.isNullOrEmpty( fileName ) ) {
            throw new IllegalArgumentException( "File name must be non-empty." );
        }

        this.fileName = fileName;
        this.text = text;

        this.state = new FileScannerState( 1, 1, 0, Optional.empty() );

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
        if ( Strings.isNullOrEmpty( token ) ) {
            throw new IllegalArgumentException( "Token must be non-empty." );
        }
        if ( token.indexOf( '\n' ) >= 0 ) {
            throw new IllegalArgumentException(
                "Token may not contain a new line character: '" +
                    Strings.whitespaceEscaped( token ) + "'"
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
        Optional<Token> result = Optional.of( new Token( token, this.getCurrentLocation() ) );

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
            return Optional.of( new Token( "", this.getCurrentLocation() ) );
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
                this.getCurrentLocation()
            )
        );

        // Move the column and current location.
        this.state.column += index - this.state.nextCharIndex;
        this.state.nextCharIndex = index;

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
        if ( Strings.isNullOrEmpty( delimiter ) ) {
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
                this.getCurrentLocation()
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
        FileOrigin origin = this.getCurrentLocation();

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
                origin
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
            throw new FileScannerException( "No prior state marked.", this.getCurrentLocation() );
        }
    }

    /**
     * @return the file origin for the current location of the input.
     */
    public FileOrigin getCurrentLocation() {
        return new FileOrigin( this.fileName, this.state.line, this.state.column );
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
        if ( Strings.isNullOrEmpty( token ) ) {
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
            throw new FileScannerException( "No prior state marked.", this.getCurrentLocation() );
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
                this.getCurrentLocation()
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
            throw new FileScannerException( "Expected end of input.", this.getCurrentLocation() );
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
            throw new FileScannerException( "Expected identifier.", this.getCurrentLocation() );
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
            throw new FileScannerException( "Expected \"" + delimiter + "\".", this.getCurrentLocation() );
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
            return new Token( "", this.getCurrentLocation() );
        }

        // Pack up the result.
        Token result = new Token(
            this.text.substring( this.state.nextCharIndex, endIndex ),
            this.getCurrentLocation()
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
            throw new FileScannerException( "Expected whitespace.", this.getCurrentLocation() );
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
     * @return whether there is another input character waiting to be read.
     */
    private boolean canRead() {
        return this.state.nextCharIndex < this.text.length();
    }

    /** The name of the file being scanned. */
    private final String fileName;

    /** The current state of the scanner. */
    private FileScannerState state;

    /** The entire contents of the file being scanned. */
    private final String text;

}
