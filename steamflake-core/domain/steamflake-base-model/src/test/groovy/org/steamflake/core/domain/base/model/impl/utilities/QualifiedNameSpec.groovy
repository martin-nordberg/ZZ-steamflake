package org.steamflake.core.domain.base.model.impl.utilities

import spock.lang.Specification

/**
 * Specification for qualified names.
 */
class QualifiedNameSpec
    extends Specification {

    def "A root qualified name has blank path."() {

        given:
        def root = new QualifiedName( "", Optional.empty() );

        expect:
        root.name == "";
        root.path == "";

    }

    def "A top level qualified name has a simple path."() {

        given:
        def root = new QualifiedName( "", Optional.empty() );
        def symbol = new QualifiedName( "myself", Optional.of( root ) );

        expect:
        symbol.name == "myself";
        symbol.path == "myself";

    }

    def "A qualified name computes its path."() {

        given:
        def root = new QualifiedName( "", Optional.empty() );
        def symbol1 = new QualifiedName( "symbol1", Optional.of( root ) );
        def symbol2 = new QualifiedName( "symbol2", Optional.of( symbol1 ) );
        def symbol3 = new QualifiedName( "symbol3", Optional.of( symbol2 ) );
        def symbol4a = new QualifiedName( "symbol4a", Optional.of( symbol3 ) );
        def symbol4b = new QualifiedName( "symbol4b", Optional.of( symbol3 ) );

        expect:
        symbol2.name == "symbol2";
        symbol2.path == "symbol1.symbol2";
        symbol3.name == "symbol3";
        symbol3.path == "symbol1.symbol2.symbol3";
        symbol4a.name == "symbol4a";
        symbol4a.path == "symbol1.symbol2.symbol3.symbol4a";
        symbol4b.name == "symbol4b";
        symbol4b.path == "symbol1.symbol2.symbol3.symbol4b";

    }

}
