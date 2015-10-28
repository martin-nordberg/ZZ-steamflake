//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.base.model.impl.elements;

import org.steamflake.core.domain.base.model.api.elements.ISteamflakeAbstractPackage;
import org.steamflake.core.domain.base.model.api.elements.ISteamflakeModelElement;
import org.steamflake.core.domain.base.model.api.elements.ISteamflakeNamedModelElement;
import org.steamflake.core.domain.base.model.api.utilities.IFileOrigin;
import org.steamflake.core.domain.base.model.api.utilities.IQualifiedName;
import org.steamflake.core.domain.base.model.impl.utilities.QualifiedName;
import org.steamflake.core.infrastructure.utilities.collections.IIndexable;
import org.steamflake.core.infrastructure.utilities.collections.ReadOnlyListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of an abstract named model element.
 */
public abstract class SteamflakeNamedModelElement<
    IRootPackage extends ISteamflakeAbstractPackage<IRootPackage, IConcretePackage>,
    IConcretePackage extends ISteamflakeAbstractPackage<IRootPackage, IConcretePackage>
    >
    extends SteamflakeModelElement<IRootPackage, IConcretePackage>
    implements ISteamflakeNamedModelElement<IRootPackage, IConcretePackage> {

    /**
     * Constructs a new Steamflake model element
     *
     * @param parent      the parent of this model element.
     * @param origin      the file location this model element was parsed from
     * @param name        the name of this model element
     * @param description a description of this model element.
     */
    protected SteamflakeNamedModelElement(
        ISteamflakeNamedModelElement<IRootPackage, IConcretePackage> parent,
        Optional<IFileOrigin> origin,
        String name,
        Optional<String> description
    ) {
        super( parent, origin, description );

        this.children = new ArrayList<>();
        this.id = new QualifiedName( name, Optional.of( parent.getId() ) );
    }

    @Override
    public IIndexable<ISteamflakeModelElement<IRootPackage, IConcretePackage>> getChildren() {
        return new ReadOnlyListAdapter<>( this.children );
    }

    @Override
    public IQualifiedName getId() {
        return this.id;
    }

    @Override
    public ISteamflakeNamedModelElement<IRootPackage, IConcretePackage> getParent() {
        return (ISteamflakeNamedModelElement<IRootPackage, IConcretePackage>) super.getParent();
    }

    /** Responds to the event of adding a child to this model element. */
    protected void onAddChild( ISteamflakeModelElement<IRootPackage, IConcretePackage> child ) {
        this.children.add( child );
    }

    private final List<ISteamflakeModelElement<IRootPackage, IConcretePackage>> children;

    private final IQualifiedName id;

}
