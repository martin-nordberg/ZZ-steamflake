//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.javamodel.api.statements;

import org.steamflake.core.domain.base.model.api.elements.ISteamflakeModelElement;
import org.steamflake.core.domain.javamodel.api.elements.IJavaPackage;
import org.steamflake.core.domain.javamodel.api.elements.IJavaRootPackage;

/**
 * Interface to a Java statement.
 */
public interface IJavaStatement
    extends ISteamflakeModelElement<IJavaRootPackage, IJavaPackage> {

    /**
     * @return the parent code block containing this statement.
     */
    @Override
    IJavaCodeBlock getParent();

}
