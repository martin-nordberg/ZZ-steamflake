//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.model.impl.tokens;

import org.steamflake.core.domain.base.model.impl.elements.SteamflakeModelElement;
import org.steamflake.core.infrastructure.utilities.files.FileOrigin;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmPackage;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmRootPackage;
import org.steamflake.templates.domain.model.api.tokens.ISteamflakeTmAbstractToken;
import org.steamflake.templates.domain.model.impl.elements.SteamflakeTmRule;

import java.util.Optional;

/**
 * Base class for Steamflake tokens.
 */
public abstract class SteamflakeTmAbstractToken
    extends SteamflakeModelElement<ISteamflakeTmRootPackage, ISteamflakeTmPackage>
    implements ISteamflakeTmAbstractToken {

    /**
     * Constructs a new Steamflake template model element
     *
     * @param parent the parent of this model element.
     */
    protected SteamflakeTmAbstractToken(
        SteamflakeTmRule parent,
        Optional<FileOrigin> origin
    ) {
        super( parent, origin, Optional.empty() );

        parent.onAddChild( this );
    }

}
