package org.steamflake.core.infrastructure.utilities.collections

import spock.lang.Specification

/**
 * Specification for class MappedRecord.
 */
class MappedRecordSpec
    extends Specification {

    def "Mapped fields can be read"() {

        given:
        def record = new SampleMappedRecord( 3, true, "example", 10.7f )

        when:
        def result = record.get( key )

        then:
        result == expectedResult

        where:
        key | expectedResult
        "i" | 3
        "b" | true
        "s" | "example"
        "f" | 10.7f

    }

}
