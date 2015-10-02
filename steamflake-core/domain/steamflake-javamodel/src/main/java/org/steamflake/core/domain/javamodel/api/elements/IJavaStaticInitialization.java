//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.javamodel.api.elements;

import org.steamflake.core.domain.javamodel.api.statements.IJavaCodeBlock;

/**
 * A static initialization block.
 */
public interface IJavaStaticInitialization
    extends IJavaCodeBlock {

    @Override
    IJavaConcreteComponent getParent();

}
