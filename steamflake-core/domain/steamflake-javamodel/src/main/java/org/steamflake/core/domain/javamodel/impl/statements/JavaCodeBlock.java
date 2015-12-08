//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.javamodel.impl.statements;

import org.steamflake.core.domain.base.model.api.utilities.IFileOrigin;
import org.steamflake.core.domain.base.model.impl.elements.SteamflakeContainerElement;
import org.steamflake.core.domain.javamodel.api.elements.IJavaPackage;
import org.steamflake.core.domain.javamodel.api.elements.IJavaRootPackage;
import org.steamflake.core.domain.javamodel.api.elements.IJavaType;
import org.steamflake.core.domain.javamodel.api.statements.IJavaAssignmentStatement;
import org.steamflake.core.domain.javamodel.api.statements.IJavaCodeBlock;
import org.steamflake.core.domain.javamodel.api.statements.IJavaReturnStatement;
import org.steamflake.core.domain.javamodel.api.statements.IJavaStatement;
import org.steamflake.core.domain.javamodel.api.statements.IJavaVariableDeclaration;
import org.steamflake.core.domain.javamodel.api.statements.IJavaWhileLoop;
import org.steamflake.core.infrastructure.utilities.collections.IIndexable;
import org.steamflake.core.infrastructure.utilities.collections.ReadOnlyListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of a Java code block (non-mixin).
 */
public class JavaCodeBlock
    extends SteamflakeContainerElement<IJavaRootPackage, IJavaPackage>
    implements IJavaCodeBlock {

    /**
     * Constructs a new Java model element
     *
     * @param parent      the parent of this model element.
     * @param description a description of this model element.
     */
    public JavaCodeBlock(
        SteamflakeContainerElement<IJavaRootPackage, IJavaPackage> parent,
        Optional<String> description
    ) {
        super( parent, IFileOrigin.UNUSED, description );

        this.statements = new ArrayList<>();
    }

    @Override
    public IJavaAssignmentStatement addAssignmentStatement(
        Optional<String> description,
        String leftHandSide,
        String rightHandSide,
        Optional<String> extraOperator
    ) {
        return new JavaAssignmentStatement( this, description, leftHandSide, rightHandSide, extraOperator );
    }

    @Override
    public IJavaReturnStatement addReturnStatement( Optional<String> description, Optional<String> returnValue ) {
        return new JavaReturnStatement( this, description, returnValue );
    }

    @Override
    public IJavaVariableDeclaration addVariableDeclaration(
        String name, Optional<String> description, IJavaType type, Optional<String> initialValue
    ) {
        return new JavaVariableDeclaration( this, name, description, type, initialValue );
    }

    @Override
    public IJavaWhileLoop addWhileLoop( Optional<String> description, String loopCondition ) {
        return new JavaWhileLoop( this, description, loopCondition );
    }

    @Override
    public IIndexable<IJavaStatement> getStatements() {
        return new ReadOnlyListAdapter<>( this.statements );
    }

    void onAddChild( IJavaStatement statement ) {
        this.statements.add( statement );
    }

    private final List<IJavaStatement> statements;

}
