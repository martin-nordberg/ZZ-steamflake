//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.model.impl.elements;

import org.steamflake.core.domain.base.model.api.utilities.IFileOrigin;
import org.steamflake.core.domain.base.model.impl.elements.SteamflakeNamedModelElement;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmPackage;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmParameter;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmRootPackage;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmRule;

import java.util.Optional;

/**
 * Implementation of Steamflake template parameter.
 */
public class SteamflakeTmParameter
    extends SteamflakeNamedModelElement<ISteamflakeTmRootPackage, ISteamflakeTmPackage>
    implements ISteamflakeTmParameter {

    @SuppressWarnings( "TypeMayBeWeakened" )
    public SteamflakeTmParameter(
        SteamflakeTmRule parent,
        Optional<IFileOrigin> origin,
        String name,
        Optional<String> description,
        String typeName
    ) {
        super( parent, origin, name, description );
        this.typeName = typeName;

        parent.onAddChild( this );
    }

    @Override
    public ISteamflakeTmRule getParent() {
        return (ISteamflakeTmRule) super.getParent();
    }

    @Override
    public String getTypeName() {
        return this.typeName;
    }

    private final String typeName;

}
