//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.model.impl.elements;

import org.steamflake.core.domain.base.model.api.elements.ESteamflakeAbstractness;
import org.steamflake.core.domain.base.model.api.elements.ESteamflakeAccessibility;
import org.steamflake.core.domain.base.model.api.utilities.IFileOrigin;
import org.steamflake.core.domain.base.model.impl.elements.SteamflakeNamedModelElement;
import org.steamflake.core.infrastructure.utilities.collections.IIndexable;
import org.steamflake.core.infrastructure.utilities.collections.ReadOnlyListAdapter;
import org.steamflake.templates.domain.model.api.directives.ISteamflakeTmAbstractDirective;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmPackage;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmParameter;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmRootPackage;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmRule;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of a Steamflake template rule.
 */
public class SteamflakeTmRule
    extends SteamflakeNamedModelElement<ISteamflakeTmRootPackage, ISteamflakeTmPackage>
    implements ISteamflakeTmRule, ISteamflakeTmDirectiveContainerMixin {

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
        this.directives = new ArrayList<>();

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
    public IIndexable<ISteamflakeTmAbstractDirective> getDirectives() {
        return new ReadOnlyListAdapter<>( this.directives );
    }

    @Override
    public String getKeyword() {
        return "rule";
    }

    @Override
    public IIndexable<ISteamflakeTmParameter> getParameters() {
        return new ReadOnlyListAdapter<>( this.parameters );
    }

    @Override
    public ISteamflakeTmTemplate getParent() {
        return (ISteamflakeTmTemplate) super.getParent();
    }

    /** Responds to the event of adding a child directive to this rule. */
    public void onAddChild( ISteamflakeTmAbstractDirective child ) {
        super.onAddChild( child );
        this.directives.add( child );
    }

    /** Responds to the event of adding a child parameter to this rule. */
    void onAddChild( ISteamflakeTmParameter child ) {
        super.onAddChild( child );
        this.parameters.add( child );
    }

    private final ESteamflakeAbstractness abstractness;

    private final ESteamflakeAccessibility accessibility;

    private final List<ISteamflakeTmAbstractDirective> directives;

    private final List<ISteamflakeTmParameter> parameters;

}
