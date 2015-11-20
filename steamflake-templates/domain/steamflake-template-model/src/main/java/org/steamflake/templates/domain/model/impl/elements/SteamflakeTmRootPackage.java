//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.model.impl.elements;

import org.steamflake.core.infrastructure.utilities.files.FileOrigin;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmRootPackage;

import static java.util.Optional.of;

/**
 * Implementation of Steamflake template root package.
 */
public class SteamflakeTmRootPackage
    extends SteamflakeTmAbstractPackage
    implements ISteamflakeTmRootPackage {

    /**
     * Constructs a new root Steamflake template package.
     */
    public SteamflakeTmRootPackage() {
        super( null, FileOrigin.UNUSED, "", of( "Steamflake Templates Root Package" ) );
    }

}
