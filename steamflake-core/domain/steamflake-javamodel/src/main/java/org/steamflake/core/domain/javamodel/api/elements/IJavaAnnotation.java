//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.javamodel.api.elements;

import org.steamflake.core.domain.base.model.api.elements.ISteamflakeModelElement;

import java.util.Optional;

/**
 * An annotation.
 */
public interface IJavaAnnotation
    extends ISteamflakeModelElement<IJavaRootPackage, IJavaPackage> {

    /** @return the annotationInterface. */
    IJavaAnnotationInterface getAnnotationInterface();

    /** @return the parametersCode. */
    Optional<String> getParametersCode();

    @Override
    IJavaAnnotatableModelElement getParent();

}
