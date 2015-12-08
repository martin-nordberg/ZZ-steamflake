//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.base.model.impl.elements;

import org.steamflake.core.domain.base.model.api.elements.ISteamflakeAbstractPackage;
import org.steamflake.core.domain.base.model.api.elements.ISteamflakeNamedContainerElement;
import org.steamflake.core.domain.base.model.api.utilities.IFileOrigin;
import org.steamflake.core.domain.base.model.api.utilities.IQualifiedName;
import org.steamflake.core.domain.base.model.impl.utilities.QualifiedName;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

/**
 * Implementation of an abstract named model element.
 */
public abstract class SteamflakeNamedContainerElement<
    IRootPackage extends ISteamflakeAbstractPackage<IRootPackage, IConcretePackage>,
    IConcretePackage extends ISteamflakeAbstractPackage<IRootPackage, IConcretePackage>
    >
    extends SteamflakeContainerElement<IRootPackage, IConcretePackage>
    implements ISteamflakeNamedContainerElement<IRootPackage, IConcretePackage> {

    /**
     * Constructs a new Steamflake root package (no parent).
     *
     * @param origin      the file location this model element was parsed from
     * @param description a description of this model element.
     */
    protected SteamflakeNamedContainerElement(
        Optional<IFileOrigin> origin,
        String name,
        Optional<String> description
    ) {
        super( origin, description );

        this.qualifiedName = new QualifiedName( name, empty() );
    }

    /**
     * Constructs a new Steamflake model element
     *
     * @param parent      the parent of this model element.
     * @param origin      the file location this model element was parsed from
     * @param description a description of this model element.
     */
    protected SteamflakeNamedContainerElement(
        SteamflakeNamedContainerElement<IRootPackage, IConcretePackage> parent,
        Optional<IFileOrigin> origin,
        String name,
        Optional<String> description
    ) {
        super( parent, origin, description );

        this.qualifiedName = new QualifiedName( name, of( parent.getQualifiedName() ) );
    }

    @Override
    public String getName() {
        return this.qualifiedName.getName();
    }

    @Override
    public ISteamflakeNamedContainerElement<IRootPackage, IConcretePackage> getParent() {
        return (ISteamflakeNamedContainerElement<IRootPackage, IConcretePackage>) super.getParent();
    }

    @Override
    public IQualifiedName getQualifiedName() {
        return this.qualifiedName;
    }

    private final QualifiedName qualifiedName;

}
