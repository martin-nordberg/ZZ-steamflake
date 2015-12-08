//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.javamodel.api.statements;

/**
 * Interface to a while loop.
 */
public interface IJavaWhileLoop
    extends IJavaCompositeStatement {

    /**
     * @return the code for the expression controlling the loop.
     */
    String getLoopCondition();

}
