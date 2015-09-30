//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.persistence.ioutilities.fileio;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class similar to FileWriter, but it does not change a file if the output content matches the file's current content.
 */
public class FileRewriter
    extends AbstractFileRewriter {

    /**
     * Constructs a new file rewriter to the given file.
     * @param file the file to be written if changed.
     */
    public FileRewriter( File file ) {
        super( file );
    }

    @Override
    protected void rewrite( File fileToWrite, String newContent ) throws IOException {

        // Delete the old file if exists.
        if ( fileToWrite.exists() ) {
            if ( !fileToWrite.delete() ) {
                throw new IOException( "Failed to delete output file: " + fileToWrite.getAbsolutePath() );
            }
        }

        // Write the new contents.
        FileWriter outWriter = new FileWriter( fileToWrite );
        outWriter.write( newContent );
        outWriter.close();

    }


}
