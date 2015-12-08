//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.model.impl.elements;

import org.steamflake.core.domain.base.model.api.elements.ESteamflakeAbstractness;
import org.steamflake.core.domain.base.model.api.elements.ESteamflakeAccessibility;
import org.steamflake.core.domain.base.model.api.utilities.IFileOrigin;
import org.steamflake.core.domain.base.model.impl.elements.SteamflakeNamedContainerElement;
import org.steamflake.core.infrastructure.utilities.collections.IIndexable;
import org.steamflake.core.infrastructure.utilities.collections.ReadOnlyListAdapter;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmPackage;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmParameter;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmRootPackage;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmRule;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;

/**
 * Implementation of a Steamflake template rule.
 */
public class SteamflakeTmRule
    extends SteamflakeNamedContainerElement<ISteamflakeTmRootPackage, ISteamflakeTmPackage>
    implements ISteamflakeTmRule {

    @SuppressWarnings( "TypeMayBeWeakened" )
    public SteamflakeTmRule(
        SteamflakeTmTemplate parent,
        Optional<IFileOrigin> origin,
        String name,
        Optional<String> description,
        ESteamflakeAccessibility accessibility,
        ESteamflakeAbstractness abstractness
    ) {
        super( parent, origin, name, description );

        this.accessibility = accessibility;
        this.abstractness = abstractness;

        this.parameters = new ArrayList<>();

        this.directiveSequence = new SteamflakeTmDirectiveSequence( this, IFileOrigin.UNUSED, empty() );

        parent.onAddChild( this );
    }

    @Override
    public ISteamflakeTmParameter addParameter(
        Optional<IFileOrigin> origin, String name, Optional<String> description, String typeName
    ) {
        return new SteamflakeTmParameter( this, origin, name, description, typeName );
    }

    @Override
    public ESteamflakeAbstractness getAbstractness() {
        return this.abstractness;
    }

    @Override
    public ESteamflakeAccessibility getAccessibility() {
        return this.accessibility;
    }

    @Override
    public SteamflakeTmDirectiveSequence getDirectiveSequence() {
        return this.directiveSequence;
    }

    @Override
    public IIndexable<ISteamflakeTmParameter> getParameters() {
        return new ReadOnlyListAdapter<>( this.parameters );
    }

    @Override
    public ISteamflakeTmTemplate getParent() {
        return (ISteamflakeTmTemplate) super.getParent();
    }

    /** Responds to the event of adding a child parameter to this rule. */
    void onAddChild( ISteamflakeTmParameter child ) {
        super.onAddChild( child );
        this.parameters.add( child );
    }

    private final ESteamflakeAbstractness abstractness;

    private final ESteamflakeAccessibility accessibility;

    private final SteamflakeTmDirectiveSequence directiveSequence;

    private final List<ISteamflakeTmParameter> parameters;

}
