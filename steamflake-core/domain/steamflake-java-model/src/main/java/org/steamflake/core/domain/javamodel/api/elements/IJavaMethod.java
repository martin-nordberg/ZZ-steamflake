//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.javamodel.api.elements;

import org.steamflake.core.domain.base.model.api.elements.ESteamflakeAbstractness;

/**
 * A method.
 */
public interface IJavaMethod
    extends IJavaFunction {

    /** @return whether this is an abstract method. */
    ESteamflakeAbstractness getAbstractness();

}
