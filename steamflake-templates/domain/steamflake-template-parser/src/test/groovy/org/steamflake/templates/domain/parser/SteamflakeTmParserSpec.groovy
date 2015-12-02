package org.steamflake.templates.domain.parser

import org.steamflake.templates.domain.model.api.directives.comments.ISteamflakeTmCommentDirective
import org.steamflake.templates.domain.model.api.directives.logic.ISteamflakeTmIfDirective
import org.steamflake.templates.domain.model.api.directives.text.ISteamflakeTmTextDirective
import org.steamflake.templates.domain.model.api.directives.whitespace.ISteamflakeTmNewLineDirective
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

                public rule rule1(p:Stuff) {{{
                }}}

                protected abstract rule rule2(p:Stuff) {{{
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
        template.rules[0].id.name == "rule1";
        template.rules[0].id.path == "p1.p2.TSample.rule1";
        template.rules[1].id.name == "rule2";
        template.rules[1].id.path == "p1.p2.TSample.rule2";

    }

    def "A template parser parses a rule with multiple parameters."() {

        given:
        def rootPackage = new SteamflakeTmRootPackage();
        def code = '''
            package p1.p2;

            public abstract template TSample {

                public rule rule1( a : Stuff , b:p1 . p2.Widget, c : p1.MoreStuff ) {{{
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
        template.rules.size() == 1;
        template.rules[0].parameters.size() == 3;
        template.rules[0].parameters[0].id.name == "a";
        template.rules[0].parameters[0].typeName == "Stuff";
        template.rules[0].parameters[1].id.name == "b";
        template.rules[0].parameters[1].typeName == "p1.p2.Widget";
        template.rules[0].parameters[2].id.name == "c";
        template.rules[0].parameters[2].typeName == "p1.MoreStuff";

    }

    def "A template parser parses a template with specially delimited rules."() {

        given:
        def rootPackage = new SteamflakeTmRootPackage();
        def code = '''
            package p1.p2;

            public abstract template TSample {
                rule rule1(p:Stuff) {{[ ]}}
                rule rule2(p:Stuff) {({ })}
                rule rule3(p:Stuff) <{{ }}>
                rule rule4(p:Stuff) [[` `]]
                rule rule5(p:Stuff) {{" "}}
                rule rule6(p:Stuff) <<' '>>
                rule rule7(p:Stuff) ({{ }})
            }
        ''';
        def template = SteamflakeTmParser.parse( rootPackage, code, "example.stft" );

        expect:
        template.abstractness.isAbstract();
        template.accessibility.isPublic();
        template.id.name == "TSample";
        template.rules.size() == 7;

    }

    def "A template parser parses simple variable directives."() {

        given:
        def rootPackage = new SteamflakeTmRootPackage();
        def code = '''
            package p1.p2;

            public abstract template TSample {
                rule rule1(p:Stuff) {{{ {{x.y}} {{a.b}}{{p.q.r}} }}}
            }
        ''';
        def template = SteamflakeTmParser.parse( rootPackage, code, "example.stft" );

        expect:
        template.abstractness.isAbstract();
        template.accessibility.isPublic();
        template.id.name == "TSample";
        template.rules.size() == 1;
        template.rules[0].directives.size() == 6;

    }

    def "A template parser ignores empty text between directives."() {

        given:
        def rootPackage = new SteamflakeTmRootPackage();
        def code = '''
            package p1.p2;

            public abstract template TSample {
                rule rule1(p:Stuff) {[{{[a]}{[b]}{[c]}}]}
            }
        ''';
        def template = SteamflakeTmParser.parse( rootPackage, code, "example.stft" );

        expect:
        template.abstractness.isAbstract();
        template.accessibility.isPublic();
        template.id.name == "TSample";
        template.rules.size() == 1;
        template.rules[0].directives.size() == 3;

    }

    def "A template parser parses comment directives."() {

        given:
        def rootPackage = new SteamflakeTmRootPackage();
        def code = '''
            package p1.p2;

            public template TSample {
                rule rule1(p:Stuff) {{{ Hello {{! Here be comments. }}world. }}}
            }
        ''';
        def template = SteamflakeTmParser.parse( rootPackage, code, "example.stft" );

        expect:
        template.rules.size() == 1;
        template.rules[0].directives.size() == 3;
        ( (ISteamflakeTmTextDirective) template.rules[0].directives[0] ).text == " Hello ";
        ( (ISteamflakeTmCommentDirective) template.rules[0].directives[1] ).text == "Here be comments.";
        ( (ISteamflakeTmTextDirective) template.rules[0].directives[2] ).text == "world. ";

    }

    def "A template parser parses a simple if directive."() {

        given:
        def rootPackage = new SteamflakeTmRootPackage();
        def code = '''
            package p1.p2;

            public template TSample {
                rule rule1(p:Stuff) {{{ abc {{#if p.condition }} def {{/if}} ghi }}}
            }
        ''';
        def template = SteamflakeTmParser.parse( rootPackage, code, "example.stft" );

        expect:
        template.rules.size() == 1;
        template.rules[0].directives.size() == 3;
        ( (ISteamflakeTmTextDirective) template.rules[0].directives[0] ).text == " abc ";
        ( (ISteamflakeTmIfDirective) template.rules[0].directives[1] ).boolConditionPath == "p.condition";
        ( (ISteamflakeTmIfDirective) template.rules[0].directives[1] ).directives.size() == 1;
        ( (ISteamflakeTmTextDirective) ( (ISteamflakeTmIfDirective) template.rules[0].directives[1] ).directives
            [0] ).text == " def ";
        ( (ISteamflakeTmTextDirective) template.rules[0].directives[2] ).text == " ghi ";

    }

    def "A template parser parses a nested if directive."() {

        given:
        def rootPackage = new SteamflakeTmRootPackage();
        def code = '''
            package p1.p2;

            public template TSample {
                rule rule1(p:Stuff) {{{ abc {{#if p.condition }} def {{#if p.othercond}} ghi {{/if}}{{/if}} jkl }}}
            }
        ''';
        def template = SteamflakeTmParser.parse( rootPackage, code, "example.stft" );

        expect:
        template.rules.size() == 1;
        template.rules[0].directives.size() == 3;
        ( (ISteamflakeTmTextDirective) template.rules[0].directives[0] ).text == " abc ";

        def outerIf = (ISteamflakeTmIfDirective) template.rules[0].directives[1];
        outerIf.boolConditionPath == "p.condition";
        outerIf.directives.size() == 2;
        ( (ISteamflakeTmTextDirective) outerIf.directives[0] ).text == " def ";

        def innerIf = (ISteamflakeTmIfDirective) outerIf.directives[1];
        innerIf.boolConditionPath == "p.othercond";
        innerIf.directives.size() == 1;
        ( (ISteamflakeTmTextDirective) innerIf.directives[0] ).text == " ghi ";

        ( (ISteamflakeTmTextDirective) template.rules[0].directives[2] ).text == " jkl ";

    }

    def "A template parser parses new-line directives."() {

        given:
        def rootPackage = new SteamflakeTmRootPackage();
        def code = '''
            package p1.p2;

            public template TSample {
                rule rule1(p:Stuff) {{{ {{%nl}}{{%nl p.condition}}{{%nl_ q.condition}} }}}
            }
        ''';
        def template = SteamflakeTmParser.parse( rootPackage, code, "example.stft" );

        expect:
        template.rules.size() == 1;
        template.rules[0].directives.size() == 5;

        ( (ISteamflakeTmTextDirective) template.rules[0].directives[0] ).text == " ";

        def nl1 = (ISteamflakeTmNewLineDirective) template.rules[0].directives[1];
        !nl1.isSpaceNeededIfNoNewLine();
        !nl1.boolConditionPath.isPresent();

        def nl2 = (ISteamflakeTmNewLineDirective) template.rules[0].directives[2];
        !nl2.isSpaceNeededIfNoNewLine();
        nl2.boolConditionPath.get() == "p.condition";

        def nl3 = (ISteamflakeTmNewLineDirective) template.rules[0].directives[3];
        nl3.isSpaceNeededIfNoNewLine();
        nl3.boolConditionPath.get() == "q.condition";

        ( (ISteamflakeTmTextDirective) template.rules[0].directives[4] ).text == " ";

    }

}
