//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.base.model.api.elements;

import org.steamflake.core.infrastructure.utilities.collections.IIndexable;

/**
 * A generic model element that contains sub-elements.
 */
public interface ISteamflakeContainerElement<
    IRootPackage extends ISteamflakeAbstractPackage<IRootPackage, IConcretePackage>,
    IConcretePackage extends ISteamflakeAbstractPackage<IRootPackage, IConcretePackage>
    >
    extends ISteamflakeModelElement<IRootPackage, IConcretePackage> {

    /**
     * @return The children of this model element.
     */
    IIndexable<ISteamflakeModelElement<IRootPackage, IConcretePackage>> getChildren();

}
