//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.javamodel.api.elements;

import java.util.Optional;

/**
 * A Java class.
 */
public interface IJavaClass
    extends IJavaConcreteComponent {

    /** @return the baseClass. */
    Optional<IJavaClass> getBaseClass();

    /** @return whether this is an abstract class. */
    boolean isAbstract();

    /** @return whether this is a final class. */
    boolean isFinal();

    /**
     * Sets the base class.
     *
     * @param baseClass the base class for this class.
     */
    void setBaseClass( IJavaClass baseClass );

}
