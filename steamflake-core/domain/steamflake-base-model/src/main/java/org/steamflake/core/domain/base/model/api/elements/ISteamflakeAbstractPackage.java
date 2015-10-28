//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.base.model.api.elements;

import org.steamflake.core.domain.base.model.api.utilities.IFileOrigin;
import org.steamflake.core.infrastructure.utilities.collections.IIndexable;

import java.util.Optional;


/**
 * Model element for the abstract behavior of a package.
 */
@SuppressWarnings( "ClassReferencesSubclass" )
public interface ISteamflakeAbstractPackage<
    IRootPackage extends ISteamflakeAbstractPackage<IRootPackage, IConcretePackage>,
    IConcretePackage extends ISteamflakeAbstractPackage<IRootPackage, IConcretePackage>
    >
    extends ISteamflakeNamedModelElement<IRootPackage, IConcretePackage> {

    /**
     * Creates a package within this one (empty description).
     *
     * @param name the name of the new package.
     *
     * @return the newly created package.
     */
    default IConcretePackage addPackage( Optional<IFileOrigin> origin, String name ) {
        return this.addPackage( origin, name, Optional.empty() );
    }

    /**
     * Creates a package within this one (empty description, not implicitly imported).
     *
     * @param name        the name of the new package.
     * @param description a description of the new package.
     *
     * @return the newly created package.
     */
    default IConcretePackage addPackage( Optional<IFileOrigin> origin, String name, String description ) {
        return this.addPackage( origin, name, Optional.of( description ) );
    }

    /**
     * Creates a package within this one.
     *
     * @param name        the name of the new package.
     * @param description a description of the new package.
     *
     * @return the newly created package.
     */
    IConcretePackage addPackage( Optional<IFileOrigin> origin, String name, Optional<String> description );

    /**
     * Given a qualified name relative to this package, find or create the needed subpackages.
     *
     * @param relativeQualifiedName the relative name of the package to find or create if it does not exist.
     *
     * @return the found or created package.
     */
    IConcretePackage findOrCreatePackage( String relativeQualifiedName );

    /**
     * @return the packages within this package.
     */
    IIndexable<IConcretePackage> getPackages();

    /**
     * The parent of an abstract package must itself be an abstract package.
     *
     * @return the parent package to this one.
     */
    @Override
    ISteamflakeAbstractPackage<IRootPackage, IConcretePackage> getParent();

}
