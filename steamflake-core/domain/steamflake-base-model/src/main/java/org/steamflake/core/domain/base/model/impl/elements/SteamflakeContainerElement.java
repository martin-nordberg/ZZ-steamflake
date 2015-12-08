//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.base.model.impl.elements;

import org.steamflake.core.domain.base.model.api.elements.ISteamflakeAbstractPackage;
import org.steamflake.core.domain.base.model.api.elements.ISteamflakeContainerElement;
import org.steamflake.core.domain.base.model.api.elements.ISteamflakeModelElement;
import org.steamflake.core.domain.base.model.api.utilities.IFileOrigin;
import org.steamflake.core.infrastructure.utilities.collections.IIndexable;
import org.steamflake.core.infrastructure.utilities.collections.ReadOnlyListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of an abstract named model element.
 */
public abstract class SteamflakeContainerElement<
    IRootPackage extends ISteamflakeAbstractPackage<IRootPackage, IConcretePackage>,
    IConcretePackage extends ISteamflakeAbstractPackage<IRootPackage, IConcretePackage>
    >
    extends SteamflakeModelElement<IRootPackage, IConcretePackage>
    implements ISteamflakeContainerElement<IRootPackage, IConcretePackage> {

    /**
     * Constructs a new Steamflake root package (no parent).
     *
     * @param origin      the file location this model element was parsed from
     * @param description a description of this model element.
     */
    protected SteamflakeContainerElement(
        Optional<IFileOrigin> origin,
        Optional<String> description
    ) {
        super( origin, description );

        this.children = new ArrayList<>();
    }

    /**
     * Constructs a new Steamflake container model element.
     *
     * @param parent      the parent of this model element.
     * @param origin      the file location this model element was parsed from
     * @param description a description of this model element.
     */
    protected SteamflakeContainerElement(
        SteamflakeContainerElement<IRootPackage, IConcretePackage> parent,
        Optional<IFileOrigin> origin,
        Optional<String> description
    ) {
        super( parent, origin, description );

        this.children = new ArrayList<>();
    }

    @Override
    public IIndexable<ISteamflakeModelElement<IRootPackage, IConcretePackage>> getChildren() {
        return new ReadOnlyListAdapter<>( this.children );
    }

    /** Responds to the event of adding a child to this model element. */
    protected void onAddChild( ISteamflakeModelElement<IRootPackage, IConcretePackage> child ) {
        this.children.add( child );
    }

    private final List<ISteamflakeModelElement<IRootPackage, IConcretePackage>> children;

}
