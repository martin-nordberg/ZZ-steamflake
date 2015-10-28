package org.steamflake.core.domain.base.model.spi;

import org.steamflake.core.domain.base.model.api.elements.ISteamflakeAbstractPackage;
import org.steamflake.core.domain.base.model.api.elements.ISteamflakeModelElement;

/**
 * Interface to an output supplier extension service.
 */
@FunctionalInterface
public interface ISteamflakeModelSupplierService<
    IRootPackage extends ISteamflakeAbstractPackage<IRootPackage, IConcretePackage>,
    IConcretePackage extends ISteamflakeAbstractPackage<IRootPackage, IConcretePackage>,
    T
    > {

    T supply( ISteamflakeModelElement<IRootPackage, IConcretePackage> element );

}
