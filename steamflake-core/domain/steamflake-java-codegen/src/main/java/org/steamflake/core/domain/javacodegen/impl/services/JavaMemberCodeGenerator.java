package org.steamflake.core.domain.javacodegen.impl.services;

import org.steamflake.core.domain.javamodel.api.elements.IJavaMember;
import org.steamflake.core.persistence.codeio.codegen.api.CodeWriter;

/**
 * Code generator for a Java field.
 */
@SuppressWarnings( "AbstractClassWithoutAbstractMethods" )
abstract class JavaMemberCodeGenerator
    extends JavaAnnotatableModelElementCodeGenerator {

    protected JavaMemberCodeGenerator() {
    }

    /**
     * Writes out the accessibility and static/final qualifiers for a member.
     *
     * @param member the member to be written.
     * @param writer the output writer.
     */
    protected void writeQualifiers(
        IJavaMember member, CodeWriter writer
    ) {

        switch ( member.getAccessibility() ) {
            case PUBLIC:
                writer.append( "public" );
                break;
            case PROTECTED:
                writer.append( "protected" );
                break;
            case PRIVATE:
                writer.append( "private" );
                break;
            case MODULE:
            case LOCAL:
                writer.append( "/*default*/" );
                break;
        }

        writer.appendIf( member.isStatic(), " static" )
              .appendIf( member.isFinal(), " final" )
              .spaceOrWrap();

    }

}
