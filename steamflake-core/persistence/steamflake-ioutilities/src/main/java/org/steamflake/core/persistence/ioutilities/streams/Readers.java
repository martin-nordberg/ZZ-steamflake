//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.persistence.ioutilities.streams;

import java.io.IOException;
import java.io.Reader;

/**
 * Utility class for use of Java Readers.
 */
public final class Readers {

    private Readers() {
    }

    /**
     * Read the entire contents of the specified Reader and return a single String object containing the contents of the
     * input. This method does not return until the end of the input is reached. The reader is always closed, with or
     * without an error.
     *
     * @param reader a Reader from which to read
     *
     * @return a String containing the contents of the stream
     *
     * @throws IOException if an I/O error occurs while reading the input stream
     */
    public static String readAll( Reader reader ) throws IOException {

        try {

            char[] buffer = new char[Readers.BUFFER_SIZE];
            StringBuilder result = new StringBuilder();

            int nChars = reader.read( buffer, 0, buffer.length );
            while ( nChars > 0 ) {
                result.append( buffer, 0, nChars );
                nChars = reader.read( buffer, 0, buffer.length );
            }

            return result.toString();

        }
        finally {
            reader.close();
        }

    }

    /** The size to use for I/O buffers. */
    private static final int BUFFER_SIZE = 8 * 1024;

}
