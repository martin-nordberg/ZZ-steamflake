//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.base.model.api.elements;

/**
 * Enumeration of Steamflake abstractness levels.
 */
public enum ESteamflakeAbstractness {

    ABSTRACT,

    EXTENSIBLE,

    CONCRETE;

    /** @return whether this instance is abstract. */
    public boolean isAbstract() {
        return this == ESteamflakeAbstractness.ABSTRACT;
    }

    /** @return whether this instance is concrete. */
    public boolean isConcrete() {
        return this == ESteamflakeAbstractness.CONCRETE;
    }

    /** @return whether this instance is extensible (non-final). */
    public boolean isExtensible() {
        return this == ESteamflakeAbstractness.EXTENSIBLE;
    }

}
