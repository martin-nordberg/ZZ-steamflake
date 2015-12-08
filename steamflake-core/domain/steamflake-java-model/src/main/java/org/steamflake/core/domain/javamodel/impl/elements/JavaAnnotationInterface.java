//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.javamodel.impl.elements;

import org.steamflake.core.domain.javamodel.api.elements.IJavaAnnotationInterface;
import org.steamflake.core.domain.javamodel.api.elements.IJavaPackage;
import org.steamflake.core.domain.javamodel.api.elements.IJavaType;

import java.util.Optional;

/**
 * An annotation definition
 */
public class JavaAnnotationInterface
    extends JavaAnnotatableModelElement
    implements IJavaAnnotationInterface {

    /**
     * Constructs a new annotation
     */
    JavaAnnotationInterface( JavaPackage parent, String name, Optional<String> description ) {
        super( parent, name, description );

        parent.onAddChild( this );
    }

    @Override
    public IJavaPackage getParent() {
        return (IJavaPackage) super.getParent();
    }

    @Override
    public IJavaType getType() {
        return new JavaAnnotationType( this );
    }

}
