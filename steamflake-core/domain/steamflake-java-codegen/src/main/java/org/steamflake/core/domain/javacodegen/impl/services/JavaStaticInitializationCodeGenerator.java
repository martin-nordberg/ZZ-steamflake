package org.steamflake.core.domain.javacodegen.impl.services;

import org.steamflake.core.domain.base.model.api.elements.ISteamflakeModelElement;
import org.steamflake.core.domain.base.model.spi.ISteamflakeModelConsumerService;
import org.steamflake.core.domain.javacodegen.api.services.JavaCodeGenerator;
import org.steamflake.core.domain.javamodel.api.elements.IJavaPackage;
import org.steamflake.core.domain.javamodel.api.elements.IJavaRootPackage;
import org.steamflake.core.domain.javamodel.api.statements.IJavaCodeBlock;
import org.steamflake.core.domain.javamodel.api.statements.IJavaStatement;
import org.steamflake.core.persistence.codeio.codegen.api.CodeWriter;

/**
 * Code generator for a Java field.
 */
public final class JavaStaticInitializationCodeGenerator
    extends JavaMemberCodeGenerator
    implements ISteamflakeModelConsumerService<IJavaRootPackage, IJavaPackage, CodeWriter> {

    private JavaStaticInitializationCodeGenerator() {
    }

    @SuppressWarnings( "ParameterNameDiffersFromOverriddenParameter" )
    @Override
    public <E extends ISteamflakeModelElement<IJavaRootPackage, IJavaPackage>> void consume(
        E element, CodeWriter writer
    ) {

        IJavaCodeBlock staticInitialization = (IJavaCodeBlock) element;

        // JavaDoc
        if ( staticInitialization.getDescription().isPresent() ) {

            writer.append( "/**" )
                  .spaceOrWrap( " * " )
                  .appendProse( staticInitialization.getDescription().get(), " * " )
                  .spaceOrWrap( " " )
                  .append( "*/" )
                  .newLine();

        }

        // Concrete body
        writer.append( " {" )
              .newLine()
              .indent();

        for ( IJavaStatement statement : staticInitialization.getStatements() ) {
            statement.consume( JavaCodeGenerator.INSTANCE, writer );
        }

        writer.unindent()
              .append( "}" )
              .newLine()
              .newLine();

    }

    public static final JavaStaticInitializationCodeGenerator INSTANCE = new JavaStaticInitializationCodeGenerator();

}
