//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.persistence.codeio.codegen.api;

import org.steamflake.core.persistence.codeio.codegen.impl.CodeWriterImpl;

import java.io.Writer;

/**
 * Utility class for writing code.
 */
@SuppressWarnings( { "HardcodedLineSeparator", "ClassWithTooManyMethods" } )
public final class CodeWriter
    implements AutoCloseable {

    /**
     * Constructs a new code writer with given configuration.
     *
     * @param writer the underlying output to be written to.
     * @param config the configuration of the writer.
     */
    public CodeWriter( Writer writer, CodeWriterConfig config ) {
        this.impl = new CodeWriterImpl( writer, config );
    }

    /**
     * Forces a wrap at this position in the current line of output.
     *
     * @return this code writer for method chaining.
     */
    public CodeWriter alwaysWrap() {
        this.impl.alwaysWrap( "" );
        return this;
    }

    /**
     * Forces a wrap at this position in the current line of output. Includes extra characters (typically a comment
     * indicator) at the start of the new line if wrapped.
     *
     * @param newLinePrefixChars the characters to append at the start of the new line when wrapped.
     *
     * @return this code writer for method chaining.
     */
    public CodeWriter alwaysWrap( String newLinePrefixChars ) {
        this.impl.alwaysWrap( newLinePrefixChars );
        return this;
    }

    /**
     * Appends a string of text to the output code.
     *
     * @param text the text to append (must not contain any new line characters).
     *
     * @return this code writer for method chaining.
     */
    public CodeWriter append( String text ) {
        this.impl.append( text );
        return this;
    }

    /**
     * Appends a string of text to the output code if a given condition is true.
     *
     * @param condition the guard condition.
     * @param text      the text to append (must not contain any new line characters).
     *
     * @return this code writer for method chaining.
     */
    @SuppressWarnings( "BooleanParameter" )
    public CodeWriter appendIf( boolean condition, String text ) {
        if ( condition ) {
            this.append( text );
        }
        return this;
    }

    /**
     * Appends a string of text that can be evenly spaced and wrapped as needed.
     */
    public CodeWriter appendProse( String text, String newLinePrefixChars ) {
        this.impl.appendProse( text, newLinePrefixChars );
        return this;
    }

    @Override
    public void close() {
        this.impl.close();
    }

    /**
     * Appends nothing to the output or when needed provides a place to wrap to the next line instead.
     *
     * @return this code writer for method chaining.
     */
    public CodeWriter emptyOrWrap() {
        this.impl.emptyOrWrap( "" );
        return this;
    }

    /**
     * Appends nothing to the output or when needed provides a place to wrap to the next line instead. Includes extra
     * characters (typically a comment indicator) at the start of the new line if wrapped.
     *
     * @param newLinePrefixChars the characters to append at the start of the new line when wrapped.
     *
     * @return this code writer for method chaining.
     */
    public CodeWriter emptyOrWrap( String newLinePrefixChars ) {
        this.impl.emptyOrWrap( newLinePrefixChars );
        return this;
    }

    /**
     * Appends nothing to the output or when needed provides a place to wrap to the next line instead. If wrapped, the
     * next line will also be indented.
     *
     * @return this code writer for method chaining.
     */
    public CodeWriter emptyOrWrapIndent() {
        return this.emptyOrWrap().indent();
    }

    /**
     * Appends nothing to the output or when needed provides a place to wrap to the next line instead. If wrapped, the
     * next line will also be unindented.
     *
     * @return this code writer for method chaining.
     */
    public CodeWriter emptyOrWrapUnindent() {
        return this.emptyOrWrap().unindent();
    }

    /**
     * Increments the indent level for the next written line of code.
     *
     * @return this code writer for method chaining.
     */
    public CodeWriter indent() {
        this.impl.indent();
        return this;
    }

    /**
     * Marks the current line's output so far as a point to back up to if needed.
     *
     * @return this code writer for method chaining.
     */
    public CodeWriter mark() {
        this.impl.mark();
        return this;
    }

    /**
     * Starts a new line in the output. Writes out the currently pending line including intermediate line wraps if
     * needed.
     *
     * @return this code writer for method chaining.
     */
    @SuppressWarnings( "ImplicitNumericConversion" )
    public CodeWriter newLine() {
        this.impl.newLine();
        return this;
    }

    /**
     * Starts a new line in the output if the given condition is true. Writes out the currently pending line including
     * intermediate line wraps if needed.
     *
     * @return this code writer for method chaining.
     */
    @SuppressWarnings( "BooleanParameter" )
    public CodeWriter newLineIf( boolean condition ) {
        if ( condition ) {
            return this.newLine();
        }
        return this;
    }

    /**
     * Discards anything written since the last marker was set.
     *
     * @return this code writer for method chaining.
     */
    public CodeWriter revertToMark() {
        this.impl.revertToMark();
        return this;
    }

    /**
     * Appends a space character to the output or when needed provides a place to wrap to the next line instead.
     *
     * @return this code writer for method chaining.
     */
    public CodeWriter spaceOrWrap() {
        this.impl.spaceOrWrap( "" );
        return this;
    }

    /**
     * Appends a space character to the output or when needed provides a place to wrap to the next line instead.
     * Includes extra characters (typically a comment indicator) at the start of the new line if wrapped.
     *
     * @param newLinePrefixChars the characters to append at the start of the new line when wrapped.
     *
     * @return this code writer for method chaining.
     */
    public CodeWriter spaceOrWrap( String newLinePrefixChars ) {
        this.impl.spaceOrWrap( newLinePrefixChars );
        return this;
    }

    /**
     * Appends a space character to the output or when needed provides a place to wrap to the next line instead. If
     * wrapped, the next line will also be indented.
     *
     * @return this code writer for method chaining.
     */
    public CodeWriter spaceOrWrapIndent() {
        return this.spaceOrWrap().indent();
    }

    /**
     * Appends a space character to the output or when needed provides a place to wrap to the next line instead. If
     * wrapped, the next line will also be indented. Skips the whole thing if the condition is false.
     *
     * @param condition if false do nothing at all.
     *
     * @return this code writer for method chaining.
     */
    @SuppressWarnings( "BooleanParameter" )
    public CodeWriter spaceOrWrapIndentIf( boolean condition ) {
        if ( condition ) {
            return this.spaceOrWrap().indent();
        }
        return this;
    }

    /**
     * Appends a space character to the output or when needed provides a place to wrap to the next line instead. If
     * wrapped, the next line will also be unindented.
     *
     * @return this code writer for method chaining.
     */
    public CodeWriter spaceOrWrapUnindent() {
        return this.spaceOrWrap().unindent();
    }

    /**
     * Appends a space character to the output or when needed provides a place to wrap to the next line instead. If
     * wrapped, the next line will also be unindented. Skips the whole thing if the condition is false.
     *
     * @param condition if false do nothing at all.
     *
     * @return this code writer for method chaining.
     */
    @SuppressWarnings( "BooleanParameter" )
    public CodeWriter spaceOrWrapUnindentIf( boolean condition ) {
        if ( condition ) {
            return this.spaceOrWrap().unindent();
        }
        return this;
    }

    /**
     * Increments the indent level for the next written line of code.
     *
     * @return this code writer for method chaining.
     */
    public CodeWriter unindent() {
        this.impl.unindent();
        return this;
    }

    private final CodeWriterImpl impl;

}
