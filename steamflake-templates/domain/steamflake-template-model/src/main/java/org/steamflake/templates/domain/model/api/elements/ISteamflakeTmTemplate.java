//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.model.api.elements;

import org.steamflake.core.domain.base.model.api.elements.ESteamflakeAbstractness;
import org.steamflake.core.domain.base.model.api.elements.ESteamflakeAccessibility;
import org.steamflake.core.domain.base.model.api.elements.ISteamflakeNamedModelElement;
import org.steamflake.core.domain.base.model.api.utilities.IFileOrigin;
import org.steamflake.core.infrastructure.utilities.collections.IIndexable;

import java.util.Optional;

/**
 * Interface to a Steamflake template.
 */
public interface ISteamflakeTmTemplate
    extends ISteamflakeNamedModelElement<ISteamflakeTmRootPackage, ISteamflakeTmPackage> {

    /**
     * Creates a rule within this template.
     *
     * @param origin      the source file location of the new rule.
     * @param name        the name of the rule.
     * @param description a description of the rule.
     *
     * @return the newly created rule.
     */
    default ISteamflakeTmRule addRule(
        Optional<IFileOrigin> origin,
        String name,
        String description
    ) {
        return this.addRule(
            origin,
            name,
            Optional.of( description ),
            ESteamflakeAccessibility.PUBLIC,
            ESteamflakeAbstractness.CONCRETE
        );
    }

    /**
     * Creates a rule within this template.
     *
     * @param origin           the source file location of the new rule.
     * @param name             the name of the rule.
     * @param description      a description of the rule.
     * @param accessibility    the accessibility of the rule.
     * @param ruleAbstractness whether the new rule is abstract or concrete.
     *
     * @return the newly created method.
     */
    @SuppressWarnings( "BooleanParameter" )
    ISteamflakeTmRule addRule(
        Optional<IFileOrigin> origin,
        String name,
        Optional<String> description,
        ESteamflakeAccessibility accessibility,
        ESteamflakeAbstractness ruleAbstractness
    );

    /**
     * @return whether this is an abstract or concrete template.
     */
    ESteamflakeAbstractness getAbstractness();

    /**
     * @return the base template.
     */
    Optional<ISteamflakeTmTemplate> getBaseTemplate();

    /**
     * @return the rules within this template.
     */
    IIndexable<ISteamflakeTmRule> getRules();

    /**
     * Sets the base template.
     *
     * @param baseTemplate the base template for this template.
     */
    void setBaseTemplate( ISteamflakeTmTemplate baseTemplate );

}
