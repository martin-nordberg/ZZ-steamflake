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
    PUBLIC( "public" ),

    /** Accessible by any inheriting element. */
    PROTECTED( "protected" ),

    /** Accessible from any package in the parent module. */
    MODULE( "module" ),

    /** Accessible only within the parent package. */
    LOCAL( "local" ),

    /** Accessible only from the enclosing element. */
    PRIVATE( "private" );

    ESteamflakeAccessibility( String keyWord ) {
        this.keyWord = keyWord;
    }

    public String getKeyWord() {
        return this.keyWord;
    }

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

    private final String keyWord;

}
