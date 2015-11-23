//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.model.api.directives;

import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmDirectiveContainer;

/**
 * A Steamflake template directive which has directives inside it.
 */
public interface ISteamflakeTmCompositeDirective
    extends ISteamflakeTmAbstractDirective, ISteamflakeTmDirectiveContainer {

}
