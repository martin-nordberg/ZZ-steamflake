package org.steamflake.core.persistence.ioutilities.fileio

import org.steamflake.core.infrastructure.utilities.text.Strings
import spock.lang.Specification

/**
 * Specification for class FileScanner.
 */
class FileScannerSpec
    extends Specification {

    def "A file scanner reads tokens and whitespace."() {

        given:
        def text = "t1 t2\n\n\t t3;";
        def scanner = new FileScanner( text, "example.txt" );

        def tk1 = scanner.scan( "t1" );
        def ws1 = scanner.scanWhitespace();
        def tk2 = scanner.scan( "t2" );
        def ws2 = scanner.scanWhitespace();
        def tk3 = scanner.scan( "t3" );
        def semicolon = scanner.scan( ";" );
        def eoi = scanner.scanEndOfInput();

        expect:
        tk1.text == "t1";
        tk1.origin.line == 1;
        tk1.origin.column == 1;
        tk1.origin.fileName == "example.txt";

        ws1.text == " ";
        ws1.origin.line == 1;
        ws1.origin.column == 3;

        tk2.text == "t2";
        tk2.origin.line == 1;
        tk2.origin.column == 4;

        ws2.text == "\n\n\t ";
        ws2.origin.line == 1;
        ws2.origin.column == 6;

        tk3.text == "t3";
        tk3.origin.line == 3;
        tk3.origin.column == 3;

        semicolon.text == ";";
        semicolon.origin.line == 3;
        semicolon.origin.column == 5;

        eoi.text == "";
        eoi.origin.line == 3;
        eoi.origin.column == 6;

    }

    def "A file scanner scans using delimiters."() {

        given:
        def text = "a b c\nd e f\n{{xyz}}\ng h i j";
        def scanner = new FileScanner( text, "example.txt" );

        def tk1 = scanner.scanUntil( "{{" );
        def tk2 = scanner.scanUntil( "}}" );
        def tk3 = scanner.scanUntilEndOfInput();

        expect:
        tk1.text == "a b c\nd e f\n";
        tk1.origin.line == 1;
        tk1.origin.column == 1;
        tk1.origin.fileName == "example.txt";

        tk2.text == "xyz";
        tk2.origin.line == 3;
        tk2.origin.column == 3;

        tk3.text == "\ng h i j";
        tk3.origin.line == 3;
        tk3.origin.column == 8;

    }

    def "A file scanner cannot scan empty tokens."() {

        given:
        def text = "abc";
        def scanner = new FileScanner( text, "example.txt" );

        when:
        scanner.accept( "" )

        then:
        IllegalArgumentException e = thrown();
        !Strings.isNullOrEmpty( e.message );

    }

    def "A file scanner cannot scan multi-line tokens."() {

        given:
        def text = "abc";
        def scanner = new FileScanner( text, "example.txt" );

        when:
        scanner.accept( "a\nb" )

        then:
        IllegalArgumentException e = thrown();
        !Strings.isNullOrEmpty( e.message );

    }

    def "A file scanner cannot scan to an empty delimiter."() {

        given:
        def text = "abc";
        def scanner = new FileScanner( text, "example.txt" );

        when:
        scanner.acceptUntil( "" )

        then:
        IllegalArgumentException e = thrown();
        !Strings.isNullOrEmpty( e.message );

    }

}
