//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.javamodel.impl.elements;

import org.steamflake.core.domain.base.model.api.utilities.IFileOrigin;
import org.steamflake.core.domain.base.model.api.utilities.IQualifiedName;
import org.steamflake.core.domain.base.model.impl.elements.SteamflakeModelElement;
import org.steamflake.core.domain.javamodel.api.elements.IJavaComponent;
import org.steamflake.core.domain.javamodel.api.elements.IJavaPackage;
import org.steamflake.core.domain.javamodel.api.elements.IJavaReferenceType;
import org.steamflake.core.domain.javamodel.api.elements.IJavaRootPackage;

import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.empty;

/**
 * A reference type.
 */
public final class JavaReferenceType
    extends SteamflakeModelElement<IJavaRootPackage, IJavaPackage>
    implements IJavaReferenceType {

    /**
     * Constructs a new type reference.
     *
     * @param component The referenced component defining this type
     */
    JavaReferenceType( JavaComponent component ) {
        super( IFileOrigin.UNUSED, empty() );

        this.component = component;
        this.typeArgs = new ArrayList<>();
    }

    @Override
    public void addTypeArgument( IJavaComponent typeArg ) {
        this.typeArgs.add( typeArg );
    }

    @Override
    public String getName() {
        return this.component.getName() + this.getTypeArgsForName();
    }

    @Override
    public IQualifiedName getQualifiedName() {
        return this.component.getQualifiedName();
    }

    @Override
    public boolean isImplicitlyImported() {
        return this.component.getParent().isImplicitlyImported();
    }

    private String getTypeArgsForName() {

        if ( this.typeArgs.isEmpty() ) {
            return "";
        }

        StringBuilder result = new StringBuilder( "<" );
        String delimiter = "";
        for ( IJavaComponent typeArg : this.typeArgs ) {
            result.append( delimiter );
            result.append( typeArg.getName() );
            delimiter = ",";
        }
        result.append( ">" );

        return result.toString();
    }

    private final JavaComponent component;

    private final List<IJavaComponent> typeArgs;

}
