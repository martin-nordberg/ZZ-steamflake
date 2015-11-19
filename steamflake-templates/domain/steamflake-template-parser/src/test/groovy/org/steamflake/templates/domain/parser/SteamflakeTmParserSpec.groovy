package org.steamflake.templates.domain.parser

import org.steamflake.templates.domain.model.impl.elements.SteamflakeTmRootPackage
import org.steamflake.templates.domain.parser.api.SteamflakeTmParser
import spock.lang.Specification

/**
 * Specification for Steamflake template parsing.
 */
class SteamflakeTmParserSpec
    extends Specification {

    def "A template parser parses an empty template."() {

        given:
        def rootPackage = new SteamflakeTmRootPackage();
        def code = '''
            package p1.p2;

            public abstract template TSample {
            }
        ''';
        def template = SteamflakeTmParser.parse( rootPackage, code, "example.stft" );

        expect:
        template.abstractness.isAbstract();
        template.accessibility.isPublic();
        template.id.name == "TSample";
        template.origin.get().fileName == "example.stft";
        template.origin.get().line == 4;
        template.origin.get().column == 38;

    }

    def "A template parser parses template imports."() {

        given:
        def rootPackage = new SteamflakeTmRootPackage();
        def code = '''
            package p1.p2;

            import p1.p3.Type1;
            import p1.p3.p4.Type2 as TypeTwo;

            public abstract template TSample {
            }
        ''';
        def template = SteamflakeTmParser.parse( rootPackage, code, "example.stft" );

        expect:
        template.abstractness.isAbstract();
        template.accessibility.isPublic();
        template.id.name == "TSample";
        template.origin.get().fileName == "example.stft";
        template.origin.get().line == 7;
        template.origin.get().column == 38;
        template.imports.size() == 2;
        template.imports[0].origin.get().line == 4;
        template.imports[0].origin.get().column == 13;
        template.imports[0].typeName == "p1.p3.Type1";
        !template.imports[0].alias.isPresent();
        template.imports[1].origin.get().line == 5;
        template.imports[1].origin.get().column == 13;
        template.imports[1].typeName == "p1.p3.p4.Type2";
        template.imports[1].alias.get() == "TypeTwo";
    }

    def "A template parser parses a template with empty rules."() {

        given:
        def rootPackage = new SteamflakeTmRootPackage();
        def code = '''
            package p1.p2;

            public abstract template TSample {

                public rule rule1() {{{
                }}}

                protected abstract rule rule2() {{{
                }}}

            }
        ''';
        def template = SteamflakeTmParser.parse( rootPackage, code, "example.stft" );

        expect:
        template.abstractness.isAbstract();
        template.accessibility.isPublic();
        template.id.name == "TSample";
        template.origin.get().fileName == "example.stft";
        template.origin.get().line == 4;
        template.origin.get().column == 38;
        template.rules.size() == 2;

    }

    def "A template parser parses a template with specailly delimited rules."() {

        given:
        def rootPackage = new SteamflakeTmRootPackage();
        def code = '''
            package p1.p2;

            public abstract template TSample {
                rule rule1() {{[ ]}}
                rule rule2() {({ })}
                rule rule3() <{{ }}>
                rule rule4() [[` `]]
                rule rule5() {{" "}}
                rule rule6() <<' '>>
                rule rule7() ({{ }})
            }
        ''';
        def template = SteamflakeTmParser.parse( rootPackage, code, "example.stft" );

        expect:
        template.abstractness.isAbstract();
        template.accessibility.isPublic();
        template.id.name == "TSample";
        template.rules.size() == 7;

    }

    def "A template parser parses simple variable tokens."() {

        given:
        def rootPackage = new SteamflakeTmRootPackage();
        def code = '''
            package p1.p2;

            public abstract template TSample {
                rule rule1() {{{ {{x.y}} {{a.b}}{{p.q.r}} }}}
            }
        ''';
        def template = SteamflakeTmParser.parse( rootPackage, code, "example.stft" );

        expect:
        template.abstractness.isAbstract();
        template.accessibility.isPublic();
        template.id.name == "TSample";
        template.rules.size() == 1;
        template.rules[0].tokens.size() == 6;

    }

    def "A template parser ignores empty text between tokens."() {

        given:
        def rootPackage = new SteamflakeTmRootPackage();
        def code = '''
            package p1.p2;

            public abstract template TSample {
                rule rule1() {[{{[a]}{[b]}{[c]}}]}
            }
        ''';
        def template = SteamflakeTmParser.parse( rootPackage, code, "example.stft" );

        expect:
        template.abstractness.isAbstract();
        template.accessibility.isPublic();
        template.id.name == "TSample";
        template.rules.size() == 1;
        template.rules[0].tokens.size() == 3;

    }

}
