//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.templates.domain.codegen.api.services;

import org.steamflake.core.domain.javamodel.api.elements.IJavaRootPackage;
import org.steamflake.templates.domain.model.api.elements.ISteamflakeTmRootPackage;

/**
 * Service for generating Java models from a Steamflake Template model.
 */
public class SteamflakeTmToJavaModelTransformer {

    /**
     * Transforms a Steamflake template model into a Java model for the input/parsing of that template.
     * @param rootPackage the template model to transform.
     * @param javaRootPackage the Java model to augment with code for the template's parsing capability.
     */
    void generateJavaInputModel( ISteamflakeTmRootPackage rootPackage, IJavaRootPackage javaRootPackage ) {
        // TODO ...
    }

    /**
     * Transforms a Steamflake template model into a Java model for the output/pretty printing of that template.
     * @param rootPackage the template model to transform.
     * @param javaRootPackage the Java model to augment with code for the template's output/pretty printing capability.
     */
    void generateJavaOutputModel( ISteamflakeTmRootPackage rootPackage, IJavaRootPackage javaRootPackage ) {
        // TODO ...
    }

}
