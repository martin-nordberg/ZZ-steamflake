package org.steamflake.core.domain.javacodegen.impl.services;

import org.steamflake.core.domain.javamodel.api.elements.IJavaBuiltinType;
import org.steamflake.core.domain.javamodel.api.services.IJavaModelConsumerService;
import org.steamflake.core.persistence.ioutilities.codegen.CodeWriter;

/**
 * Code generator for a Java field.
 */
public final class JavaBuiltinTypeCodeGenerator
    extends JavaMemberCodeGenerator
    implements IJavaModelConsumerService<IJavaBuiltinType, CodeWriter> {

    private JavaBuiltinTypeCodeGenerator() {
    }

    @SuppressWarnings( "ParameterNameDiffersFromOverriddenParameter" )
    @Override
    public void consume(
        IJavaBuiltinType type, CodeWriter writer
    ) {
        writer.append( type.getJavaName() );
    }

    public static final JavaBuiltinTypeCodeGenerator INSTANCE = new JavaBuiltinTypeCodeGenerator();

}
