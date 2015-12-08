//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.model.impl.directives;

import org.steamflake.core.domain.base.model.api.utilities.IFileOrigin;
import org.steamflake.core.domain.base.model.impl.elements.SteamflakeContainerElement;
import org.steamflake.templates.domain.model.api.directives.ISteamflakeTmCompositeDirective;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmDirectiveSequence;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmPackage;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmRootPackage;
import org.steamflake.templates.domain.model.impl.elements.SteamflakeTmDirectiveSequence;

import java.util.Optional;

import static java.util.Optional.empty;

/**
 * Base class for Steamflake directives that contain sub-directives.
 */
@SuppressWarnings( "BooleanParameter" )
public abstract class SteamflakeTmCompositeDirective
    extends SteamflakeContainerElement<ISteamflakeTmRootPackage, ISteamflakeTmPackage>
    implements ISteamflakeTmCompositeDirective {

    /**
     * Constructs a new Steamflake directive.
     *
     * @param parent the parent of this directive.
     */
    protected SteamflakeTmCompositeDirective(
        SteamflakeTmDirectiveSequence parent,
        Optional<IFileOrigin> origin
    ) {
        super( parent, origin, empty() );

        this.directiveSequence = new SteamflakeTmDirectiveSequence( this, IFileOrigin.UNUSED, empty() );

        parent.onAddChild( this );
    }

    @Override
    public SteamflakeTmDirectiveSequence getDirectiveSequence() {
        return this.directiveSequence;
    }

    @Override
    public ISteamflakeTmDirectiveSequence getParent() {
        return (ISteamflakeTmDirectiveSequence) super.getParent();
    }

    @Override
    public boolean isComposite() {
        return true;
    }

    private final SteamflakeTmDirectiveSequence directiveSequence;

}
