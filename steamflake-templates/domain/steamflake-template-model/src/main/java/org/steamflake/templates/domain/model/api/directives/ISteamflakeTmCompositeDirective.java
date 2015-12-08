//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.model.api.directives;

import org.steamflake.core.domain.base.model.api.elements.ISteamflakeContainerElement;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmDirectiveSequence;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmPackage;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmRootPackage;

/**
 * A Steamflake template directive which has directives inside it.
 */
public interface ISteamflakeTmCompositeDirective
    extends ISteamflakeTmAbstractDirective,
            ISteamflakeContainerElement<ISteamflakeTmRootPackage, ISteamflakeTmPackage> {

    /**
     * @return the sequence of directives nested inside this one.
     */
    ISteamflakeTmDirectiveSequence getDirectiveSequence();

    /**
     * @return the keyword of this composite directive.
     */
    String getKeyword();

}
