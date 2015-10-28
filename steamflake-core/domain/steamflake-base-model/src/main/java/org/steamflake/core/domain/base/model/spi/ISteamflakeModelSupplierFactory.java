package org.steamflake.core.domain.base.model.spi;


import org.steamflake.core.domain.base.model.api.elements.ISteamflakeAbstractPackage;
import org.steamflake.core.domain.base.model.api.elements.ISteamflakeModelElement;

/**
 * Interface to a factory of Steamflake model extensions.
 */
@FunctionalInterface
public interface ISteamflakeModelSupplierFactory<
    IRootPackage extends ISteamflakeAbstractPackage<IRootPackage, IConcretePackage>,
    IConcretePackage extends ISteamflakeAbstractPackage<IRootPackage, IConcretePackage>,
    T
    > {

    /**
     * Builds a model external supplier service for the given concrete type of model element.
     *
     * @param element the model element whose concrete type drives the factory.
     *
     * @return the supplier service.
     */
    <E extends ISteamflakeModelElement<IRootPackage, IConcretePackage>>
    ISteamflakeModelSupplierService<IRootPackage, IConcretePackage, T> build(
        E element
    );

}
