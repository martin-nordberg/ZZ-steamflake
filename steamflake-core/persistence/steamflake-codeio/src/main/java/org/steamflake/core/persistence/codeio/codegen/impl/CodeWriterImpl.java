//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.persistence.codeio.codegen.impl;

import org.steamflake.core.persistence.codeio.codegen.api.CodeWriterConfig;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Utility class for writing code.
 */
@SuppressWarnings( { "HardcodedLineSeparator", "ClassWithTooManyMethods" } )
public final class CodeWriterImpl
    implements AutoCloseable {

    /**
     * Constructs a new code writer with given configuration.
     *
     * @param writer the underlying output to be written to.
     * @param config the configuration of the writer.
     */
    public CodeWriterImpl( Writer writer, CodeWriterConfig config ) {
        this.writer = writer;
        this.config = config;
        this.indentForCurrLine = 0;
        this.markers = new ArrayList<>();
        this.tokensOnCurrLine = new ArrayList<>();
    }

    /**
     * Forces a wrap at this position in the current line of output. Includes extra characters (typically a comment
     * indicator) at the start of the new line if wrapped.
     *
     * @param newLinePrefixChars the characters to append at the start of the new line when wrapped.
     */
    public void alwaysWrap( String newLinePrefixChars ) {
        this.tokensOnCurrLine.add( new AlwaysWrapCodeOutputToken( newLinePrefixChars ) );
    }

    /**
     * Appends a string of text to the output code.
     *
     * @param text the text to append (must not contain any new line characters).
     */
    public void append( String text ) {
        this.tokensOnCurrLine.add( new TextCodeOutputToken( text ) );
    }

    /**
     * Appends a string of text that can be evenly spaced and wrapped as needed.
     */
    public void appendProse( String text, String newLinePrefixChars ) {
        this.tokensOnCurrLine.add( new ProseCodeOutputToken( text, newLinePrefixChars ) );
    }

    @Override
    public void close() {
        try {
            this.writer.close();
        }
        catch ( IOException e ) {
            throw new RuntimeException( "Failed to close code writer.", e );
        }
    }

    /**
     * Appends nothing to the output or when needed provides a place to wrap to the next line instead. Includes extra
     * characters (typically a comment indicator) at the start of the new line if wrapped.
     *
     * @param newLinePrefixChars the characters to append at the start of the new line when wrapped.
     */
    public void emptyOrWrap( String newLinePrefixChars ) {
        this.tokensOnCurrLine.add( new EmptyOrWrapCodeOutputToken( newLinePrefixChars ) );
    }

    /**
     * Increments the indent level for the next written line of code.
     */
    public void indent() {
        this.tokensOnCurrLine.add( new IndentCodeOutputToken() );
    }

    /**
     * Marks the current line's output so far as a point to back up to if needed.
     */
    public void mark() {
        this.markers.add( this.tokensOnCurrLine.size() );
    }

    /**
     * Starts a new line in the output. Writes out the currently pending line including intermediate line wraps if
     * needed.
     */
    @SuppressWarnings( "ImplicitNumericConversion" )
    public void newLine() {

        int indent = this.indentForCurrLine;

        // Try building the current line without wrapping.
        StringBuilder line = new StringBuilder();
        for ( ICodeOutputToken token : this.tokensOnCurrLine ) {
            indent = token.writeText( line, indent, this.config.spacesPerIndent );
        }

        // Ignore trailing spaces.
        int lineLength = line.length();
        while ( lineLength > 0 && line.charAt( lineLength - 1 ) == ' ' ) {
            lineLength -= 1;
        }

        // If the line is too long, rewrite it with intermediate wrapping.
        if ( lineLength > this.config.maxLineLength ) {

            indent = this.indentForCurrLine;
            line = new StringBuilder();
            for ( ICodeOutputToken token : this.tokensOnCurrLine ) {
                indent = token.writeWrappedText( line, indent, this.config.spacesPerIndent, this.config.maxLineLength );
            }

        }

        // Output the final line ending character(s).
        line.append( AbstractWrapCodeOutputToken.LINE_SEPARATOR );

        // Write through to the underlying writer.
        try {
            String lineStr = line.toString();

            // Remove trailing spaces.
            lineStr = CodeWriterImpl.SPACE_LF.matcher( lineStr ).replaceAll( "\n" );
            lineStr = CodeWriterImpl.SPACE_CR_LF.matcher( lineStr ).replaceAll( "\r\n" );

            this.writer.write( lineStr );
        }
        catch ( IOException e ) {
            throw new RuntimeException( "Failed to write through code writer.", e );
        }

        // Update the indent level.
        this.indentForCurrLine = indent;

        // Start the next line.
        this.tokensOnCurrLine.clear();
        this.markers.clear();

    }

    /**
     * Discards anything written since the last marker was set.
     */
    public void revertToMark() {

        if ( this.markers.isEmpty() ) {
            throw new IllegalStateException( "No marker to revert to." );
        }

        int lastMarkerIndex = this.markers.size() - 1;
        int lastMarker = this.markers.get( lastMarkerIndex );

        while ( this.tokensOnCurrLine.size() > lastMarker ) {
            this.tokensOnCurrLine.remove( this.tokensOnCurrLine.size() - 1 );
        }

        this.markers.remove( lastMarkerIndex );

    }

    /**
     * Appends a space character to the output or when needed provides a place to wrap to the next line instead.
     * Includes extra characters (typically a comment indicator) at the start of the new line if wrapped.
     *
     * @param newLinePrefixChars the characters to append at the start of the new line when wrapped.
     */
    public void spaceOrWrap( String newLinePrefixChars ) {
        this.tokensOnCurrLine.add( new SpaceOrWrapCodeOutputToken( newLinePrefixChars ) );
    }

    /**
     * Increments the indent level for the next written line of code.
     */
    public void unindent() {
        this.tokensOnCurrLine.add( new UnindentCodeOutputToken() );
    }

    /** Pattern for removing line ending spaces. */
    private static final Pattern SPACE_CR_LF = Pattern.compile( "( )+\\r\\n" );

    /** Pattern for removing line ending spaces. */
    private static final Pattern SPACE_LF = Pattern.compile( "( )+\\n" );

    /** The configuration of the code writer. */
    private final CodeWriterConfig config;

    /** The indent level queued up for the current line. */
    private int indentForCurrLine;

    /** Markers to make it possible to back up on the current line. */
    private final List<Integer> markers;

    /** Tokens waiting to be written as the current line of output. */
    private final List<ICodeOutputToken> tokensOnCurrLine;

    /** The underlying writer receiving the output of this code writer. */
    private final Writer writer;

}
