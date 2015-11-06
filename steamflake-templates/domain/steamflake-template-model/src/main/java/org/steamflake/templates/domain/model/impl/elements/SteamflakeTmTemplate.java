//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.model.impl.elements;

import org.steamflake.core.domain.base.model.api.elements.ESteamflakeAbstractness;
import org.steamflake.core.domain.base.model.api.elements.ESteamflakeAccessibility;
import org.steamflake.core.domain.base.model.impl.elements.SteamflakeNamedModelElement;
import org.steamflake.core.infrastructure.utilities.collections.IIndexable;
import org.steamflake.core.infrastructure.utilities.collections.ReadOnlyListAdapter;
import org.steamflake.core.infrastructure.utilities.files.FileOrigin;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmPackage;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmRootPackage;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmRule;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of Steamflake template.
 */
public class SteamflakeTmTemplate
    extends SteamflakeNamedModelElement<ISteamflakeTmRootPackage, ISteamflakeTmPackage>
    implements ISteamflakeTmTemplate {

    @SuppressWarnings( "TypeMayBeWeakened" )
    public SteamflakeTmTemplate(
        SteamflakeTmPackage parent,
        Optional<FileOrigin> origin,
        String name,
        Optional<String> description,
        ESteamflakeAccessibility accessibility,
        ESteamflakeAbstractness abstractness,
        Optional<ISteamflakeTmTemplate> baseTemplate
    ) {
        super( parent, origin, name, description );

        this.abstractness = abstractness;
        this.accessibility = accessibility;
        this.baseTemplate = baseTemplate;

        this.rules = new ArrayList<>();

        parent.onAddChild( this );
    }

    @Override
    public ISteamflakeTmRule addRule(
        Optional<FileOrigin> origin,
        String name,
        Optional<String> description,
        ESteamflakeAccessibility ruleAccessibility,
        ESteamflakeAbstractness ruleAbstractness
    ) {
        return new SteamflakeTmRule(
            this, origin, name, description, ruleAccessibility, ruleAbstractness
        );
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
    public Optional<ISteamflakeTmTemplate> getBaseTemplate() {
        return this.baseTemplate;
    }

    @Override
    public IIndexable<ISteamflakeTmRule> getRules() {
        return new ReadOnlyListAdapter<>( this.rules );
    }

    @Override
    public void setBaseTemplate( ISteamflakeTmTemplate baseTemplate ) {
        assert !this.baseTemplate.isPresent() : "Cannot change base template once set.";
        this.baseTemplate = Optional.of( baseTemplate );
    }

    /** Responds to the event of adding a child rule to this template. */
    void onAddChild( ISteamflakeTmRule child ) {
        super.onAddChild( child );
        this.rules.add( child );
    }

    private final ESteamflakeAbstractness abstractness;

    private final ESteamflakeAccessibility accessibility;

    private Optional<ISteamflakeTmTemplate> baseTemplate;

    private final List<ISteamflakeTmRule> rules;

}
