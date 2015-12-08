//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.javamodel.impl.elements;

import org.steamflake.core.domain.javamodel.api.elements.EJavaAccessibility;
import org.steamflake.core.domain.javamodel.api.elements.IJavaFunction;
import org.steamflake.core.domain.javamodel.api.elements.IJavaParameter;
import org.steamflake.core.domain.javamodel.api.elements.IJavaType;
import org.steamflake.core.domain.javamodel.api.statements.IJavaCodeBlock;
import org.steamflake.core.domain.javamodel.impl.statements.JavaCodeBlock;
import org.steamflake.core.infrastructure.utilities.collections.IIndexable;
import org.steamflake.core.infrastructure.utilities.collections.ReadOnlyListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.Optional.empty;

/**
 * A Java function (constructor or member).
 */
public abstract class JavaFunction
    extends JavaMember
    implements IJavaFunction {

    /**
     * Constructs a new method.
     */
    protected JavaFunction(
        JavaComponent parent,
        String name,
        Optional<String> description,
        EJavaAccessibility accessibility,
        boolean isStatic,
        boolean isFinal,
        IJavaType returnType
    ) {
        super( parent, name, description, accessibility, isStatic, isFinal, returnType );

        this.parameters = new ArrayList<>();
        this.codeBlock = new JavaCodeBlock( this, empty() );

        parent.onAddChild( this );
    }

    @Override
    public IJavaParameter addParameter( String name, Optional<String> description, IJavaType type ) {
        return new JavaParameter( this, name, description, type );
    }

    @Override
    public IJavaCodeBlock getCodeBlock() {
        return this.codeBlock;
    }

    @Override
    public Set<IJavaType> getImports() {
        Set<IJavaType> result = super.getImports();

        for ( IJavaParameter parameter : this.parameters ) {
            result.add( parameter.getType() );
        }

        return result;
    }

    @Override
    public IIndexable<IJavaParameter> getParameters() {
        return new ReadOnlyListAdapter<>( this.parameters );
    }

    @Override
    public IJavaType getReturnType() {
        return this.getType();
    }

    /** Responds to the event of adding a child to this model element. */
    void onAddChild( IJavaParameter child ) {
        super.onAddChild( child );
        this.parameters.add( child );
    }

    private final JavaCodeBlock codeBlock;

    private final List<IJavaParameter> parameters;

}
