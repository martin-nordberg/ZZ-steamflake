//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.javamodel.impl.statements;

import org.steamflake.core.domain.base.model.api.utilities.IFileOrigin;
import org.steamflake.core.domain.base.model.impl.elements.SteamflakeContainerElement;
import org.steamflake.core.domain.javamodel.api.elements.IJavaPackage;
import org.steamflake.core.domain.javamodel.api.elements.IJavaRootPackage;
import org.steamflake.core.domain.javamodel.api.statements.IJavaCodeBlock;
import org.steamflake.core.domain.javamodel.api.statements.IJavaCompositeStatement;

import java.util.Optional;

import static java.util.Optional.empty;

/**
 * A Java return statement.
 */
public class JavaCompositeStatement
    extends SteamflakeContainerElement<IJavaRootPackage, IJavaPackage>
    implements IJavaCompositeStatement {

    /**
     * Constructs a new Java composite statement.
     *
     * @param parent      the parent container of this statement.
     * @param description a description of this model element.
     */
    protected JavaCompositeStatement(
        JavaCodeBlock parent,
        Optional<String> description
    ) {
        super( parent, IFileOrigin.UNUSED, description );
        this.codeBlock = new JavaCodeBlock( this, empty() );
    }

    @Override
    public IJavaCodeBlock getCodeBlock() {
        return this.codeBlock;
    }

    @Override
    public IJavaCodeBlock getParent() {
        return (IJavaCodeBlock) super.getParent();
    }

    private final JavaCodeBlock codeBlock;

}
