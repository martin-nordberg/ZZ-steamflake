//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.javamodel.impl.elements;

import org.steamflake.core.domain.base.model.api.utilities.IFileOrigin;
import org.steamflake.core.domain.base.model.api.utilities.IQualifiedName;
import org.steamflake.core.domain.base.model.impl.elements.SteamflakeNamedModelElement;
import org.steamflake.core.domain.base.model.impl.utilities.QualifiedName;
import org.steamflake.core.domain.javamodel.api.elements.IJavaBuiltinType;
import org.steamflake.core.domain.javamodel.api.elements.IJavaPackage;
import org.steamflake.core.domain.javamodel.api.elements.IJavaRootPackage;

import static java.util.Optional.empty;
import static java.util.Optional.of;

/**
 * A built-in type.
 */
@SuppressWarnings( "javadoc" )
public final class JavaBuiltinType
    extends SteamflakeNamedModelElement<IJavaRootPackage, IJavaPackage>
    implements IJavaBuiltinType {

    /**
     * Constructs a new built in type.
     */
    JavaBuiltinType( JavaPackage parent, String name ) {
        super( parent, IFileOrigin.UNUSED, name, empty() );

        this.qualifiedName = new QualifiedName( name, of( parent.getQualifiedName() ) );
    }

    @Override
    public String getName() {
        return this.qualifiedName.getName();
    }

    @Override
    public IQualifiedName getQualifiedName() {
        return this.qualifiedName;
    }

    @Override
    public boolean isImplicitlyImported() {
        return true;
    }

    private final QualifiedName qualifiedName;

}
