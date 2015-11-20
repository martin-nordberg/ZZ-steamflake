//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.model.api.elements;

import org.steamflake.core.domain.base.model.api.elements.ESteamflakeAbstractness;
import org.steamflake.core.domain.base.model.api.elements.ESteamflakeAccessibility;
import org.steamflake.core.domain.base.model.api.elements.ISteamflakeNamedModelElement;
import org.steamflake.core.infrastructure.utilities.collections.IIndexable;
import org.steamflake.core.infrastructure.utilities.files.FileOrigin;

import java.util.Optional;

/**
 * Interface to a Steamflake template.
 */
public interface ISteamflakeTmTemplate
    extends ISteamflakeNamedModelElement<ISteamflakeTmRootPackage, ISteamflakeTmPackage> {

    /**
     * Adds a new import to this template.
     *
     * @param origin   the file and location the import appeared.
     * @param typeName the fully qualified name of the type imported.
     * @param alias    a different name used for the type in its parent template.
     *
     * @return the newly created import.
     */
    ISteamflakeTmImport addImport(
        Optional<FileOrigin> origin,
        String typeName,
        Optional<String> alias
    );

    /**
     * Creates a rule within this template.
     *
     * @param origin            the source file location of the new rule.
     * @param name              the name of the rule.
     * @param description       a description of the rule.
     * @param ruleAccessibility the accessibility of the rule.
     * @param ruleAbstractness  whether the new rule is abstract or concrete.
     *
     * @return the newly created method.
     */
    @SuppressWarnings( "BooleanParameter" )
    ISteamflakeTmRule addRule(
        Optional<FileOrigin> origin,
        String name,
        Optional<String> description,
        ESteamflakeAccessibility ruleAccessibility,
        ESteamflakeAbstractness ruleAbstractness
    );

    /**
     * @return whether this is an abstract or concrete template.
     */
    ESteamflakeAbstractness getAbstractness();

    /**
     * @return the accessibility of this template.
     */
    ESteamflakeAccessibility getAccessibility();

    /**
     * @return the base template.
     */
    Optional<ISteamflakeTmTemplate> getBaseTemplate();

    /**
     * @return the imports within this template.
     */
    IIndexable<ISteamflakeTmImport> getImports();

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
