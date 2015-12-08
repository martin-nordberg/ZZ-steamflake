//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.model.impl.directives.comments;

import org.steamflake.core.domain.base.model.api.utilities.IFileOrigin;
import org.steamflake.templates.domain.model.api.directives.comments.ISteamflakeTmCommentDirective;
import org.steamflake.templates.domain.model.impl.directives.SteamflakeTmAbstractDirective;
import org.steamflake.templates.domain.model.impl.elements.SteamflakeTmDirectiveSequence;

import java.util.Optional;

/**
 * Concrete directive for a comment directive within a Steamflake template.
 */
public class SteamflakeTmCommentDirective
    extends SteamflakeTmAbstractDirective
    implements ISteamflakeTmCommentDirective {

    /**
     * Constructs a new comment directive with given text.
     *
     * @param parent the parent of this model element.
     */
    public SteamflakeTmCommentDirective(
        SteamflakeTmDirectiveSequence parent,
        Optional<IFileOrigin> origin,
        String text
    ) {
        super( parent, origin );
        this.text = text.trim();
    }

    @Override
    public String getText() {
        return this.text;
    }

    private final String text;

}
