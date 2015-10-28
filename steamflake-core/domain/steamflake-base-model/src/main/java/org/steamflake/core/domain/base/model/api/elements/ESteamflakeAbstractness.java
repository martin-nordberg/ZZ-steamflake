//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.base.model.api.elements;

/**
 * Enumeration of Steamflake abstractness levels.
 */
public enum ESteamflakeAbstractness {

    ABSTRACT( "abstract" ),

    CONCRETE( "concrete" );

    /** Constructs a new abstractness instance. */
    ESteamflakeAbstractness( String keyWord ) {
        this.keyWord = keyWord;
    }

    /** @return the keyword for this abstractness. */
    public String getKeyWord() {
        return this.keyWord;
    }

    /** @return whether this instance is abstract. */
    public boolean isAbstract() {
        return this == ESteamflakeAbstractness.ABSTRACT;
    }

    /** @return whether this instance is concrete. */
    public boolean isConcrete() {
        return this == ESteamflakeAbstractness.CONCRETE;
    }

    private final String keyWord;

}
