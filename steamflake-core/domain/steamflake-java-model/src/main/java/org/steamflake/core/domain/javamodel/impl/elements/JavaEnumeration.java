//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.javamodel.impl.elements;

import org.steamflake.core.domain.javamodel.api.elements.IJavaEnumConstant;
import org.steamflake.core.domain.javamodel.api.elements.IJavaEnumeration;
import org.steamflake.core.infrastructure.utilities.collections.IIndexable;
import org.steamflake.core.infrastructure.utilities.collections.ReadOnlyListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * An enumeration.
 */
public class JavaEnumeration
    extends JavaConcreteComponent
    implements IJavaEnumeration {

    /**
     * Constructs a new class.
     */
    JavaEnumeration( JavaPackage parent, String name, Optional<String> description, boolean isExternal ) {
        super( parent, name, description, isExternal );

        this.enumConstants = new ArrayList<>();

        parent.onAddChild( this );
    }

    @Override
    public IJavaEnumConstant addEnumConstant(
        String name, Optional<String> description, Optional<String> parametersCode
    ) {
        return new JavaEnumConstant( this, name, description, parametersCode );
    }

    @Override
    public IIndexable<IJavaEnumConstant> getEnumConstants() {
        return new ReadOnlyListAdapter<>( this.enumConstants );
    }

    /** Responds to the event of adding a child to this model element. */
    void onAddChild( IJavaEnumConstant child ) {
        super.onAddChild( child );
        this.enumConstants.add( child );
    }

    private final List<IJavaEnumConstant> enumConstants;

}
