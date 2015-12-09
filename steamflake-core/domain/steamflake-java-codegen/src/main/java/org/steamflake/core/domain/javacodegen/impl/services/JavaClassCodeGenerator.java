package org.steamflake.core.domain.javacodegen.impl.services;

import org.steamflake.core.domain.base.model.api.elements.ISteamflakeModelElement;
import org.steamflake.core.domain.base.model.spi.ISteamflakeModelConsumerService;
import org.steamflake.core.domain.javacodegen.api.services.JavaCodeGenerator;
import org.steamflake.core.domain.javamodel.api.elements.IJavaClass;
import org.steamflake.core.domain.javamodel.api.elements.IJavaConstructor;
import org.steamflake.core.domain.javamodel.api.elements.IJavaField;
import org.steamflake.core.domain.javamodel.api.elements.IJavaImplementedInterface;
import org.steamflake.core.domain.javamodel.api.elements.IJavaMethod;
import org.steamflake.core.domain.javamodel.api.elements.IJavaPackage;
import org.steamflake.core.domain.javamodel.api.elements.IJavaRootPackage;
import org.steamflake.core.domain.javamodel.api.elements.IJavaStaticInitialization;
import org.steamflake.core.domain.javamodel.api.elements.IJavaType;
import org.steamflake.core.persistence.codeio.codegen.api.CodeWriter;

/**
 * Code generator for a Java class.
 */
public final class JavaClassCodeGenerator
    extends JavaAnnotatableModelElementCodeGenerator
    implements ISteamflakeModelConsumerService<IJavaRootPackage, IJavaPackage, CodeWriter> {

    private JavaClassCodeGenerator() {
    }

    @SuppressWarnings( "ParameterNameDiffersFromOverriddenParameter" )
    @Override
    public <E extends ISteamflakeModelElement<IJavaRootPackage, IJavaPackage>> void consume(
        E element, CodeWriter writer
    ) {

        IJavaClass klass = (IJavaClass) element;

        // Package declaration
        writer.newLine()
              .append( "package " )
              .append( klass.getParent().getQualifiedName().getPath() )
              .append( ";" )
              .newLine()
              .newLine();

        // Imports
        for ( IJavaType imp : klass.getImports() ) {
            writer.append( "import " )
                  .append( imp.getQualifiedName().getPath() )
                  .append( ";" )
                  .newLine();
        }
        writer.newLineIf( !klass.getImports().isEmpty() );

        // JavaDoc
        if ( klass.getDescription().isPresent() ) {

            writer.append( "/**" )
                  .spaceOrWrap( " * " )
                  .appendProse( klass.getDescription().get(), " * " );

            writer.spaceOrWrap( " " )
                  .append( "*/" )
                  .newLine();

        }

        // Annotations
        this.writeAnnotations( klass, writer, true );

        // Class name
        writer.append( "public " )
              .appendIf( klass.getAbstractness().isConcrete(), "final " )
              .appendIf( klass.getAbstractness().isAbstract(), "abstract " )
              .append( "class " )
              .append( klass.getName() );
        // TODO: type args

        // Extends
        if ( klass.getBaseClass().isPresent() ) {
            writer.newLine()
                  .indent()
                  .append( "extends " )
                  .append( klass.getBaseClass().get().getName() )
                  .unindent();
        }

        // Implements
        if ( !klass.getImplementedInterfaces().isEmpty() ) {
            writer.newLine()
                  .indent()
                  .append( "implements " );
            for ( IJavaImplementedInterface implInterface : klass.getImplementedInterfaces() ) {
                writer.append( implInterface.getImplementedInterface().getName() )
                      .mark()
                      .append( ", " );
            }
            writer.revertToMark()
                  .unindent();
        }

        // Opening brace
        writer.append( " {" )
              .newLine()
              .newLine()
              .indent();

        // Constructors
        for ( IJavaConstructor constructor : klass.getConstructors() ) {
            constructor.consume( JavaCodeGenerator.INSTANCE, writer );
        }

        // Methods
        for ( IJavaMethod method : klass.getMethods() ) {
            method.consume( JavaCodeGenerator.INSTANCE, writer );
        }

        // Fields
        for ( IJavaField field : klass.getFields() ) {
            field.consume( JavaCodeGenerator.INSTANCE, writer );
        }

        // Static initializers
        for ( IJavaStaticInitialization staticInitialization : klass.getStaticInitializations() ) {
            staticInitialization.consume( JavaCodeGenerator.INSTANCE, writer );
        }

        // Closing brace
        writer.unindent()
              .append( "}" )
              .newLine()
              .newLine();

    }

    public static final JavaClassCodeGenerator INSTANCE = new JavaClassCodeGenerator();

}
