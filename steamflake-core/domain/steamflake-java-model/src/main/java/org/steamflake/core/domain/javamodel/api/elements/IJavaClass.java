//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.javamodel.api.elements;

import org.steamflake.core.domain.base.model.api.elements.ESteamflakeAbstractness;

import java.util.Optional;

/**
 * A Java class.
 */
public interface IJavaClass
    extends IJavaConcreteComponent {

    /** @return whether this is an abstract class. */
    ESteamflakeAbstractness getAbstractness();

    /** @return the baseClass. */
    Optional<IJavaClass> getBaseClass();

    /**
     * Sets the base class.
     *
     * @param baseClass the base class for this class.
     */
    void setBaseClass( IJavaClass baseClass );

}
