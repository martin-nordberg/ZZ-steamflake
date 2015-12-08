package org.steamflake.core.domain.javacodegen.api.services;

import org.steamflake.core.domain.base.model.api.elements.ISteamflakeModelElement;
import org.steamflake.core.domain.base.model.spi.ISteamflakeModelConsumerFactory;
import org.steamflake.core.domain.base.model.spi.ISteamflakeModelConsumerService;
import org.steamflake.core.domain.javacodegen.impl.services.JavaAssignmentStatementCodeGenerator;
import org.steamflake.core.domain.javacodegen.impl.services.JavaBuiltinTypeCodeGenerator;
import org.steamflake.core.domain.javacodegen.impl.services.JavaClassCodeGenerator;
import org.steamflake.core.domain.javacodegen.impl.services.JavaConstructorCodeGenerator;
import org.steamflake.core.domain.javacodegen.impl.services.JavaFieldCodeGenerator;
import org.steamflake.core.domain.javacodegen.impl.services.JavaMethodCodeGenerator;
import org.steamflake.core.domain.javacodegen.impl.services.JavaParameterCodeGenerator;
import org.steamflake.core.domain.javacodegen.impl.services.JavaStaticInitializationCodeGenerator;
import org.steamflake.core.domain.javamodel.api.elements.IJavaPackage;
import org.steamflake.core.domain.javamodel.api.elements.IJavaRootPackage;
import org.steamflake.core.persistence.codeio.codegen.api.CodeWriter;

import java.util.HashMap;
import java.util.Map;

/**
 * Java model extension service for generating Java source code.
 */
public final class JavaCodeGenerator
    implements ISteamflakeModelConsumerFactory<IJavaRootPackage, IJavaPackage, CodeWriter> {

    /**
     * Constructs a new code generator.
     */
    private JavaCodeGenerator() {

        this.consumers = new HashMap<>();

        // Map all the concrete classes to individual code generators.
        this.consumers.put( "JavaAssignmentStatement", JavaAssignmentStatementCodeGenerator.INSTANCE );
        this.consumers.put( "JavaBuiltinType", JavaBuiltinTypeCodeGenerator.INSTANCE );
        this.consumers.put( "JavaClass", JavaClassCodeGenerator.INSTANCE );
        this.consumers.put( "JavaConstructor", JavaConstructorCodeGenerator.INSTANCE );
        this.consumers.put( "JavaField", JavaFieldCodeGenerator.INSTANCE );
        this.consumers.put( "JavaMethod", JavaMethodCodeGenerator.INSTANCE );
        this.consumers.put( "JavaParameter", JavaParameterCodeGenerator.INSTANCE );
        this.consumers.put( "JavaStaticInitialization", JavaStaticInitializationCodeGenerator.INSTANCE );

    }

    @Override
    public <E extends ISteamflakeModelElement<IJavaRootPackage, IJavaPackage>> ISteamflakeModelConsumerService<IJavaRootPackage, IJavaPackage, CodeWriter> build(
        E element
    ) {

        String elementTypeName = element.getClass().getSimpleName();

        if ( !this.consumers.containsKey( elementTypeName ) ) {
            throw new IllegalArgumentException( "Unhandled Java model element type: " + elementTypeName );
        }

        return this.consumers.get( elementTypeName );

    }

    /**
     * The one and only Java code generator (factory).
     */
    public static final JavaCodeGenerator INSTANCE = new JavaCodeGenerator();

    /**
     * Map of code generators by concrete class name.
     */
    private final Map<String, ISteamflakeModelConsumerService<IJavaRootPackage, IJavaPackage, CodeWriter>> consumers;

}
