//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.model.impl.elements;

import org.steamflake.core.domain.base.model.impl.elements.SteamflakeModelElement;
import org.steamflake.core.infrastructure.utilities.files.FileOrigin;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmImport;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmPackage;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmRootPackage;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmTemplate;

import java.util.Optional;

/**
 * Implementation of a Steamflake template import.
 */
public class SteamflakeTmImport
    extends SteamflakeModelElement<ISteamflakeTmRootPackage, ISteamflakeTmPackage>
    implements ISteamflakeTmImport {

    @SuppressWarnings( "TypeMayBeWeakened" )
    public SteamflakeTmImport(
        SteamflakeTmTemplate parent,
        Optional<FileOrigin> origin,
        String typeName,
        Optional<String> alias
    ) {
        super( parent, origin, Optional.empty() );

        this.typeName = typeName;
        this.alias = alias;

        parent.onAddChild( this );
    }

    @Override
    public Optional<String> getAlias() {
        return this.alias;
    }

    @Override
    public ISteamflakeTmTemplate getParent() {
        return (ISteamflakeTmTemplate) super.getParent();
    }

    @Override
    public String getTypeName() {
        return this.typeName;
    }

    private final Optional<String> alias;

    private final String typeName;

}
