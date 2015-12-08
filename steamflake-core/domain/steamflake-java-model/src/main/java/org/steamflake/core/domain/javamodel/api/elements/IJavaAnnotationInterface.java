//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.javamodel.api.elements;

/**
 * An annotation definition
 */
public interface IJavaAnnotationInterface
    extends IJavaAnnotatableModelElement, IJavaTyped {

    @Override
    IJavaPackage getParent();

}
