//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.javamodel.api.statements;

import org.steamflake.core.domain.javamodel.api.elements.IJavaModelElement;

/**
 * Interface to a Java statement.
 */
public interface IJavaStatement
    extends IJavaModelElement {

    /**
     * @return the parent code block containing this statement.
     */
    @Override
    IJavaCodeBlock getParent();

}
