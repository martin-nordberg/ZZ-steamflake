//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.javamodel.impl.statements;

import org.steamflake.core.domain.base.model.api.utilities.IFileOrigin;
import org.steamflake.core.domain.base.model.impl.elements.SteamflakeModelElement;
import org.steamflake.core.domain.javamodel.api.elements.IJavaPackage;
import org.steamflake.core.domain.javamodel.api.elements.IJavaRootPackage;
import org.steamflake.core.domain.javamodel.api.statements.IJavaCodeBlock;
import org.steamflake.core.domain.javamodel.api.statements.IJavaStatement;

import java.util.Optional;

/**
 * Implementation for a Java statement.
 */
public class JavaStatement
    extends SteamflakeModelElement<IJavaRootPackage, IJavaPackage>
    implements IJavaStatement {

    /**
     * Constructs a new Java statement.
     *
     * @param parent      the helper object containing the statements of the parent code block.
     * @param description a description of this model element.
     */
    @SuppressWarnings( "TypeMayBeWeakened" )
    protected JavaStatement(
        JavaCodeBlock parent,
        Optional<String> description
    ) {
        super( parent, IFileOrigin.UNUSED, description );

        parent.onAddChild( this );
    }

    @Override
    public IJavaCodeBlock getParent() {
        return (IJavaCodeBlock) super.getParent();
    }

}
