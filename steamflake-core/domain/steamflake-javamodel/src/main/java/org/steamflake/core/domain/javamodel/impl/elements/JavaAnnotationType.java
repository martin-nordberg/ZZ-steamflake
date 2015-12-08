//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.javamodel.impl.elements;

import org.steamflake.core.domain.base.model.api.utilities.IFileOrigin;
import org.steamflake.core.domain.base.model.api.utilities.IQualifiedName;
import org.steamflake.core.domain.base.model.impl.elements.SteamflakeModelElement;
import org.steamflake.core.domain.javamodel.api.elements.IJavaAnnotationInterface;
import org.steamflake.core.domain.javamodel.api.elements.IJavaAnnotationType;
import org.steamflake.core.domain.javamodel.api.elements.IJavaPackage;
import org.steamflake.core.domain.javamodel.api.elements.IJavaRootPackage;

import static java.util.Optional.empty;

/**
 * An annotation type.
 */
public final class JavaAnnotationType
    extends SteamflakeModelElement<IJavaRootPackage, IJavaPackage>
    implements IJavaAnnotationType {

    /**
     * Constructs a annotation type.
     *
     * @param annotationInterface The referenced annotation interface defining this type
     */
    JavaAnnotationType( IJavaAnnotationInterface annotationInterface ) {
        super( IFileOrigin.UNUSED, empty() );

        this.annotationInterface = annotationInterface;
    }

    @Override
    public String getName() {
        return this.annotationInterface.getName();
    }

    @Override
    public IQualifiedName getQualifiedName() {
        return this.annotationInterface.getQualifiedName();
    }

    @Override
    public boolean isImplicitlyImported() {
        return this.annotationInterface.getParent().isImplicitlyImported();
    }

    private final IJavaAnnotationInterface annotationInterface;

}
