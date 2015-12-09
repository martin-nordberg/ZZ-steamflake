//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.base.model.api.elements;

/**
 * Enumeration of Steamflake accessibility levels.
 */
public enum ESteamflakeAccessibility {

    /** Accessible by any client. */
    PUBLIC,

    /** Accessible by any inheriting element. */
    PROTECTED,

    /** Accessible from any package in the parent module. */
    MODULE,

    /** Accessible only within the parent package. */
    LOCAL,

    /** Accessible only from the enclosing element. */
    PRIVATE;

    public boolean isLocal() {
        return this == ESteamflakeAccessibility.LOCAL;
    }

    public boolean isModule() {
        return this == ESteamflakeAccessibility.MODULE;
    }

    public boolean isPrivate() {
        return this == ESteamflakeAccessibility.PRIVATE;
    }

    public boolean isProtected() {
        return this == ESteamflakeAccessibility.PROTECTED;
    }

    public boolean isPublic() {
        return this == ESteamflakeAccessibility.PUBLIC;
    }

}
