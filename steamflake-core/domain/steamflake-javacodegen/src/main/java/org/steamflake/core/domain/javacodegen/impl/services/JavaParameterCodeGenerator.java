package org.steamflake.core.domain.javacodegen.impl.services;

import org.steamflake.core.domain.javacodegen.api.services.JavaCodeGenerator;
import org.steamflake.core.domain.javamodel.api.elements.IJavaParameter;
import org.steamflake.core.domain.javamodel.api.services.IJavaModelConsumerService;
import org.steamflake.core.persistence.ioutilities.codegen.CodeWriter;

/**
 * Code generator for a Java parameter.
 */
public final class JavaParameterCodeGenerator
    extends JavaAnnotatableModelElementCodeGenerator
    implements IJavaModelConsumerService<IJavaParameter, CodeWriter> {

    private JavaParameterCodeGenerator() {
    }

    @SuppressWarnings( "ParameterNameDiffersFromOverriddenParameter" )
    @Override
    public void consume(
        IJavaParameter parameter, CodeWriter writer
    ) {

        this.writeAnnotations( parameter, writer, false );

        parameter.getType().consume( JavaCodeGenerator.INSTANCE, writer );

        writer.spaceOrWrap()
              .append( parameter.getJavaName() );

    }

    public static final JavaParameterCodeGenerator INSTANCE = new JavaParameterCodeGenerator();

}
