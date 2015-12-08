package org.steamflake.core.domain.javacodegen.api.services

import org.steamflake.core.domain.javamodel.api.elements.EJavaAccessibility
import org.steamflake.core.domain.javamodel.api.elements.IJavaRootPackage
import org.steamflake.core.domain.javamodel.impl.elements.JavaRootPackage
import org.steamflake.core.persistence.codeio.codegen.api.CodeWriter
import org.steamflake.core.persistence.codeio.codegen.api.CodeWriterConfig
import spock.lang.Specification

/**
 * Specification for class JavaCodeGenerator.
 */
class JavaCodeGeneratorSpec
    extends Specification {

    def nl = System.getProperty( "line.separator" );

    def "A code generator generates the code for a class."() {

        given:
        def writer = new StringWriter();
        def config = new CodeWriterConfig( 2, 80 );
        def codeWriter = new CodeWriter( writer, config );

        IJavaRootPackage root = new JavaRootPackage();

        def pkg = root.addPackage( "pkg1" ).addPackage( "pkg2" );
        def cls = pkg.addClass( "MyClass", Optional.empty(), false, true, Optional.empty() );

        cls.addField( "field1", root.builtinInt );

        cls.addConstructor( "Default constructor.", EJavaAccessibility.PROTECTED );

        def con2 = cls.addConstructor( "Constructs a new instance.", EJavaAccessibility.PUBLIC );
        con2.addParameter( "p1", "first parameter", root.builtinDouble );
        con2.addParameter( "p2", "second parameter", root.builtinFloat );
        con2.codeBlock.addAssignmentStatement( "this.field1", "p1" );

        cls.addMethod( "method1", "A method to do stuff.", root.builtinBoolean );

        def meth2 = cls.addMethod( "method2", "A method to do more stuff.", root.builtinInt );
        meth2.addParameter( "arg1", "first argument", root.builtinLong );

        cls.consume( JavaCodeGenerator.INSTANCE, codeWriter );

        def code = writer.toString();

        expect:
        code == nl +
            "package pkg1.pkg2;" + nl +
            nl +
            "public final class MyClass {" + nl +
            nl +
            "  /** Default constructor. */" + nl +
            "  protected MyClass() {" + nl +
            "  }" + nl +
            nl +
            "  /**" + nl +
            "   * Constructs a new instance." + nl +
            "   * @param p1 first parameter" + nl +
            "   * @param p2 second parameter" + nl +
            "   */" + nl +
            "  public MyClass( double p1, float p2 ) {" + nl +
            "    this.field1 = p1;" + nl +
            "  }" + nl +
            nl +
            "  /** A method to do stuff. */" + nl +
            "  public boolean method1() {" + nl +
            "  }" + nl +
            nl +
            "  /**" + nl +
            "   * A method to do more stuff." + nl +
            "   * @param arg1 first argument" + nl +
            "   */" + nl +
            "  public int method2( long arg1 ) {" + nl +
            "  }" + nl +
            nl +
            "  private final int field1;" + nl +
            nl +
            "}" + nl +
            nl;

    }


}
