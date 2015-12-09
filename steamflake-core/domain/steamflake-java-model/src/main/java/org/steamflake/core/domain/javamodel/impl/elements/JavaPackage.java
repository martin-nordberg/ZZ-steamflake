//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.javamodel.impl.elements;

import org.steamflake.core.domain.base.model.api.elements.ESteamflakeAbstractness;
import org.steamflake.core.domain.javamodel.api.elements.IJavaAnnotationInterface;
import org.steamflake.core.domain.javamodel.api.elements.IJavaClass;
import org.steamflake.core.domain.javamodel.api.elements.IJavaComponent;
import org.steamflake.core.domain.javamodel.api.elements.IJavaEnumeration;
import org.steamflake.core.domain.javamodel.api.elements.IJavaInterface;
import org.steamflake.core.domain.javamodel.api.elements.IJavaPackage;
import org.steamflake.core.infrastructure.utilities.collections.IIndexable;
import org.steamflake.core.infrastructure.utilities.collections.ReadOnlyListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A package.
 */
public final class JavaPackage
    extends JavaAbstractPackage
    implements IJavaPackage {

    /**
     * Constructs a new Java package.
     */
    JavaPackage(
        JavaAbstractPackage parent, String name, Optional<String> description, boolean isImplicitlyImported
    ) {
        super( parent, name, description );

        this.isImplicitlyImported = isImplicitlyImported;

        this.classes = new ArrayList<>();
        this.components = new ArrayList<>();
        this.enumerations = new ArrayList<>();
        this.interfaces = new ArrayList<>();
        this.annotationInterfaces = new ArrayList<>();

        parent.onAddChild( this );
    }

    @Override
    public IJavaAnnotationInterface addAnnotationInterface( String name, Optional<String> description ) {
        return new JavaAnnotationInterface( this, name, description );
    }

    @SuppressWarnings( "BooleanParameter" )
    @Override
    public IJavaClass addClass(
        String name,
        Optional<String> description,
        ESteamflakeAbstractness abstractness,
        Optional<IJavaClass> baseClass
    ) {
        return new JavaClass(
            this, name, description, false, abstractness, baseClass
        );
    }

    @SuppressWarnings( "BooleanParameter" )
    @Override
    public IJavaEnumeration addEnumeration( String name, Optional<String> description, boolean isExternal ) {
        return new JavaEnumeration( this, name, description, isExternal );
    }

    @Override
    public IJavaClass addExternalClass( String name ) {
        return new JavaClass( this, name, Optional.empty(), true, ESteamflakeAbstractness.CONCRETE, null );
    }

    @Override
    public IJavaInterface addExternalInterface( String name ) {
        return new JavaInterface( this, name, Optional.empty(), true );
    }

    @Override
    public IJavaInterface addInterface( String name, Optional<String> description ) {
        return new JavaInterface( this, name, description, false );
    }

    @Override
    public Optional<IJavaAnnotationInterface> findAnnotationInterface( String relativeQualifiedName ) {

        // split the relative path into first and rest
        if ( relativeQualifiedName.indexOf( '.' ) > 0 ) {
            return super.findAnnotationInterface( relativeQualifiedName );
        }

        // look for an existing component
        for ( IJavaAnnotationInterface annotationInterface : this.annotationInterfaces ) {
            if ( annotationInterface.getName().equals( relativeQualifiedName ) ) {
                return Optional.of( annotationInterface );
            }
        }

        return Optional.empty();
    }

    @Override
    public Optional<IJavaComponent> findComponent( String relativeQualifiedName ) {

        // split the relative path into first and rest
        if ( relativeQualifiedName.indexOf( '.' ) > 0 ) {
            return super.findComponent( relativeQualifiedName );
        }

        // look for an existing component
        for ( IJavaComponent component : this.components ) {
            if ( component.getName().equals( relativeQualifiedName ) ) {
                return Optional.of( component );
            }
        }

        return Optional.empty();
    }

    @Override
    public IIndexable<IJavaAnnotationInterface> getAnnotationInterfaces() {
        return new ReadOnlyListAdapter<>( this.annotationInterfaces );
    }

    @Override
    public IIndexable<IJavaClass> getClasses() {
        return new ReadOnlyListAdapter<>( this.classes );
    }

    @Override
    public IIndexable<IJavaComponent> getComponents() {
        return new ReadOnlyListAdapter<>( this.components );
    }

    @Override
    public IIndexable<IJavaEnumeration> getEnumerations() {
        return new ReadOnlyListAdapter<>( this.enumerations );
    }

    @Override
    public IIndexable<IJavaInterface> getInterfaces() {
        return new ReadOnlyListAdapter<>( this.interfaces );
    }

    @Override
    public boolean isImplicitlyImported() {
        return this.isImplicitlyImported;
    }

    /** Responds to the event of adding a child to this model element. */
    void onAddChild( IJavaAnnotationInterface child ) {
        super.onAddChild( child );
        this.annotationInterfaces.add( child );
    }

    /** Responds to the event of adding a child to this model element. */
    void onAddChild( IJavaClass child ) {
        this.onAddChild( (IJavaComponent) child );
        this.classes.add( child );
    }

    /** Responds to the event of adding a child to this model element. */
    void onAddChild( IJavaComponent child ) {
        super.onAddChild( child );
        this.components.add( child );
    }

    /** Responds to the event of adding a child to this model element. */
    void onAddChild( IJavaEnumeration child ) {
        this.onAddChild( (IJavaComponent) child );
        this.enumerations.add( child );
    }

    /** Responds to the event of adding a child to this model element. */
    void onAddChild( IJavaInterface child ) {
        this.onAddChild( (IJavaComponent) child );
        this.interfaces.add( child );
    }

    private final List<IJavaAnnotationInterface> annotationInterfaces;

    private final List<IJavaClass> classes;

    private final List<IJavaComponent> components;

    private final List<IJavaEnumeration> enumerations;

    private final List<IJavaInterface> interfaces;

    private final boolean isImplicitlyImported;

}
