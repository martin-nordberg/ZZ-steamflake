//
// (C) Copyright 2015 Martin E. Nordberg III
// Apache 2.0 License
//

package org.steamflake.core.domain.javamodel.impl.elements;

import org.steamflake.core.domain.javamodel.api.elements.IJavaBuiltinType;
import org.steamflake.core.domain.javamodel.api.elements.IJavaPackage;
import org.steamflake.core.domain.javamodel.api.elements.IJavaRootPackage;

import java.util.Optional;

/**
 * The root package for Java code.
 */
public final class JavaRootPackage
    extends JavaAbstractPackage
    implements IJavaRootPackage {

    /**
     * Constructs a new Java package.
     */
    public JavaRootPackage() {
        super( "", Optional.of( "(Top level Java package)" ) );

        JavaPackage lang = this.establishJavaLangPackage();

        this.builtinBoolean = new JavaBuiltinType( lang, "boolean" );
        this.builtinDouble = new JavaBuiltinType( lang, "double" );
        this.builtinFloat = new JavaBuiltinType( lang, "float" );
        this.builtinInt = new JavaBuiltinType( lang, "int" );
        this.builtinLong = new JavaBuiltinType( lang, "long" );
        this.builtinVoid = new JavaBuiltinType( lang, "void" );

    }

    @Override
    public IJavaBuiltinType getBuiltinBoolean() {
        return this.builtinBoolean;
    }

    @Override
    public IJavaBuiltinType getBuiltinDouble() {
        return this.builtinDouble;
    }

    @Override
    public IJavaBuiltinType getBuiltinFloat() {
        return this.builtinFloat;
    }

    @Override
    public IJavaBuiltinType getBuiltinInt() {
        return this.builtinInt;
    }

    @Override
    public IJavaBuiltinType getBuiltinLong() {
        return this.builtinLong;
    }

    @Override
    public IJavaBuiltinType getBuiltinVoid() {
        return this.builtinVoid;
    }

    @Override
    public JavaRootPackage getRootPackage() {
        return this;
    }

    /**
     * Creates the external classes of the java.lang package.
     */
    private JavaPackage establishJavaLangPackage() {

        IJavaPackage lang = this.addPackage( "java" )
                                .addPackage( "lang", Optional.of( "Standard java language package." ), true );

        lang.addExternalClass( "Boolean" );
        lang.addExternalClass( "Exception" );
        lang.addExternalClass( "Float" );
        lang.addExternalClass( "Integer" );
        lang.addExternalClass( "Long" );
        lang.addExternalClass( "Object" );
        lang.addExternalClass( "String" );
        lang.addExternalClass( "Thread" );
        // TODO: lots more if/when needed ...

        return (JavaPackage) lang;
    }

    private final JavaBuiltinType builtinBoolean;

    private final JavaBuiltinType builtinDouble;

    private final JavaBuiltinType builtinFloat;

    private final JavaBuiltinType builtinInt;

    private final JavaBuiltinType builtinLong;

    private final JavaBuiltinType builtinVoid;

}
