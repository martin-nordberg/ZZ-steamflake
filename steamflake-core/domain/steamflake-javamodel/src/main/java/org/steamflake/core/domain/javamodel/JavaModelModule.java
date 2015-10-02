//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.javamodel;

import dagger.Module;
import dagger.Provides;
import org.steamflake.core.domain.javamodel.api.elements.IJavaRootPackage;
import org.steamflake.core.domain.javamodel.impl.elements.JavaRootPackage;

import javax.inject.Singleton;

/**
 * Dagger dependency injection module.
 */
@SuppressWarnings( "ClassNamePrefixedWithPackageName" )
@Module( library = true )
public class JavaModelModule {

    /**
     * Constructs a new root package.
     *
     * @return the newly created root package.
     */
    @Provides
    @Singleton
    public IJavaRootPackage provideJavaRootPackage() {
        return new JavaRootPackage();
    }

}
