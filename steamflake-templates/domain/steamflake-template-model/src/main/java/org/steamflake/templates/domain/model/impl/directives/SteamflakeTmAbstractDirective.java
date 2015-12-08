//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.model.impl.directives;

import org.steamflake.core.domain.base.model.api.utilities.IFileOrigin;
import org.steamflake.core.domain.base.model.impl.elements.SteamflakeModelElement;
import org.steamflake.templates.domain.model.api.directives.ISteamflakeTmAbstractDirective;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmDirectiveSequence;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmPackage;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmRootPackage;
import org.steamflake.templates.domain.model.impl.elements.SteamflakeTmDirectiveSequence;

import java.util.Optional;

/**
 * Base class for all Steamflake template directives.
 */
public abstract class SteamflakeTmAbstractDirective
    extends SteamflakeModelElement<ISteamflakeTmRootPackage, ISteamflakeTmPackage>
    implements ISteamflakeTmAbstractDirective {

    /**
     * Constructs a new Steamflake directive.
     *
     * @param parent the parent of this directive.
     */
    @SuppressWarnings( { "TypeMayBeWeakened", "ClassReferencesSubclass" } )
    protected SteamflakeTmAbstractDirective(
        SteamflakeTmDirectiveSequence parent,
        Optional<IFileOrigin> origin
    ) {
        super( parent, origin, Optional.empty() );

        parent.onAddChild( this );
    }

    @Override
    public ISteamflakeTmDirectiveSequence getParent() {
        return (ISteamflakeTmDirectiveSequence) super.getParent();
    }

}
