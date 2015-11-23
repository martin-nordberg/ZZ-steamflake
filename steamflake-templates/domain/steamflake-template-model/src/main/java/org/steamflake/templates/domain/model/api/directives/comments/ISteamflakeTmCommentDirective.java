//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.model.api.directives.comments;

import org.steamflake.templates.domain.model.api.directives.ISteamflakeTmAbstractDirective;

/**
 * A Steamflake template directive representing a comment.
 */
public interface ISteamflakeTmCommentDirective
    extends ISteamflakeTmAbstractDirective {

    /**
     * @return the comment text string within this token (excludes leading and trailing space.
     */
    String getText();

}
