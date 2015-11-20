//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.base.model.impl.utilities;

import org.steamflake.core.domain.base.model.api.utilities.IQualifiedName;

import java.util.Optional;

/**
 * Implementation of a qualified name (path).
 */
public final class QualifiedName
    implements IQualifiedName {

    /**
     * Constructs a new qualified name.
     *
     * @param unqualifiedName the core name.
     * @param parent          the optional parent qualified name.
     */
    public QualifiedName( String unqualifiedName, Optional<IQualifiedName> parent ) {
        assert unqualifiedName.isEmpty() != parent.isPresent();

        this.unqualifiedName = unqualifiedName;
        this.parent = parent;
    }

    @Override
    public boolean equals( Object other ) {
        if ( this == other ) {
            return true;
        }
        if ( other == null || this.getClass() != other.getClass() ) {
            return false;
        }

        IQualifiedName that = (IQualifiedName) other;

        return this.getPath().equals( that.getPath() );
    }

    @Override
    public String getName() {
        return this.unqualifiedName;
    }

    @Override
    public String getPath() {
        if ( !this.parent.isPresent() ) {
            return this.unqualifiedName;
        }

        if ( this.parent.get().getPath().isEmpty() ) {
            return this.unqualifiedName;
        }

        return this.parent.get().getPath() + "." + this.unqualifiedName;
    }

    @Override
    public int hashCode() {
        int result = this.unqualifiedName.hashCode();
        result = 31 * result + this.parent.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return this.unqualifiedName;
    }

    private final Optional<IQualifiedName> parent;

    private final String unqualifiedName;

}
