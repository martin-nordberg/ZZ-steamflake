package org.steamflake.core.domain.javacodegen.impl.services;

import org.steamflake.core.domain.base.model.api.elements.ISteamflakeModelElement;
import org.steamflake.core.domain.base.model.spi.ISteamflakeModelConsumerService;
import org.steamflake.core.domain.javamodel.api.elements.IJavaBuiltinType;
import org.steamflake.core.domain.javamodel.api.elements.IJavaPackage;
import org.steamflake.core.domain.javamodel.api.elements.IJavaRootPackage;
import org.steamflake.core.persistence.codeio.codegen.api.CodeWriter;

/**
 * Code generator for a Java field.
 */
public final class JavaBuiltinTypeCodeGenerator
    extends JavaMemberCodeGenerator
    implements ISteamflakeModelConsumerService<IJavaRootPackage, IJavaPackage, CodeWriter> {

    private JavaBuiltinTypeCodeGenerator() {
    }

    @SuppressWarnings( { "ParameterNameDiffersFromOverriddenParameter", "OverlyStrongTypeCast" } )
    @Override
    public <E extends ISteamflakeModelElement<IJavaRootPackage, IJavaPackage>> void consume(
        E element, CodeWriter writer
    ) {

        writer.append( ( (IJavaBuiltinType) element ).getName() );
    }

    public static final JavaBuiltinTypeCodeGenerator INSTANCE = new JavaBuiltinTypeCodeGenerator();

}
