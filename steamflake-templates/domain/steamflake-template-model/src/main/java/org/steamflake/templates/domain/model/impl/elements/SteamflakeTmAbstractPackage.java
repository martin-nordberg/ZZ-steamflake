//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.model.impl.elements;

import org.steamflake.core.domain.base.model.api.utilities.IFileOrigin;
import org.steamflake.core.domain.base.model.impl.elements.SteamflakeAbstractPackage;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmAbstractPackage;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmPackage;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmRootPackage;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmTemplate;

import java.util.Optional;

/**
 * Implementation of Steamflake templates abstract package.
 */
public abstract class SteamflakeTmAbstractPackage
    extends SteamflakeAbstractPackage<ISteamflakeTmRootPackage, ISteamflakeTmPackage>
    implements ISteamflakeTmAbstractPackage {

    /**
     * Constructs a new abstract Steamflake root package (no parent).
     */
    @SuppressWarnings( "TypeMayBeWeakened" )
    protected SteamflakeTmAbstractPackage(
        Optional<IFileOrigin> origin,
        String name,
        Optional<String> description
    ) {
        super( origin, name, description );
    }

    /**
     * Constructs a new abstract Steamflake concrete package.
     */
    @SuppressWarnings( "TypeMayBeWeakened" )
    protected SteamflakeTmAbstractPackage(
        SteamflakeTmAbstractPackage parent,
        Optional<IFileOrigin> origin,
        String name,
        Optional<String> description
    ) {
        super( parent, origin, name, description );
    }

    @Override
    public ISteamflakeTmPackage addPackage(
        Optional<IFileOrigin> origin, String name, Optional<String> description
    ) {
        return new SteamflakeTmPackage( this, origin, name, description );
    }

    @Override
    public Optional<ISteamflakeTmTemplate> findTemplate( String relativeQualifiedName ) {

        // split the relative path into first and rest
        String[] packageNames = relativeQualifiedName.split( "\\.", 2 );

        // look for an existing sub-package
        for ( ISteamflakeTmPackage jpackage : this.getPackages() ) {
            if ( jpackage.getName().equals( packageNames[0] ) ) {
                return jpackage.findTemplate( packageNames[1] );
            }
        }

        return Optional.empty();

    }

    @Override
    public ISteamflakeTmAbstractPackage getParent() {
        return (ISteamflakeTmAbstractPackage) super.getParent();
    }

}
