//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.infrastructure.utilities.text;

import java.util.regex.Pattern;

/**
 * Utility class for strings.
 */
@SuppressWarnings( "HardcodedLineSeparator" )
public final class Strings {

    private Strings() {
    }

    /**
     * Checks for a null or empty string.
     *
     * @param str the string to check.
     *
     * @return true if the string is null or empty.
     */
    public static boolean isNullOrEmpty( String str ) {
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
    public static String whitespaceEscaped( CharSequence str ) {

        if ( str == null ) {
            return null;
        }

        String result = Strings.TAB_CHAR.matcher( str ).replaceAll( "\\t" );
        result = Strings.NL_CHAR.matcher( result ).replaceAll( "\\n" );
        result = Strings.CR_CHAR.matcher( result ).replaceAll( "\\r" );
        result = Strings.FF_CHAR.matcher( result ).replaceAll( "\\f" );

        return result;

    }

    private static final Pattern CR_CHAR = Pattern.compile( "\\r" );

    private static final Pattern FF_CHAR = Pattern.compile( "\\f" );

    private static final Pattern NL_CHAR = Pattern.compile( "\\n" );

    private static final Pattern TAB_CHAR = Pattern.compile( "\\t" );

}
