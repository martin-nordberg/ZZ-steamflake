//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.javamodel.impl.elements;

import org.steamflake.core.domain.javamodel.api.elements.EJavaAccessibility;
import org.steamflake.core.domain.javamodel.api.elements.IJavaEnumConstant;
import org.steamflake.core.domain.javamodel.api.elements.IJavaEnumeration;

import java.util.Optional;

/**
 * An Enum constant.
 */
public final class JavaEnumConstant
    extends JavaMember
    implements IJavaEnumConstant {

    /**
     * Constructs a new field.
     */
    JavaEnumConstant(
        JavaEnumeration parent,
        String name,
        Optional<String> description,
        Optional<String> parametersCode
    ) {
        super(
            parent, name, description, EJavaAccessibility.PUBLIC, true, true, new JavaReferenceType( parent )
        );

        this.parametersCode = parametersCode;

        parent.onAddChild( this );
    }

    @Override
    public Optional<String> getParametersCode() {
        return this.parametersCode;
    }

    @Override
    public IJavaEnumeration getParent() {
        return (IJavaEnumeration) super.getParent();
    }

    private final Optional<String> parametersCode;

}
