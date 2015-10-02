package org.steamflake.core.domain.javacodegen.impl.services;

import org.steamflake.core.domain.javamodel.api.services.IJavaModelConsumerService;
import org.steamflake.core.domain.javamodel.api.statements.IJavaAssignmentStatement;
import org.steamflake.core.persistence.ioutilities.codegen.CodeWriter;

/**
 * Code generator for a Java field.
 */
public final class JavaAssignmentStatementCodeGenerator
    extends JavaMemberCodeGenerator
    implements IJavaModelConsumerService<IJavaAssignmentStatement, CodeWriter> {

    private JavaAssignmentStatementCodeGenerator() {
    }

    @SuppressWarnings( "ParameterNameDiffersFromOverriddenParameter" )
    @Override
    public void consume(
        IJavaAssignmentStatement statement, CodeWriter writer
    ) {

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
