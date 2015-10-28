//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.model.api.elements;

import org.steamflake.core.domain.base.model.api.elements.ISteamflakeNamedModelElement;

/**
 * A Steamflake parameter.
 */
public interface ISteamflakeTmParameter
    extends ISteamflakeNamedModelElement<ISteamflakeTmRootPackage, ISteamflakeTmPackage> {

    /**
     * @return the parent of this parameter.
     */
    @Override
    ISteamflakeTmRule getParent();

    /**
     * @return the type of this parameter.
     */
    String getTypeName();

}
