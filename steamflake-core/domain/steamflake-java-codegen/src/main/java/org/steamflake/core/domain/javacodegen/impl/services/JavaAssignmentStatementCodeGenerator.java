package org.steamflake.core.domain.javacodegen.impl.services;

import org.steamflake.core.domain.base.model.api.elements.ISteamflakeModelElement;
import org.steamflake.core.domain.base.model.spi.ISteamflakeModelConsumerService;
import org.steamflake.core.domain.javamodel.api.elements.IJavaPackage;
import org.steamflake.core.domain.javamodel.api.elements.IJavaRootPackage;
import org.steamflake.core.domain.javamodel.api.statements.IJavaAssignmentStatement;
import org.steamflake.core.persistence.codeio.codegen.api.CodeWriter;

/**
 * Code generator for a Java field.
 */
public final class JavaAssignmentStatementCodeGenerator
    extends JavaMemberCodeGenerator
    implements ISteamflakeModelConsumerService<IJavaRootPackage, IJavaPackage, CodeWriter> {

    private JavaAssignmentStatementCodeGenerator() {
    }

    @SuppressWarnings( "ParameterNameDiffersFromOverriddenParameter" )
    @Override
    public <E extends ISteamflakeModelElement<IJavaRootPackage, IJavaPackage>> void consume(
        E element, CodeWriter writer
    ) {

        IJavaAssignmentStatement statement = (IJavaAssignmentStatement) element;

        // Description
        if ( statement.getDescription().isPresent() ) {
            writer.append( "// " )
                  .appendProse( statement.getDescription().get(), "// " )
                  .newLine();
        }

        // Left hand side
        writer.append( statement.getLeftHandSide() )
              .append( " " );

        // Extra operator
        if ( statement.getExtraOperator().isPresent() ) {
            writer.append( statement.getExtraOperator().get() );
        }

        // Equals
        writer.append( "=" )
              .spaceOrWrapIndent();

        // Left hand side
        writer.append( statement.getRightHandSide() )
              .append( ";" )
              .emptyOrWrapUnindent();

        // Ending punctuation
        writer.newLine();

    }

    public static final JavaAssignmentStatementCodeGenerator INSTANCE = new JavaAssignmentStatementCodeGenerator();

}
