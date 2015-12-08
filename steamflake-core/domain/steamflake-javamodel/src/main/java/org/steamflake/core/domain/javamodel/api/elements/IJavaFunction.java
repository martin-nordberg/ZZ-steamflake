//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.javamodel.api.elements;

import org.steamflake.core.domain.javamodel.api.statements.IJavaCodeBlock;
import org.steamflake.core.infrastructure.utilities.collections.IIndexable;

import java.util.Optional;

/**
 * A Java function (constructor or member).
 */
public interface IJavaFunction
    extends IJavaMember {

    /**
     * Creates a parameter for this method.
     *
     * @param name        the name of the parameter.
     * @param description a description of the parameter.
     * @param type        the type of the parameter.
     *
     * @return the newly created parameter.
     */
    default IJavaParameter addParameter( String name, String description, IJavaType type ) {
        return this.addParameter( name, Optional.of( description ), type );
    }

    /**
     * Creates a parameter for this method.
     *
     * @param name        the name of the parameter.
     * @param description an optional description of the parameter.
     * @param type        the type of the parameter.
     *
     * @return the newly created parameter.
     */
    IJavaParameter addParameter( String name, Optional<String> description, IJavaType type );

    /** @return the code block within this function. */
    IJavaCodeBlock getCodeBlock();

    /** @return the parameters within this method. */
    IIndexable<IJavaParameter> getParameters();

    /** @return the return type of this method. */
    IJavaType getReturnType();

}
