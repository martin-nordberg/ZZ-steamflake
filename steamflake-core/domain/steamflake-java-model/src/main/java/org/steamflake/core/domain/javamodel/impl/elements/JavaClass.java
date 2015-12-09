//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.javamodel.impl.elements;

import org.steamflake.core.domain.base.model.api.elements.ESteamflakeAbstractness;
import org.steamflake.core.domain.javamodel.api.elements.IJavaClass;
import org.steamflake.core.domain.javamodel.api.elements.IJavaType;

import java.util.Optional;
import java.util.Set;

/**
 * A Java class.
 */
public final class JavaClass
    extends JavaConcreteComponent
    implements IJavaClass {

    /**
     * Constructs a new class.
     */
    JavaClass(
        JavaPackage parent,
        String name,
        Optional<String> description,
        boolean isExternal,
        ESteamflakeAbstractness abstractness,
        Optional<IJavaClass> baseClass
    ) {
        super( parent, name, description, isExternal );

        this.abstractness = abstractness;
        this.baseClass = baseClass;

        parent.onAddChild( this );
    }

    @Override
    public ESteamflakeAbstractness getAbstractness() {
        return this.abstractness;
    }

    @Override
    public Optional<IJavaClass> getBaseClass() {
        return this.baseClass;
    }

    @Override
    public Set<IJavaType> getImports() {
        Set<IJavaType> result = super.getImports();

        if ( this.baseClass.isPresent() ) {
            result.add( this.baseClass.get().getType() );
        }

        return result;
    }

    @Override
    public void setBaseClass( IJavaClass baseClass ) {
        assert !this.baseClass.isPresent() : "Cannot change base class once set.";
        this.baseClass = Optional.of( baseClass );
    }

    private final ESteamflakeAbstractness abstractness;

    private Optional<IJavaClass> baseClass;

}
