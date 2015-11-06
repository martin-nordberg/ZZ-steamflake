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

}
