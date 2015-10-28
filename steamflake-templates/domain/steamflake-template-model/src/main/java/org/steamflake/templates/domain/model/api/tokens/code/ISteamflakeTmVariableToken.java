//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.model.api.tokens.code;

import org.steamflake.templates.domain.model.api.tokens.ISteamflakeTmAbstractToken;

/**
 * Interface to a variable reference token.
 */
public interface ISteamflakeTmVariableToken
    extends ISteamflakeTmAbstractToken {

    /**
     * @return the path of the variable being referenced.
     */
    String getPath();

}
