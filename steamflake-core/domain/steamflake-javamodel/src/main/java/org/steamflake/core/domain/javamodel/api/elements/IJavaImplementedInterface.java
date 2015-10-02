//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.javamodel.api.elements;

/**
 * An implements clause.
 */
public interface IJavaImplementedInterface
    extends IJavaModelElement {

    /** Returns the implementedInterface. */
    IJavaInterface getImplementedInterface();

    @Override
    IJavaComponent getParent();

}
