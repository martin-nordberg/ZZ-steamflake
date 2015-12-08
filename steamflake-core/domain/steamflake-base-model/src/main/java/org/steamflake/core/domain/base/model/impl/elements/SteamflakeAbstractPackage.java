//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.base.model.impl.elements;

import org.steamflake.core.domain.base.model.api.elements.ISteamflakeAbstractPackage;
import org.steamflake.core.domain.base.model.api.utilities.IFileOrigin;
import org.steamflake.core.infrastructure.utilities.collections.IIndexable;
import org.steamflake.core.infrastructure.utilities.collections.ReadOnlyListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of an abstract model package.
 */
public abstract class SteamflakeAbstractPackage<
    IRootPackage extends ISteamflakeAbstractPackage<IRootPackage, IConcretePackage>,
    IConcretePackage extends ISteamflakeAbstractPackage<IRootPackage, IConcretePackage>
    >
    extends SteamflakeNamedContainerElement<IRootPackage, IConcretePackage>
    implements ISteamflakeAbstractPackage<IRootPackage, IConcretePackage> {

    /**
     * Constructs a new abstract Steamflake root package (no parent).
     */
    @SuppressWarnings( "TypeMayBeWeakened" )
    protected SteamflakeAbstractPackage(
        Optional<IFileOrigin> origin,
        String name,
        Optional<String> description
    ) {
        super( origin, name, description );

        this.packages = new ArrayList<>();
    }

    /**
     * Constructs a new abstract Steamflake package (package or root package).
     */
    @SuppressWarnings( "TypeMayBeWeakened" )
    protected SteamflakeAbstractPackage(
        SteamflakeAbstractPackage<IRootPackage, IConcretePackage> parent,
        Optional<IFileOrigin> origin,
        String name,
        Optional<String> description
    ) {
        super( parent, origin, name, description );

        this.packages = new ArrayList<>();
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public final IConcretePackage findOrCreatePackage( String relativeQualifiedName ) {
        // return this package itself if we're at the bottom of the path
        if ( relativeQualifiedName.isEmpty() ) {
            return (IConcretePackage) this;
        }

        // split the relative path into first and rest
        String[] packageNames = relativeQualifiedName.split( "\\.", 2 );

        // look for an existing sub-package
        for ( IConcretePackage stpackage : this.packages ) {
            if ( stpackage.getName().equals( packageNames[0] ) ) {
                if ( packageNames.length == 1 ) {
                    return stpackage;
                }
                return stpackage.findOrCreatePackage( packageNames[1] );
            }
        }

        // not found - create a new sub-package
        IConcretePackage newPackage = this.addPackage( IFileOrigin.UNUSED, packageNames[0], Optional.empty() );

        if ( packageNames.length == 1 ) {
            return newPackage;
        }

        return newPackage.findOrCreatePackage( packageNames[1] );
    }

    @Override
    public final IIndexable<IConcretePackage> getPackages() {
        return new ReadOnlyListAdapter<>( this.packages );
    }

    @Override
    public ISteamflakeAbstractPackage<IRootPackage, IConcretePackage> getParent() {
        return (ISteamflakeAbstractPackage<IRootPackage, IConcretePackage>) super.getParent();
    }

    /** Responds to the event of adding a child to this model element. */
    public void onAddChild( IConcretePackage child ) {
        super.onAddChild( child );
        this.packages.add( child );
    }

    private final List<IConcretePackage> packages;

}
