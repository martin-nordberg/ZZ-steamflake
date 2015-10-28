//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.base.model.api.utilities;

/**
 * Interface to a qualified name.
 */
public interface IQualifiedName {

    /**
     * @return the unqualified base name of this symbol.
     */
    String getName();

    /**
     * @return the fully qualified path of this symbol.
     */
    String getPath();

}
