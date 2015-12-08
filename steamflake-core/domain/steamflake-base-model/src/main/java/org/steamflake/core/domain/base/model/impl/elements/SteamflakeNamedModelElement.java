//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.base.model.impl.elements;

import org.steamflake.core.domain.base.model.api.elements.ISteamflakeAbstractPackage;
import org.steamflake.core.domain.base.model.api.elements.ISteamflakeNamedModelElement;
import org.steamflake.core.domain.base.model.api.utilities.IFileOrigin;

import java.util.Optional;

/**
 * Implementation of an abstract named model element.
 */
public abstract class SteamflakeNamedModelElement<
    IRootPackage extends ISteamflakeAbstractPackage<IRootPackage, IConcretePackage>,
    IConcretePackage extends ISteamflakeAbstractPackage<IRootPackage, IConcretePackage>
    >
    extends SteamflakeModelElement<IRootPackage, IConcretePackage>
    implements ISteamflakeNamedModelElement<IRootPackage, IConcretePackage> {

    /**
     * Constructs a new Steamflake named model element
     *
     * @param parent      the parent of this model element.
     * @param origin      the file location this model element was parsed from
     * @param name        the name of this model element
     * @param description a description of this model element.
     */
    protected SteamflakeNamedModelElement(
        SteamflakeContainerElement<IRootPackage, IConcretePackage> parent,
        Optional<IFileOrigin> origin,
        String name,
        Optional<String> description
    ) {
        super( parent, origin, description );

        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    private final String name;

}
