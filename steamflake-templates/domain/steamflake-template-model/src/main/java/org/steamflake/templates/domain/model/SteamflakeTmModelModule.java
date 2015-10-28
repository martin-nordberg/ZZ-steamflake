//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.model;

import dagger.Module;
import dagger.Provides;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmRootPackage;
import org.steamflake.templates.domain.model.impl.elements.SteamflakeTmRootPackage;

/**
 * Dagger dependency injection module.
 */
@Module( library = true )
public class SteamflakeTmModelModule {

    /**
     * Constructs a new root package.
     *
     * @return the newly created root package.
     */
    @Provides
    public ISteamflakeTmRootPackage provideSteamflakeTmRootPackage() {
        return new SteamflakeTmRootPackage();
    }

}
