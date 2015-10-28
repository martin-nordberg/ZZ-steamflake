package org.steamflake.core.domain.base.model.impl.utilities

import spock.lang.Specification

/**
 * Specification for qualified names.
 */
class QualifiedNameSpec
    extends Specification {

    def "A qualified name with no parent has a simple path."() {

        given:
        def symbol = new QualifiedName( "myself", Optional.empty() );

        expect:
        symbol.name == "myself";
        symbol.path == "myself";

    }

    def "A qualified name computes its path."() {

        given:
        def symbol1 = new QualifiedName( "symbol1", Optional.empty() );
        def symbol2 = new QualifiedName( "symbol2", Optional.of( symbol1 ) );
        def symbol3 = new QualifiedName( "symbol3", Optional.of( symbol2 ) );
        def symbol4a = new QualifiedName( "symbol4a", Optional.of( symbol3 ) );
        def symbol4b = new QualifiedName( "symbol4b", Optional.of( symbol3 ) );

        expect:
        symbol2.path == "symbol1.symbol2";
        symbol3.path == "symbol1.symbol2.symbol3";
        symbol4a.path == "symbol1.symbol2.symbol3.symbol4a";
        symbol4b.path == "symbol1.symbol2.symbol3.symbol4b";

    }

}
