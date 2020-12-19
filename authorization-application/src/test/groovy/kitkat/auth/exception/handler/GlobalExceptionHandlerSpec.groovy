package kitkat.auth.exception.handler

import kitkat.auth.exception.AuthorizationException
import kitkat.auth.exception.error.AuthError
import org.springframework.security.authentication.BadCredentialsException
import spock.lang.Specification
import spock.lang.Unroll

class GlobalExceptionHandlerSpec extends Specification {

    GlobalExceptionHandler globalExceptionHandler

    def setup() {
        globalExceptionHandler = new GlobalExceptionHandler()
    }

    @Unroll
    def "Should handle AuthorizationException"() {
        when:
            def response = globalExceptionHandler.handleAuthorizationException(new AuthorizationException(authError))

        then:
            with (response) {
                body.errorCode == authError.errorCode
                body.message == authError.message
                statusCode == authError.httpStatus
            }

        where:
            authError << EnumSet.allOf(AuthError)
    }

    def "Should handle BadCredentialsException"() {
        when:
            def response = globalExceptionHandler.handleBadCredentialsException(new BadCredentialsException('exception'))

        then:
            with (response) {
                body.errorCode == AuthError.BAD_CREDENTIALS.errorCode
                body.message == AuthError.BAD_CREDENTIALS.message
                statusCode == AuthError.BAD_CREDENTIALS.httpStatus
            }
    }
}
