//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.javamodel.impl.elements;

import org.steamflake.core.domain.base.model.api.utilities.IFileOrigin;
import org.steamflake.core.domain.base.model.impl.elements.SteamflakeModelElement;
import org.steamflake.core.domain.javamodel.api.elements.IJavaAnnotatableModelElement;
import org.steamflake.core.domain.javamodel.api.elements.IJavaAnnotation;
import org.steamflake.core.domain.javamodel.api.elements.IJavaAnnotationInterface;
import org.steamflake.core.domain.javamodel.api.elements.IJavaPackage;
import org.steamflake.core.domain.javamodel.api.elements.IJavaRootPackage;

import java.util.Optional;

/**
 * An annotation
 */
public final class JavaAnnotation
    extends SteamflakeModelElement<IJavaRootPackage, IJavaPackage>
    implements IJavaAnnotation {

    /**
     * Constructs a new annotation (usage).
     */
    JavaAnnotation(
        JavaAnnotatableModelElement parent,
        Optional<String> description,
        IJavaAnnotationInterface annotationInterface,
        Optional<String> parametersCode
    ) {
        super( parent, IFileOrigin.UNUSED, description );

        this.annotationInterface = annotationInterface;
        this.parametersCode = parametersCode;

        parent.onAddChild( this );
    }

    @Override
    public IJavaAnnotationInterface getAnnotationInterface() {
        return this.annotationInterface;
    }

    @Override
    public Optional<String> getParametersCode() {
        return this.parametersCode;
    }

    @Override
    public IJavaAnnotatableModelElement getParent() {
        return (IJavaAnnotatableModelElement) super.getParent();
    }

    private final IJavaAnnotationInterface annotationInterface;

    private final Optional<String> parametersCode;

}
