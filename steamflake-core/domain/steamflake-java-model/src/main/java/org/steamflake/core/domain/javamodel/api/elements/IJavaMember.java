//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.javamodel.api.elements;

import org.steamflake.core.domain.base.model.api.elements.ESteamflakeAccessibility;

/**
 * A member.
 */
public interface IJavaMember
    extends IJavaTypedModelElement {

    /** @return the accessibility of this member. */
    ESteamflakeAccessibility getAccessibility();

    /** @return the parent of this member. */
    @Override
    IJavaComponent getParent();

    /** @return whether this is a final member. */
    boolean isFinal();

    /** @return whether this is a static member. */
    boolean isStatic();

}
