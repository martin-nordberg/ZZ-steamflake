//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.model.impl.tokens.text;

import org.steamflake.core.infrastructure.utilities.files.FileOrigin;
import org.steamflake.templates.domain.model.api.tokens.text.ISteamflakeTmTextToken;
import org.steamflake.templates.domain.model.impl.elements.SteamflakeTmRule;
import org.steamflake.templates.domain.model.impl.tokens.SteamflakeTmAbstractToken;

import java.util.Optional;

/**
 * Concrete token for a text string within a Steamflake template.
 */
public class SteamflakeTmTextToken
    extends SteamflakeTmAbstractToken
    implements ISteamflakeTmTextToken {

    /**
     * Constructs a new Steamflake template model element
     *
     * @param parent the parent of this model element.
     */
    public SteamflakeTmTextToken( SteamflakeTmRule parent, Optional<FileOrigin> origin, String text ) {
        super( parent, origin );
        this.text = text;
    }

    @Override
    public String getText() {
        return this.text;
    }

    private final String text;

}
