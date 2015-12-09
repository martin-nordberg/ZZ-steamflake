//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.javamodel.impl.elements;

import org.steamflake.core.domain.base.model.api.elements.ESteamflakeAbstractness;
import org.steamflake.core.domain.base.model.api.elements.ESteamflakeAccessibility;
import org.steamflake.core.domain.javamodel.api.elements.IJavaMethod;
import org.steamflake.core.domain.javamodel.api.elements.IJavaType;

import java.util.Optional;

/**
 * A method.
 */
public final class JavaMethod
    extends JavaFunction
    implements IJavaMethod {

    /**
     * Constructs a new method.
     */
    JavaMethod(
        JavaComponent parent,
        String name,
        Optional<String> description,
        ESteamflakeAccessibility accessibility,
        ESteamflakeAbstractness abstractness,
        boolean isStatic,
        boolean isFinal,
        IJavaType returnType
    ) {
        super( parent, name, description, accessibility, isStatic, isFinal, returnType );

        this.abstractness = abstractness;

        parent.onAddChild( this );
    }

    @Override
    public ESteamflakeAbstractness getAbstractness() {
        return this.abstractness;
    }

    private final ESteamflakeAbstractness abstractness;

}
