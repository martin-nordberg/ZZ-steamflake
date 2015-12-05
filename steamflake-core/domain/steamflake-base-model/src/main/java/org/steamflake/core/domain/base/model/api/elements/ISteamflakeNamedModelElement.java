//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.base.model.api.elements;

import org.steamflake.core.domain.base.model.api.utilities.IQualifiedName;
import org.steamflake.core.infrastructure.utilities.collections.IIndexable;

/**
 * A generic model element with a name.
 */
public interface ISteamflakeNamedModelElement<
    IRootPackage extends ISteamflakeAbstractPackage<IRootPackage, IConcretePackage>,
    IConcretePackage extends ISteamflakeAbstractPackage<IRootPackage, IConcretePackage>
    >
    extends ISteamflakeModelElement<IRootPackage, IConcretePackage> {

    /**
     * @return The children of this model element.
     */
    IIndexable<ISteamflakeModelElement<IRootPackage, IConcretePackage>> getChildren();

    /**
     * @return The qualified name of this element.
     */
    IQualifiedName getId();

    /**
     * The parent of a named model element must itself be a named model element.
     *
     * @return the named parent of this model element.
     */
    @Override
    ISteamflakeNamedModelElement<IRootPackage, IConcretePackage> getParent();

}
