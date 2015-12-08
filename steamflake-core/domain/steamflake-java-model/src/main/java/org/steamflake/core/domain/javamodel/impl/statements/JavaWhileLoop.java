//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.javamodel.impl.statements;

import org.steamflake.core.domain.javamodel.api.statements.IJavaWhileLoop;

import java.util.Optional;

/**
 * A Java while loop.
 */
public class JavaWhileLoop
    extends JavaCompositeStatement
    implements IJavaWhileLoop {

    /**
     * Constructs a new Java while loop.
     *
     * @param codeBlock     the container of statements.
     * @param description   a description of this model element.
     * @param loopCondition expression for the value returned.
     */
    protected JavaWhileLoop(
        JavaCodeBlock codeBlock,
        Optional<String> description,
        String loopCondition
    ) {
        super( codeBlock, description );
        this.loopCondition = loopCondition;
    }

    @Override
    public String getLoopCondition() {
        return this.loopCondition;
    }

    private final String loopCondition;
}
