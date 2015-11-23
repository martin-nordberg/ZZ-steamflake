//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.model.api.directives.variables;

import org.steamflake.templates.domain.model.api.directives.ISteamflakeTmAbstractDirective;

/**
 * Interface to a variable reference directive.
 */
public interface ISteamflakeTmVariableDirective
    extends ISteamflakeTmAbstractDirective {

    /**
     * @return the path of the variable being referenced.
     */
    String getPath();

}
