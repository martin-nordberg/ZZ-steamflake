//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.model.impl.directives.variables;

import org.steamflake.core.domain.base.model.api.utilities.IFileOrigin;
import org.steamflake.templates.domain.model.api.directives.variables.ISteamflakeTmVariableDirective;
import org.steamflake.templates.domain.model.impl.directives.SteamflakeTmAbstractDirective;
import org.steamflake.templates.domain.model.impl.elements.SteamflakeTmDirectiveSequence;

import java.util.Optional;

/**
 * Implementation of a Steamflake template variable reference directive.
 */
public class SteamflakeTmVariableDirective
    extends SteamflakeTmAbstractDirective
    implements ISteamflakeTmVariableDirective {

    /**
     * Constructs a new Steamflake variable directive.
     *
     * @param parent the parent of this directive.
     */
    public SteamflakeTmVariableDirective(
        SteamflakeTmDirectiveSequence parent,
        Optional<IFileOrigin> origin,
        String path
    ) {
        super( parent, origin );
        this.path = path;
    }

    @Override
    public String getPath() {
        return this.path;
    }

    private final String path;

}
