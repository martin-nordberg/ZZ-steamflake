//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.model.impl.elements;

import org.steamflake.core.domain.base.model.api.elements.ESteamflakeAbstractness;
import org.steamflake.core.domain.base.model.api.utilities.IFileOrigin;
import org.steamflake.core.infrastructure.utilities.collections.IIndexable;
import org.steamflake.core.infrastructure.utilities.collections.ReadOnlyListAdapter;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmPackage;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of Steamflake template package.
 */
public class SteamflakeTmPackage
    extends SteamflakeTmAbstractPackage
    implements ISteamflakeTmPackage {

    /**
     * Constructs a new Java package.
     */
    SteamflakeTmPackage(
        SteamflakeTmAbstractPackage parent, Optional<IFileOrigin> origin, String name, Optional<String> description
    ) {
        super( parent, origin, name, description );

        this.templates = new ArrayList<>();

        parent.onAddChild( this );
    }

    @Override
    public ISteamflakeTmTemplate addTemplate(
        Optional<IFileOrigin> origin,
        String name,
        Optional<String> description,
        ESteamflakeAbstractness abstractness,
        Optional<ISteamflakeTmTemplate> baseTemplate
    ) {
        return new SteamflakeTmTemplate(
            this, origin, name, description, abstractness, baseTemplate
        );
    }

    @Override
    public IIndexable<ISteamflakeTmTemplate> getTemplates() {
        return new ReadOnlyListAdapter<>( this.templates );
    }

    /** Responds to the event of adding a child template to this package. */
    void onAddChild( ISteamflakeTmTemplate child ) {
        super.onAddChild( child );
        this.templates.add( child );
    }

    private final List<ISteamflakeTmTemplate> templates;

}
