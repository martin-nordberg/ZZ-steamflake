//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.transform.impl.services.output;

import org.steamflake.core.domain.javamodel.api.elements.IJavaPackage;
import org.steamflake.core.domain.javamodel.api.elements.IJavaRootPackage;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmPackage;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmRootPackage;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmTemplate;

import static java.util.Optional.empty;

/**
 * Transformer to translate a Steamflake Template model into a Java model.
 */
public class SteamflakeTmToJavaOutputModelTransformer {

    /**
     * Transforms an entire Steamflake template model into a Java model.
     *
     * @param rootPackage     the root template model.
     * @param javaRootPackage the root Java model (to be modified).
     */
    public void transformRootPackage( ISteamflakeTmRootPackage rootPackage, IJavaRootPackage javaRootPackage ) {

        for ( ISteamflakeTmPackage subpkg : rootPackage.getPackages() ) {
            this.transformPackage(
                subpkg,
                javaRootPackage.findOrCreatePackage( subpkg.getName() )
            );
        }

    }

    private void transformPackage( ISteamflakeTmPackage pkg, IJavaPackage javaPkg ) {

        for ( ISteamflakeTmTemplate template : pkg.getTemplates() ) {
            javaPkg.addClass(
                template.getName(),
                template.getDescription(),
                template.getAbstractness(),
                empty() // TODO
            );
        }

        for ( ISteamflakeTmPackage subpkg : pkg.getPackages() ) {

            IJavaPackage javaSubPkg = javaPkg.findOrCreatePackage( subpkg.getName() );

            this.transformPackage(
                subpkg,
                javaSubPkg
            );

        }

    }
}
