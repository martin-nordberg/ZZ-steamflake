//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.javamodel.impl.elements;

import org.steamflake.core.domain.javamodel.api.elements.EJavaAccessibility;
import org.steamflake.core.domain.javamodel.api.elements.IJavaComponent;
import org.steamflake.core.domain.javamodel.api.elements.IJavaMember;
import org.steamflake.core.domain.javamodel.api.elements.IJavaType;

import java.util.Optional;

/**
 * A member.
 */
public abstract class JavaMember
    extends JavaTypedModelElement
    implements IJavaMember {

    /**
     * Constructs a new member.
     */
    @SuppressWarnings( "TypeMayBeWeakened" )
    protected JavaMember(
        JavaComponent parent,
        String name,
        Optional<String> description,
        EJavaAccessibility accessibility,
        boolean isStatic,
        boolean isFinal,
        IJavaType type
    ) {
        super( parent, name, description, type );

        this.accessibility = accessibility;
        this.isStatic = isStatic;
        this.isFinal = isFinal;
    }

    @Override
    public EJavaAccessibility getAccessibility() {
        return this.accessibility;
    }

    @Override
    public IJavaComponent getParent() {
        return (IJavaComponent) super.getParent();
    }

    @Override
    public boolean isFinal() {
        return this.isFinal;
    }

    @Override
    public boolean isStatic() {
        return this.isStatic;
    }

    private final EJavaAccessibility accessibility;

    private final boolean isFinal;

    private final boolean isStatic;

}
