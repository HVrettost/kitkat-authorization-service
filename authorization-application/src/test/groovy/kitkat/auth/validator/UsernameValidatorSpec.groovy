package kitkat.auth.validator

import kitkat.auth.exception.AuthenticationException
import kitkat.auth.exception.error.AuthenticationError
import spock.lang.Specification
import spock.lang.Unroll

class UsernameValidatorSpec extends Specification {

    UsernameValidator usernameValidator

    def setup() {
        usernameValidator = new UsernameValidator()
    }

    @Unroll
    def "Should throw exception if username is null or empty"() {
        when:
            usernameValidator.validate(username)

        then:
            AuthenticationException ex = thrown()
            with(ex) {
                ex.errorDetails.errorCode == AuthenticationError.BAD_CREDENTIALS.errorCode
                ex.errorDetails.message == AuthenticationError.BAD_CREDENTIALS.message
            }

        where:
            username << ['', null]
    }

    @Unroll
    def "Should throw exception if username has length more than 10 characters"() {
        when:
            usernameValidator.validate(username)

        then:
            AuthenticationException ex = thrown()
            with(ex) {
                ex.errorDetails.errorCode == AuthenticationError.BAD_CREDENTIALS.errorCode
                ex.errorDetails.message == AuthenticationError.BAD_CREDENTIALS.message
            }
            0 * _

        where:
            username << ["usernameWithMoreCharactersThanAccepted", "tententente"]
    }

    @Unroll
    def "Should pass validations and not throw exception"() {
        when:
            usernameValidator.validate(username)

        then:
            noExceptionThrown()
            0 * _

        where:
            username << ["username", "tententent", "h"]
    }
}
