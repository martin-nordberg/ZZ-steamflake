//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.model.impl.directives.logic;

import org.steamflake.core.infrastructure.utilities.files.FileOrigin;
import org.steamflake.templates.domain.model.api.directives.logic.ISteamflakeTmIfDirective;
import org.steamflake.templates.domain.model.impl.directives.SteamflakeTmCompositeDirective;
import org.steamflake.templates.domain.model.impl.elements.ISteamflakeTmDirectiveContainerMixin;

import java.util.Optional;

/**
 * Implementation of a Steamflake template variable reference directive.
 */
public class SteamflakeTmIfDirective
    extends SteamflakeTmCompositeDirective
    implements ISteamflakeTmIfDirective {

    /**
     * Constructs a new Steamflake variable directive.
     *
     * @param parent the parent of this directive.
     */
    public SteamflakeTmIfDirective(
        ISteamflakeTmDirectiveContainerMixin parent,
        Optional<FileOrigin> origin,
        String path
    ) {
        super( parent, origin );
        this.path = path;
    }

    @Override
    public String getConditionPath() {
        return this.path;
    }

    @Override
    public String getKeyword() {
        return "if";
    }

    private final String path;

}
