//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.model.impl.directives;

import org.steamflake.core.domain.base.model.api.utilities.IFileOrigin;
import org.steamflake.core.infrastructure.utilities.collections.IIndexable;
import org.steamflake.core.infrastructure.utilities.collections.ReadOnlyListAdapter;
import org.steamflake.templates.domain.model.api.directives.ISteamflakeTmAbstractDirective;
import org.steamflake.templates.domain.model.api.directives.ISteamflakeTmCompositeDirective;
import org.steamflake.templates.domain.model.impl.elements.ISteamflakeTmDirectiveContainerMixin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Base class for Steamflake directives that contain sub-directives.
 */
public abstract class SteamflakeTmCompositeDirective
    extends SteamflakeTmAbstractDirective
    implements ISteamflakeTmCompositeDirective, ISteamflakeTmDirectiveContainerMixin {

    /**
     * Constructs a new Steamflake directive.
     *
     * @param parent the parent of this directive.
     */
    protected SteamflakeTmCompositeDirective(
        ISteamflakeTmDirectiveContainerMixin parent,
        Optional<IFileOrigin> origin
    ) {
        super( parent, origin );

        this.directives = new ArrayList<>();
    }

    @Override
    public IIndexable<ISteamflakeTmAbstractDirective> getDirectives() {
        return new ReadOnlyListAdapter<>( this.directives );
    }

    @Override
    public boolean isComposite() {
        return true;
    }

    /** Responds to the event of adding a child directive to this rule. */
    public void onAddChild( ISteamflakeTmAbstractDirective child ) {
        this.directives.add( child );
    }

    /** The sub-directives within this composite directive. */
    private final List<ISteamflakeTmAbstractDirective> directives;

}
