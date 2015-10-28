//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.base.model.api.elements;

import org.steamflake.core.domain.base.model.api.utilities.IFileOrigin;
import org.steamflake.core.domain.base.model.spi.ISteamflakeModelConsumerFactory;
import org.steamflake.core.domain.base.model.spi.ISteamflakeModelSupplierFactory;

import java.util.Optional;

/**
 * Top level generic model element.
 */
@SuppressWarnings( "ClassReferencesSubclass" )
public interface ISteamflakeModelElement<
    IRootPackage extends ISteamflakeAbstractPackage<IRootPackage, IConcretePackage>,
    IConcretePackage extends ISteamflakeAbstractPackage<IRootPackage, IConcretePackage>
    > {

    /**
     * Consumes a value for this model element using the external service built by the given factory.
     *
     * @param factory a factory that builds (or just provides) a consumer extension to Steamflake model elements.
     * @param value   the value to consume.
     * @param <T>     the type of the consumed value.
     */
    default <T> void consume( ISteamflakeModelConsumerFactory<IRootPackage, IConcretePackage, T> factory, T value ) {
        factory.build( this ).consume( this, value );
    }

    /**
     * @return The optional description of this model element.
     */
    Optional<String> getDescription();

    /**
     * @return the optional origin of this model element from its source file.
     */
    Optional<IFileOrigin> getOrigin();

    /**
     * @return The parent of this model element.
     */
    ISteamflakeModelElement<IRootPackage, IConcretePackage> getParent();

    /**
     * @return The highest root package containing this model element.
     */
    IRootPackage getRootPackage();

    /**
     * Supplies a value from this model element using the external service built by the given factory.
     *
     * @param factory a factory that builds (or just provides) a supplier extension to Steamflake model elements.
     * @param <T>     the type of value supplied.
     *
     * @return the supplied value.
     */
    default <T> T supply( ISteamflakeModelSupplierFactory<IRootPackage, IConcretePackage, T> factory ) {
        return factory.build( this ).supply( this );
    }

}
