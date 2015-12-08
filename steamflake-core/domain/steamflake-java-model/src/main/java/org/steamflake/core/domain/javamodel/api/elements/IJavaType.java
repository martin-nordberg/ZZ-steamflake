//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.javamodel.api.elements;

import org.steamflake.core.domain.base.model.api.elements.ISteamflakeNamedModelElement;
import org.steamflake.core.domain.base.model.api.utilities.IQualifiedName;

/**
 * A Java type.
 */
@SuppressWarnings( "ComparableImplementedButEqualsNotOverridden" )
public interface IJavaType
    extends ISteamflakeNamedModelElement<IJavaRootPackage, IJavaPackage>, Comparable<IJavaType> {

    @SuppressWarnings( "NullableProblems" )
    @Override
    default int compareTo( IJavaType that ) {
        return this.getQualifiedName().getPath().compareTo( that.getQualifiedName().getPath() );
    }

    /**
     * @return the qualified name of this model element.
     */
    IQualifiedName getQualifiedName();

    /** Whether this type is implicitly imported. */
    boolean isImplicitlyImported();

}
