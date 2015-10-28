package org.steamflake.core.domain.base.model.spi;


import org.steamflake.core.domain.base.model.api.elements.ISteamflakeAbstractPackage;
import org.steamflake.core.domain.base.model.api.elements.ISteamflakeModelElement;

/**
 * Interface to an input consumer extension service.
 */
@FunctionalInterface
public interface ISteamflakeModelConsumerService<
    IRootPackage extends ISteamflakeAbstractPackage<IRootPackage, IConcretePackage>,
    IConcretePackage extends ISteamflakeAbstractPackage<IRootPackage, IConcretePackage>,
    T
    > {

    <E extends ISteamflakeModelElement<IRootPackage, IConcretePackage>> void consume( E element, T value );

}
