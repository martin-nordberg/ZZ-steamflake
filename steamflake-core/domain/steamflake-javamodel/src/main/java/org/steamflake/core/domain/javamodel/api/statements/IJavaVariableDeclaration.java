//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.javamodel.api.statements;

import org.steamflake.core.domain.base.model.api.elements.ISteamflakeNamedModelElement;
import org.steamflake.core.domain.javamodel.api.elements.IJavaPackage;
import org.steamflake.core.domain.javamodel.api.elements.IJavaRootPackage;
import org.steamflake.core.domain.javamodel.api.elements.IJavaTyped;

import java.util.Optional;

/**
 * A statement defining a variable.
 */
public interface IJavaVariableDeclaration
    extends ISteamflakeNamedModelElement<IJavaRootPackage, IJavaPackage>, IJavaStatement, IJavaTyped {

    /**
     * @return the initial value of the variable.
     */
    Optional<String> getInitialValue();

}
