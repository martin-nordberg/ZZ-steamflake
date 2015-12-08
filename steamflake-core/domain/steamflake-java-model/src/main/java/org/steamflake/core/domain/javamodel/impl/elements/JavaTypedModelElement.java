//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.javamodel.impl.elements;

import org.steamflake.core.domain.base.model.impl.elements.SteamflakeNamedContainerElement;
import org.steamflake.core.domain.javamodel.api.elements.IJavaPackage;
import org.steamflake.core.domain.javamodel.api.elements.IJavaRootPackage;
import org.steamflake.core.domain.javamodel.api.elements.IJavaType;
import org.steamflake.core.domain.javamodel.api.elements.IJavaTypedModelElement;

import java.util.Optional;
import java.util.Set;

/**
 * A typed model element.
 */
public abstract class JavaTypedModelElement
    extends JavaAnnotatableModelElement
    implements IJavaTypedModelElement {

    /**
     * Constructs a new member.
     */
    protected JavaTypedModelElement(
        SteamflakeNamedContainerElement<IJavaRootPackage, IJavaPackage> parent,
        String name,
        Optional<String> description,
        IJavaType type
    ) {
        super( parent, name, description );

        this.type = type;
    }

    @Override
    public Set<IJavaType> getImports() {
        Set<IJavaType> result = super.getImports();

        result.add( this.type );

        return result;
    }

    @Override
    public IJavaType getType() {
        return this.type;
    }

    private final IJavaType type;

}
