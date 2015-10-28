//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.model.api.tokens.text;

import org.steamflake.templates.domain.model.api.tokens.ISteamflakeTmAbstractToken;

/**
 * A Steamflake template token representing a string of plain text.
 */
public interface ISteamflakeTmTextToken
    extends ISteamflakeTmAbstractToken {

    /**
     * @return the text string within this token.
     */
    String getText();

}
