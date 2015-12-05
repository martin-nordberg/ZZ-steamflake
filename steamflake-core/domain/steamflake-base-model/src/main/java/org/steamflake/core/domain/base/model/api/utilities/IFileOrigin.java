//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.base.model.api.utilities;

import java.util.Optional;

/**
 * Representation of a source location in a file.
 */
public interface IFileOrigin {

    /**
     * @return the column number in the file where the model element was parsed from.
     */
    int getColumn();

    /**
     * @return the name of the file where the model element was parsed from.
     */
    String getFileName();

    /**
     * @return the line number in the file where the model element was parsed from.
     */
    int getLine();

    /**
     * A convenient origin to use when not parsing a source file.
     */
    Optional<IFileOrigin> UNUSED = Optional.empty();

}
