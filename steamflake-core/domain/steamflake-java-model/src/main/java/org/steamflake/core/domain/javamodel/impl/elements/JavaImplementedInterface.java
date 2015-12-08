//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.javamodel.impl.elements;

import org.steamflake.core.domain.base.model.api.utilities.IFileOrigin;
import org.steamflake.core.domain.base.model.impl.elements.SteamflakeModelElement;
import org.steamflake.core.domain.javamodel.api.elements.IJavaComponent;
import org.steamflake.core.domain.javamodel.api.elements.IJavaImplementedInterface;
import org.steamflake.core.domain.javamodel.api.elements.IJavaInterface;
import org.steamflake.core.domain.javamodel.api.elements.IJavaPackage;
import org.steamflake.core.domain.javamodel.api.elements.IJavaRootPackage;

import java.util.Optional;

/**
 * An implements clause.
 */
public final class JavaImplementedInterface
    extends SteamflakeModelElement<IJavaRootPackage, IJavaPackage>
    implements IJavaImplementedInterface {

    /**
     * Constructs a new base interface (extends/implements)
     */
    JavaImplementedInterface( JavaComponent parent, IJavaInterface implementedInterface ) {
        super( parent, IFileOrigin.UNUSED, Optional.empty() );

        this.implementedInterface = implementedInterface;

        parent.onAddChild( this );
    }

    @Override
    public IJavaInterface getImplementedInterface() {
        return this.implementedInterface;
    }

    @Override
    public IJavaComponent getParent() {
        return (IJavaComponent) super.getParent();
    }

    private final IJavaInterface implementedInterface;

}
