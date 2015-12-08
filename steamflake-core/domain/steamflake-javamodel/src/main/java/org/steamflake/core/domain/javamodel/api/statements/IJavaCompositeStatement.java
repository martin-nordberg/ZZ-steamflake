//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.javamodel.api.statements;

import org.steamflake.core.domain.base.model.api.elements.ISteamflakeContainerElement;
import org.steamflake.core.domain.javamodel.api.elements.IJavaPackage;
import org.steamflake.core.domain.javamodel.api.elements.IJavaRootPackage;

/**
 * Interface to a Java statement that has a code block of further statements within it.
 */
public interface IJavaCompositeStatement
    extends IJavaStatement, ISteamflakeContainerElement<IJavaRootPackage, IJavaPackage> {

    /**
     * @return the code block within this while loop.
     */
    IJavaCodeBlock getCodeBlock();

}
