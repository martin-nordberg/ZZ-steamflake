//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.model.impl.tokens.code;

import org.steamflake.core.infrastructure.utilities.files.FileOrigin;
import org.steamflake.templates.domain.model.api.tokens.code.ISteamflakeTmVariableToken;
import org.steamflake.templates.domain.model.impl.elements.SteamflakeTmRule;
import org.steamflake.templates.domain.model.impl.tokens.SteamflakeTmAbstractToken;

import java.util.Optional;

/**
 * Implementation of a Steamflake template variable reference token.
 */
public class SteamflakeTmVariableToken
    extends SteamflakeTmAbstractToken
    implements ISteamflakeTmVariableToken {

    /**
     * Constructs a new Steamflake template model element
     *
     * @param parent the parent of this model element.
     */
    public SteamflakeTmVariableToken( SteamflakeTmRule parent, Optional<FileOrigin> origin, String path ) {
        super( parent, origin );
        this.path = path;
    }

    @Override
    public String getPath() {
        return this.path;
    }

    private final String path;

}
