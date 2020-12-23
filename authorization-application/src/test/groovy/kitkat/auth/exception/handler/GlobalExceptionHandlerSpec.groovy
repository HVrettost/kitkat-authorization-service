package kitkat.auth.exception.handler

import kitkat.auth.exception.AuthenticationException
import kitkat.auth.exception.AuthorizationException
import kitkat.auth.exception.error.AuthenticationError
import kitkat.auth.exception.error.AuthorizationError
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.InternalAuthenticationServiceException
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
            authError << EnumSet.allOf(AuthorizationError)
    }

    def "Should handle BadCredentialsException"() {
        when:
            def response = globalExceptionHandler.handleBadCredentialsException(new BadCredentialsException('exception'))

        then:
            with (response) {
                body.errorCode == AuthenticationError.BAD_CREDENTIALS.errorCode
                body.message == AuthenticationError.BAD_CREDENTIALS.message
                statusCode == AuthenticationError.BAD_CREDENTIALS.httpStatus
            }
    }

    @Unroll
    def "Should handle AuthenticationException"() {
        when:
            def response = globalExceptionHandler.handleAuthenticationException(new AuthenticationException(AuthenticationError.BAD_CREDENTIALS))

        then:
            with (response) {
                body.errorCode == AuthenticationError.BAD_CREDENTIALS.errorCode
                body.message == AuthenticationError.BAD_CREDENTIALS.message
                statusCode == AuthenticationError.BAD_CREDENTIALS.httpStatus
            }
    }

    def "Should handle InternalAuthenticationServiceException with AuthenticationException throwable"() {
        when:
            def response = globalExceptionHandler.handleInternalAuthenticationServiceException(new InternalAuthenticationServiceException('message',
                            new AuthenticationException(AuthenticationError.BAD_CREDENTIALS)))

        then:
            with (response) {
                body.errorCode == AuthenticationError.BAD_CREDENTIALS.errorCode
                body.message == AuthenticationError.BAD_CREDENTIALS.message
                statusCode == AuthenticationError.BAD_CREDENTIALS.httpStatus
            }
    }

    def "Should handle InternalAuthenticationServiceException with not AuthenticationException throwable"() {
        when:
            def response = globalExceptionHandler
                    .handleInternalAuthenticationServiceException(new InternalAuthenticationServiceException('message', new Throwable('throwable message')))

        then:
            with (response) {
                body.errorCode == AuthenticationError.BAD_CREDENTIALS.errorCode
                body.message == AuthenticationError.BAD_CREDENTIALS.message
                statusCode == AuthenticationError.BAD_CREDENTIALS.httpStatus
            }
    }

    def "Should handle Generic exceptions"() {
        when:
            def response = globalExceptionHandler.handleGenericException(new Exception('exception'))

        then:
            with (response) {
                body.errorCode == AuthorizationError.GENERIC_ERROR.errorCode
                body.message == AuthorizationError.GENERIC_ERROR.message
                statusCode == AuthorizationError.GENERIC_ERROR.httpStatus
            }
    }
}
