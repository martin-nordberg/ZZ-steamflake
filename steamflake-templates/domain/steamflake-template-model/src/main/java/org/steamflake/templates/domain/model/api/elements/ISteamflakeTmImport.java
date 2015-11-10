//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.model.api.elements;

import org.steamflake.core.domain.base.model.api.elements.ISteamflakeModelElement;

import java.util.Optional;

/**
 * A template import.
 */
public interface ISteamflakeTmImport
    extends ISteamflakeModelElement<ISteamflakeTmRootPackage, ISteamflakeTmPackage> {

    /**
     * @return the optional alias name for the imported type.
     */
    Optional<String> getAlias();

    @Override
    ISteamflakeTmTemplate getParent();

    /**
     * @return the imported type of this import.
     */
    String getTypeName();

}
