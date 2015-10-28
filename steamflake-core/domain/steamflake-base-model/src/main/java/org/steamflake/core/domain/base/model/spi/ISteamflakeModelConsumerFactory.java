package org.steamflake.core.domain.base.model.spi;


import org.steamflake.core.domain.base.model.api.elements.ISteamflakeAbstractPackage;
import org.steamflake.core.domain.base.model.api.elements.ISteamflakeModelElement;

/**
 * Interface to a factory of model extensions.
 */
@FunctionalInterface
public interface ISteamflakeModelConsumerFactory<
    IRootPackage extends ISteamflakeAbstractPackage<IRootPackage, IConcretePackage>,
    IConcretePackage extends ISteamflakeAbstractPackage<IRootPackage, IConcretePackage>,
    T
    > {

    /**
     * Builds a model external consumer service for the given concrete model element.
     *
     * @param element the type of model element.
     *
     * @return the consumer service.
     */
    <E extends ISteamflakeModelElement<IRootPackage, IConcretePackage>>
    ISteamflakeModelConsumerService<IRootPackage, IConcretePackage, T> build(
        E element
    );

}
