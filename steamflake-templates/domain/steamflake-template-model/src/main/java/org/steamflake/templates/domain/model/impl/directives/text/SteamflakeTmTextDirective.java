//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.model.impl.directives.text;

import org.steamflake.core.domain.base.model.api.utilities.IFileOrigin;
import org.steamflake.templates.domain.model.api.directives.text.ISteamflakeTmTextDirective;
import org.steamflake.templates.domain.model.impl.directives.SteamflakeTmAbstractDirective;
import org.steamflake.templates.domain.model.impl.elements.ISteamflakeTmDirectiveContainerMixin;

import java.util.Optional;

/**
 * Concrete directive for a text string within a Steamflake template.
 */
public class SteamflakeTmTextDirective
    extends SteamflakeTmAbstractDirective
    implements ISteamflakeTmTextDirective {

    /**
     * Constructs a new Steamflake text directive.
     *
     * @param parent the parent of this model element.
     */
    public SteamflakeTmTextDirective(
        ISteamflakeTmDirectiveContainerMixin parent,
        Optional<IFileOrigin> origin,
        String text
    ) {
        super( parent, origin );
        this.text = text;
    }

    @Override
    public String getText() {
        return this.text;
    }

    private final String text;

}
