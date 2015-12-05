//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.model.impl.directives;

import org.steamflake.core.domain.base.model.api.utilities.IFileOrigin;
import org.steamflake.core.domain.base.model.impl.elements.SteamflakeModelElement;
import org.steamflake.templates.domain.model.api.directives.ISteamflakeTmAbstractDirective;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmDirectiveContainer;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmPackage;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmRootPackage;
import org.steamflake.templates.domain.model.impl.elements.ISteamflakeTmDirectiveContainerMixin;
import org.steamflake.templates.domain.model.impl.elements.SteamflakeTmRule;

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
        ISteamflakeTmDirectiveContainerMixin parent,
        Optional<IFileOrigin> origin
    ) {
        super( parent, origin, Optional.empty() );

        if ( parent instanceof SteamflakeTmRule ) {
            ( (SteamflakeTmRule) parent ).onAddChild( this );
        }
        else {
            ( (SteamflakeTmCompositeDirective) parent ).onAddChild( this );
        }
    }

    @Override
    public ISteamflakeTmDirectiveContainer getParent() {
        return (ISteamflakeTmDirectiveContainer) super.getParent();
    }

}
