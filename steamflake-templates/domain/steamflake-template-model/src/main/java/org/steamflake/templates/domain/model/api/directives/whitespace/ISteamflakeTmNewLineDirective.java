//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.model.api.directives.whitespace;

import org.steamflake.templates.domain.model.api.directives.ISteamflakeTmAbstractDirective;

import java.util.Optional;

/**
 * Interface to a %nl directive.
 */
public interface ISteamflakeTmNewLineDirective
    extends ISteamflakeTmAbstractDirective {

    /**
     * @return the optional path for a boolean variable tested to determine whether a new line is really needed.
     */
    Optional<String> getBoolConditionPath();

    /**
     * @return flag set to true of the output should include a space character in case the condition is false.
     */
    boolean isSpaceNeededIfNoNewLine();

}
