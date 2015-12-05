package org.steamflake.core.domain.javacodegen.impl.services;

import org.steamflake.core.domain.javacodegen.api.services.JavaCodeGenerator;
import org.steamflake.core.domain.javamodel.api.elements.IJavaConstructor;
import org.steamflake.core.domain.javamodel.api.elements.IJavaParameter;
import org.steamflake.core.domain.javamodel.api.services.IJavaModelConsumerService;
import org.steamflake.core.domain.javamodel.api.statements.IJavaStatement;
import org.steamflake.core.persistence.codeio.codegen.api.CodeWriter;

/**
 * Code generator for a Java constructor.
 */
public final class JavaConstructorCodeGenerator
    extends JavaMemberCodeGenerator
    implements IJavaModelConsumerService<IJavaConstructor, CodeWriter> {

    private JavaConstructorCodeGenerator() {
    }

    @SuppressWarnings( "ParameterNameDiffersFromOverriddenParameter" )
    @Override
    public void consume(
        IJavaConstructor constructor, CodeWriter writer
    ) {

        // JavaDoc
        if ( constructor.getDescription().isPresent() ) {

            writer.append( "/**" )
                  .spaceOrWrap( " * " )
                  .appendProse( constructor.getDescription().get(), " * " );

            if ( !constructor.getParameters().isEmpty() ) {

                for ( IJavaParameter parameter : constructor.getParameters() ) {

                    writer.alwaysWrap( " * @param " )
                          .append( parameter.getJavaName() );

                    if ( parameter.getDescription().isPresent() ) {
                        writer.append( " " )
                              .appendProse( parameter.getDescription().get(), " *     " );
                    }

                }

            }

            // TODO: throws declaration(s)

            writer.spaceOrWrap( " " )
                  .append( "*/" )
                  .newLine();

        }

        // Annotations
        this.writeAnnotations( constructor, writer, true );

        // Qualifiers
        this.writeQualifiers( constructor, writer );

        // Name
        writer.append( constructor.getJavaName() );

        // Parameters
        writer.append( "(" )
              .spaceOrWrapIndentIf( !constructor.getParameters().isEmpty() )
              .mark();

        for ( IJavaParameter parameter : constructor.getParameters() ) {
            parameter.consume( JavaCodeGenerator.INSTANCE, writer );
            writer.mark()
                  .append( "," )
                  .spaceOrWrap();
        }

        writer.revertToMark()
              .spaceOrWrapUnindentIf( !constructor.getParameters().isEmpty() )
              .append( ")" );

        // Throws
        // TODO

        // Body
        writer.append( " {" )
              .newLine()
              .indent();

        for ( IJavaStatement statement : constructor.getStatements() ) {
            statement.consume( JavaCodeGenerator.INSTANCE, writer );
        }

        writer.unindent()
              .append( "}" );

        writer.newLine()
              .newLine();

    }

    public static final JavaConstructorCodeGenerator INSTANCE = new JavaConstructorCodeGenerator();

}
