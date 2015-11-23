//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.model.api.directives.text;

import org.steamflake.templates.domain.model.api.directives.ISteamflakeTmAbstractDirective;

/**
 * A Steamflake template directive representing a string of plain text.
 */
public interface ISteamflakeTmTextDirective
    extends ISteamflakeTmAbstractDirective {

    /**
     * @return the text string within this directive.
     */
    String getText();

}
