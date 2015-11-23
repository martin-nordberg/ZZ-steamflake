//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.model.api.directives.logic;

import org.steamflake.templates.domain.model.api.directives.ISteamflakeTmCompositeDirective;

/**
 * Interface to an #if directive.
 */
public interface ISteamflakeTmIfDirective
    extends ISteamflakeTmCompositeDirective {

    /**
     * @return the path of the boolean variable tested for true or false.
     */
    String getConditionPath();

}
