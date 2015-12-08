package org.steamflake.core.domain.javacodegen.impl.services;

import org.steamflake.core.domain.base.model.api.elements.ISteamflakeModelElement;
import org.steamflake.core.domain.base.model.spi.ISteamflakeModelConsumerService;
import org.steamflake.core.domain.javacodegen.api.services.JavaCodeGenerator;
import org.steamflake.core.domain.javamodel.api.elements.IJavaFunction;
import org.steamflake.core.domain.javamodel.api.elements.IJavaPackage;
import org.steamflake.core.domain.javamodel.api.elements.IJavaParameter;
import org.steamflake.core.domain.javamodel.api.elements.IJavaRootPackage;
import org.steamflake.core.domain.javamodel.api.statements.IJavaStatement;
import org.steamflake.core.persistence.codeio.codegen.api.CodeWriter;

/**
 * Code generator for a Java constructor.
 */
public final class JavaConstructorCodeGenerator
    extends JavaMemberCodeGenerator
    implements ISteamflakeModelConsumerService<IJavaRootPackage, IJavaPackage, CodeWriter> {

    private JavaConstructorCodeGenerator() {
    }

    @SuppressWarnings( "ParameterNameDiffersFromOverriddenParameter" )
    @Override
    public <E extends ISteamflakeModelElement<IJavaRootPackage, IJavaPackage>> void consume(
        E element, CodeWriter writer
    ) {

        IJavaFunction constructor = (IJavaFunction) element;


        // JavaDoc
        if ( constructor.getDescription().isPresent() ) {

            writer.append( "/**" )
                  .spaceOrWrap( " * " )
                  .appendProse( constructor.getDescription().get(), " * " );

            if ( !constructor.getParameters().isEmpty() ) {

                for ( IJavaParameter parameter : constructor.getParameters() ) {

                    writer.alwaysWrap( " * @param " )
                          .append( parameter.getName() );

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
        writer.append( constructor.getName() );

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

        for ( IJavaStatement statement : constructor.getCodeBlock().getStatements() ) {
            statement.consume( JavaCodeGenerator.INSTANCE, writer );
        }

        writer.unindent()
              .append( "}" );

        writer.newLine()
              .newLine();

    }

    public static final JavaConstructorCodeGenerator INSTANCE = new JavaConstructorCodeGenerator();

}
