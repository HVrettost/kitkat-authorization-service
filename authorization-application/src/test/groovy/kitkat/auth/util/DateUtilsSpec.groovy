package kitkat.auth.util

import spock.lang.Specification

import java.time.Clock

class DateUtilsSpec extends Specification {

    Clock clock
    DateUtils dateUtils

    def setup() {
        clock = Mock()
        dateUtils = new DateUtils(clock)
    }

    def "Should get current date in UTC"() {
        when:
            def response = dateUtils.getCurrentUTCDate()

        then:
            1 * clock.millis() >> 1608061599445
            0 * _

        and:
            response.toInstant().toEpochMilli() == 1608061599445
    }

    def "Should get current date in UTC with offset"() {
        given:
            def offset = 1000L

        when:
            def response = dateUtils.getCurrentUTCDateWithOffset(offset)

        then:
            1 * clock.millis() >> 1608061599445
            0 * _

        and:
            response.toInstant().toEpochMilli() == 1608061599445 + offset
    }
}
