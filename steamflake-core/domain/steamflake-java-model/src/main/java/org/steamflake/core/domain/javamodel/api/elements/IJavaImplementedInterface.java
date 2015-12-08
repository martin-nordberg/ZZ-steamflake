//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.javamodel.api.elements;

import org.steamflake.core.domain.base.model.api.elements.ISteamflakeModelElement;

/**
 * An implements clause.
 */
public interface IJavaImplementedInterface
    extends ISteamflakeModelElement<IJavaRootPackage, IJavaPackage> {

    /** Returns the implementedInterface. */
    IJavaInterface getImplementedInterface();

    @Override
    IJavaComponent getParent();

}
