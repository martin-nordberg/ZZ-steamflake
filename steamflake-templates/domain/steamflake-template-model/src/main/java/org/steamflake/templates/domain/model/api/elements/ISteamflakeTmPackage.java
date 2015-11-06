//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.model.api.elements;

import org.steamflake.core.domain.base.model.api.elements.ESteamflakeAbstractness;
import org.steamflake.core.domain.base.model.api.elements.ESteamflakeAccessibility;
import org.steamflake.core.infrastructure.utilities.collections.IIndexable;
import org.steamflake.core.infrastructure.utilities.files.FileOrigin;

import java.util.Optional;

/**
 * A concrete package of Steamflake templates.
 */
public interface ISteamflakeTmPackage
    extends ISteamflakeTmAbstractPackage {

    /**
     * Creates a template within this package.
     *
     * @param origin        the source file location of the template.
     * @param name          the name of the template.
     * @param description   an optional description of the template.
     * @param accessibility the accessibility of the template.
     * @param abstractness  the abstractness of the template.
     * @param baseTemplate  the base template
     *
     * @return the newly added template.
     */
    ISteamflakeTmTemplate addTemplate(
        Optional<FileOrigin> origin,
        String name,
        Optional<String> description,
        ESteamflakeAccessibility accessibility,
        ESteamflakeAbstractness abstractness,
        Optional<ISteamflakeTmTemplate> baseTemplate
    );

    /**
     * @return the templates within this package.
     */
    IIndexable<ISteamflakeTmTemplate> getTemplates();

}
