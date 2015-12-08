//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.base.model.api.elements;

import org.steamflake.core.domain.base.model.api.utilities.IQualifiedName;

/**
 * A generic model element with a name.
 */
public interface ISteamflakeNamedContainerElement<
    IRootPackage extends ISteamflakeAbstractPackage<IRootPackage, IConcretePackage>,
    IConcretePackage extends ISteamflakeAbstractPackage<IRootPackage, IConcretePackage>
    >
    extends ISteamflakeNamedModelElement<IRootPackage, IConcretePackage>,
            ISteamflakeContainerElement<IRootPackage, IConcretePackage> {

    /**
     * The parent of a named model element must itself be a named model element.
     *
     * @return the named parent of this model element.
     */
    @Override
    ISteamflakeNamedContainerElement<IRootPackage, IConcretePackage> getParent();

    /**
     * @return the qualified name of this model element.
     */
    IQualifiedName getQualifiedName();

}
