//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.model.api.elements;

import org.steamflake.core.domain.base.model.api.elements.ISteamflakeAbstractPackage;

import java.util.Optional;


/**
 * Model element for the abstract attributes of a package.
 */
@SuppressWarnings( "ClassReferencesSubclass" )
public interface ISteamflakeTmAbstractPackage
    extends ISteamflakeAbstractPackage<ISteamflakeTmRootPackage, ISteamflakeTmPackage> {

    /**
     * Given a qualified name relative to this package, find the needed template.
     *
     * @param relativeQualifiedName the relative name for the template within this package.
     *
     * @return the template found.
     */
    Optional<ISteamflakeTmTemplate> findTemplate( String relativeQualifiedName );

    /**
     * The parent of an abstract package must itself be an abstract package.
     *
     * @return the parent package to this one.
     */
    @Override
    ISteamflakeTmAbstractPackage getParent();

}
