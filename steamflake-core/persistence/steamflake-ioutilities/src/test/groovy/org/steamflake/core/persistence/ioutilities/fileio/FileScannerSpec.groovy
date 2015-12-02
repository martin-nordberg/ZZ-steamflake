package org.steamflake.core.persistence.ioutilities.fileio

import org.steamflake.core.infrastructure.utilities.text.Strings
import spock.lang.Specification

/**
 * Specification for class FileScanner.
 */
class FileScannerSpec
    extends Specification {

    def "A file scanner reads text and whitespace."() {

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

    def "A file scanner reads trailing whitespace."() {

        given:
        def text = "t1 \n\n\f\t \n ";
        def scanner = new FileScanner( text, "example.txt" );

        def tk1 = scanner.scan( "t1" );
        def ws1 = scanner.scanWhitespace();
        def eoi = scanner.scanEndOfInput();

        expect:
        tk1.text == "t1";
        tk1.origin.line == 1;
        tk1.origin.column == 1;
        tk1.origin.fileName == "example.txt";

        ws1.text == " \n\n\f\t \n ";
        ws1.origin.line == 1;
        ws1.origin.column == 3;

        eoi.text == "";
        eoi.origin.line == 4;
        eoi.origin.column == 2;

    }

    def "A file scanner reads identifiers."() {

        given:
        def text = "t1.t2\r\n  t3";
        def scanner = new FileScanner( text, "example.txt" );

        def tk1 = scanner.scanIdentifier();
        def dt1 = scanner.scan( "." );
        def tk2 = scanner.scanIdentifier();
        def ws2 = scanner.scanWhitespace();
        def tk3 = scanner.scanIdentifier();
        def eoi = scanner.scanEndOfInput();

        expect:
        tk1.text == "t1";
        tk1.origin.line == 1;
        tk1.origin.column == 1;
        tk1.origin.fileName == "example.txt";

        dt1.text == ".";
        dt1.origin.line == 1;
        dt1.origin.column == 3;

        tk2.text == "t2";
        tk2.origin.line == 1;
        tk2.origin.column == 4;

        ws2.text == "\r\n  ";
        ws2.origin.line == 1;
        ws2.origin.column == 6;

        tk3.text == "t3";
        tk3.origin.line == 2;
        tk3.origin.column == 3;

        eoi.text == "";
        eoi.origin.line == 2;
        eoi.origin.column == 5;

    }

    def "A file scanner scans using delimiters."() {

        given:
        def text = "a b c\nd e f\n{{xyz}}\ng h i j";
        def scanner = new FileScanner( text, "example.txt" );

        def tk1 = scanner.scanUntil( "{{" );
        def tk2 = scanner.scan( "{{" );
        def tk3 = scanner.scanUntil( "}}" );
        def tk4 = scanner.scan( "}}" );
        def tk5 = scanner.scanUntilEndOfInput();

        expect:
        tk1.text == "a b c\nd e f\n";
        tk1.origin.line == 1;
        tk1.origin.column == 1;
        tk1.origin.fileName == "example.txt";

        tk2.text == "{{";
        tk2.origin.line == 3;
        tk2.origin.column == 1;

        tk3.text == "xyz";
        tk3.origin.line == 3;
        tk3.origin.column == 3;

        tk4.text == "}}";
        tk4.origin.line == 3;
        tk4.origin.column == 6;

        tk5.text == "\ng h i j";
        tk5.origin.line == 3;
        tk5.origin.column == 8;

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

    def "A file scanner scans end of input as often as needed."() {

        given:
        def text = "x";
        def scanner = new FileScanner( text, "example.txt" );

        when:
        scanner.scan( "x" );
        def tok1 = scanner.acceptEndOfInput()
        def tok2 = scanner.acceptEndOfInput()
        scanner.scanEndOfInput()
        scanner.scanEndOfInput()

        then:
        tok1.isPresent();
        tok2.isPresent();

    }

}
