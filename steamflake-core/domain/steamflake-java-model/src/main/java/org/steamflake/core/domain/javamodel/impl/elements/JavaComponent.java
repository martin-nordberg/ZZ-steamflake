//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.javamodel.impl.elements;

import org.steamflake.core.domain.base.model.api.elements.ESteamflakeAbstractness;
import org.steamflake.core.domain.base.model.api.elements.ESteamflakeAccessibility;
import org.steamflake.core.domain.base.model.impl.elements.SteamflakeNamedContainerElement;
import org.steamflake.core.domain.javamodel.api.elements.IJavaComponent;
import org.steamflake.core.domain.javamodel.api.elements.IJavaFunction;
import org.steamflake.core.domain.javamodel.api.elements.IJavaImplementedInterface;
import org.steamflake.core.domain.javamodel.api.elements.IJavaMember;
import org.steamflake.core.domain.javamodel.api.elements.IJavaMethod;
import org.steamflake.core.domain.javamodel.api.elements.IJavaPackage;
import org.steamflake.core.domain.javamodel.api.elements.IJavaReferenceType;
import org.steamflake.core.domain.javamodel.api.elements.IJavaRootPackage;
import org.steamflake.core.domain.javamodel.api.elements.IJavaType;
import org.steamflake.core.infrastructure.utilities.collections.IIndexable;
import org.steamflake.core.infrastructure.utilities.collections.ReadOnlyListAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * An interface, enum, or class.
 */
public abstract class JavaComponent
    extends JavaAnnotatableModelElement
    implements IJavaComponent {

    /**
     * Constructs a new component.
     */
    protected JavaComponent(
        SteamflakeNamedContainerElement<IJavaRootPackage, IJavaPackage> parent,
        String name,
        Optional<String> description,
        boolean isExternal
    ) {
        super( parent, name, description );

        this.isExternal = isExternal;

        this.implementedInterfaces = new ArrayList<>();
        this.members = new ArrayList<>();
        this.methods = new ArrayList<>();
    }

    /** Creates a method within this component. */
    @SuppressWarnings( "BooleanParameter" )
    @Override
    public IJavaMethod addMethod(
        String name,
        Optional<String> description,
        ESteamflakeAccessibility accessibility,
        ESteamflakeAbstractness abstractness,
        boolean isStatic,
        boolean isFinal,
        IJavaType returnType
    ) {
        return new JavaMethod(
            this, name, description, accessibility, abstractness, isStatic, isFinal, returnType
        );
    }

    @Override
    public IIndexable<IJavaImplementedInterface> getImplementedInterfaces() {
        return new ReadOnlyListAdapter<>( this.implementedInterfaces );
    }

    @Override
    public Set<IJavaType> getImports() {
        Set<IJavaType> result = super.getImports();

        for ( IJavaMethod method : this.getMethods() ) {
            result.addAll( method.getImports() );
        }

        for ( IJavaImplementedInterface implInterface : this.implementedInterfaces ) {
            result.add( implInterface.getImplementedInterface().getType() );
        }

        return result;
    }

    @Override
    public IIndexable<IJavaMethod> getMethods() {
        List<IJavaMethod> result = new ArrayList<>( this.methods );
        Collections.sort(
            result, ( IJavaFunction m1, IJavaFunction m2 ) -> {
                int cresult = Boolean.compare( m2.isStatic(), m1.isStatic() );
                if ( cresult == 0 ) {
                    cresult = m1.getAccessibility().compareTo( m2.getAccessibility() );
                }
                if ( cresult == 0 ) {
                    cresult = m1.getName().compareTo( m2.getName() );
                }
                if ( cresult == 0 ) {
                    cresult = m1.getParameters().size() - m2.getParameters().size();
                }
                return cresult;
            }
        );
        return new ReadOnlyListAdapter<>( result );
    }

    @Override
    public IJavaPackage getParent() {
        return (IJavaPackage) super.getParent();
    }

    @Override
    public IJavaType getType() {
        return new JavaReferenceType( this );
    }

    @Override
    public boolean isExternal() {
        return this.isExternal;
    }

    @Override
    public IJavaType makeGenericType( IJavaComponent typeArg1 ) {
        IJavaReferenceType result = new JavaReferenceType( this );
        result.addTypeArgument( typeArg1 );
        return result;
    }

    /** Responds to the event of adding a child to this model element. */
    void onAddChild( IJavaImplementedInterface child ) {
        super.onAddChild( child );
        this.implementedInterfaces.add( child );
    }

    /** Responds to the event of adding a child to this model element. */
    void onAddChild( IJavaMember child ) {
        super.onAddChild( child );
        this.members.add( child );
    }

    /** Responds to the event of adding a child to this model element. */
    void onAddChild( IJavaMethod child ) {
        this.onAddChild( (IJavaMember) child );
        this.methods.add( child );
    }

    private final List<IJavaImplementedInterface> implementedInterfaces;

    private final boolean isExternal;

    private final List<IJavaMember> members;

    private final List<IJavaMethod> methods;

}
