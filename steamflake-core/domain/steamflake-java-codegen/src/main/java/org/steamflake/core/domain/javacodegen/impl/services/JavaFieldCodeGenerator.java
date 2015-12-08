package org.steamflake.core.domain.javacodegen.impl.services;

import org.steamflake.core.domain.base.model.api.elements.ISteamflakeModelElement;
import org.steamflake.core.domain.base.model.spi.ISteamflakeModelConsumerService;
import org.steamflake.core.domain.javacodegen.api.services.JavaCodeGenerator;
import org.steamflake.core.domain.javamodel.api.elements.IJavaField;
import org.steamflake.core.domain.javamodel.api.elements.IJavaPackage;
import org.steamflake.core.domain.javamodel.api.elements.IJavaRootPackage;
import org.steamflake.core.persistence.codeio.codegen.api.CodeWriter;

/**
 * Code generator for a Java field.
 */
public final class JavaFieldCodeGenerator
    extends JavaMemberCodeGenerator
    implements ISteamflakeModelConsumerService<IJavaRootPackage, IJavaPackage, CodeWriter> {

    private JavaFieldCodeGenerator() {
    }

    @SuppressWarnings( "ParameterNameDiffersFromOverriddenParameter" )
    @Override
    public <E extends ISteamflakeModelElement<IJavaRootPackage, IJavaPackage>> void consume(
        E element, CodeWriter writer
    ) {

        IJavaField field = (IJavaField) element;

        // JavaDoc
        if ( field.getDescription().isPresent() ) {
            writer.append( "/**" )
                  .spaceOrWrap( " * " )
                  .appendProse( field.getDescription().get(), " * " )
                  .spaceOrWrap( " " )
                  .append( "*/" )
                  .newLine();
        }

        // Annotations
        this.writeAnnotations( field, writer, true );

        // Qualifiers
        this.writeQualifiers( field, writer );

        // Type
        field.getType().consume( JavaCodeGenerator.INSTANCE, writer );

        // Name
        writer.spaceOrWrap()
              .append( field.getName() );

        // Initial value
        if ( field.getInitialValueCode().isPresent() ) {
            writer.append( " =" )
                  .spaceOrWrap()
                  .append( field.getInitialValueCode().get() );
        }

        // Ending punctuation
        writer.append( ";" )
              .newLine()
              .newLine();

    }

    public static final JavaFieldCodeGenerator INSTANCE = new JavaFieldCodeGenerator();

}
