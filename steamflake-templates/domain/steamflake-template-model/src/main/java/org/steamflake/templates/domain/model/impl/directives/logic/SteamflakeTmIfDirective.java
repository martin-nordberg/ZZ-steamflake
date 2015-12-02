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
 * Implementation of a Steamflake template if directive.
 */
public class SteamflakeTmIfDirective
    extends SteamflakeTmCompositeDirective
    implements ISteamflakeTmIfDirective {

    /**
     * Constructs a new Steamflake if directive.
     *
     * @param parent the parent of this directive.
     */
    public SteamflakeTmIfDirective(
        ISteamflakeTmDirectiveContainerMixin parent,
        Optional<FileOrigin> origin,
        String boolConditionPath
    ) {
        super( parent, origin );
        this.boolConditionPath = boolConditionPath;
    }

    @Override
    public String getBoolConditionPath() {
        return this.boolConditionPath;
    }

    @Override
    public String getKeyword() {
        return "if";
    }

    private final String boolConditionPath;

}
