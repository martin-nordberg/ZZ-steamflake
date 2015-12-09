package org.steamflake.core.domain.javacodegen.impl.services;

import org.steamflake.core.domain.base.model.api.elements.ISteamflakeModelElement;
import org.steamflake.core.domain.base.model.spi.ISteamflakeModelConsumerService;
import org.steamflake.core.domain.javacodegen.api.services.JavaCodeGenerator;
import org.steamflake.core.domain.javamodel.api.elements.IJavaMethod;
import org.steamflake.core.domain.javamodel.api.elements.IJavaPackage;
import org.steamflake.core.domain.javamodel.api.elements.IJavaParameter;
import org.steamflake.core.domain.javamodel.api.elements.IJavaRootPackage;
import org.steamflake.core.domain.javamodel.api.statements.IJavaStatement;
import org.steamflake.core.persistence.codeio.codegen.api.CodeWriter;

/**
 * Code generator for a Java field.
 */
public final class JavaMethodCodeGenerator
    extends JavaMemberCodeGenerator
    implements ISteamflakeModelConsumerService<IJavaRootPackage, IJavaPackage, CodeWriter> {

    private JavaMethodCodeGenerator() {
    }

    @SuppressWarnings( "ParameterNameDiffersFromOverriddenParameter" )
    @Override
    public <E extends ISteamflakeModelElement<IJavaRootPackage, IJavaPackage>> void consume(
        E element, CodeWriter writer
    ) {

        IJavaMethod method = (IJavaMethod) element;

        // JavaDoc
        if ( method.getDescription().isPresent() ) {

            writer.append( "/**" )
                  .spaceOrWrap( " * " )
                  .appendProse( method.getDescription().get(), " * " );

            if ( !method.getParameters().isEmpty() ) {

                for ( IJavaParameter parameter : method.getParameters() ) {

                    writer.alwaysWrap( " * @param " )
                          .append( parameter.getName() );

                    if ( parameter.getDescription().isPresent() ) {
                        writer.append( " " )
                              .appendProse( parameter.getDescription().get(), " *     " );
                    }

                }

            }

            // TODO: need a return value description

            // TODO: throws declaration(s)

            writer.spaceOrWrap( " " )
                  .append( "*/" )
                  .newLine();

        }

        // Annotations
        this.writeAnnotations( method, writer, true );

        // Qualifiers
        this.writeQualifiers( method, writer );

        // Return type
        method.getType().consume( JavaCodeGenerator.INSTANCE, writer );

        // Name
        writer.spaceOrWrap()
              .append( method.getName() );

        // Parameters
        writer.append( "(" )
              .spaceOrWrapIndentIf( !method.getParameters().isEmpty() )
              .mark();

        for ( IJavaParameter parameter : method.getParameters() ) {
            parameter.consume( JavaCodeGenerator.INSTANCE, writer );
            writer.mark()
                  .append( "," )
                  .spaceOrWrap();
        }

        writer.revertToMark()
              .spaceOrWrapUnindentIf( !method.getParameters().isEmpty() )
              .append( ")" );

        // Throws
        // TODO

        if ( method.getAbstractness().isAbstract() ) {
            // Abstract empty body
            writer.append( ";" );
        }
        else {
            // Concrete body
            writer.append( " {" )
                  .newLine()
                  .indent();

            for ( IJavaStatement statement : method.getCodeBlock().getStatements() ) {
                statement.consume( JavaCodeGenerator.INSTANCE, writer );
            }

            writer.unindent()
                  .append( "}" );
        }

        writer.newLine()
              .newLine();

    }

    public static final JavaMethodCodeGenerator INSTANCE = new JavaMethodCodeGenerator();

}
