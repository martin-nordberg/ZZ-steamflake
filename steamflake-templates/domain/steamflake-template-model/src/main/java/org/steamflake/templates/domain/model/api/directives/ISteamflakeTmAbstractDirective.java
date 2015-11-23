//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.model.api.directives;

import org.steamflake.core.domain.base.model.api.elements.ISteamflakeModelElement;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmDirectiveContainer;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmPackage;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmRootPackage;

/**
 * Interface to an abstract Steamflake template rule directive.
 */
public interface ISteamflakeTmAbstractDirective
    extends ISteamflakeModelElement<ISteamflakeTmRootPackage, ISteamflakeTmPackage> {

    @Override
    ISteamflakeTmDirectiveContainer getParent();

    /**
     * @return whether this directive (potentially) has sub-directives within it.
     */
    default boolean isComposite() {
        return false;
    }

}
