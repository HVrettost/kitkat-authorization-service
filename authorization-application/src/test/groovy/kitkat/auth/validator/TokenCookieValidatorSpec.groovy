package kitkat.auth.validator

import kitkat.auth.exception.AuthorizationException
import kitkat.auth.exception.error.AuthError
import spock.lang.Specification

import kitkat.auth.trait.CookieHelper
import spock.lang.Unroll

class TokenCookieValidatorSpec extends Specification implements CookieHelper{

    TokenCookieValidator tokenCookieValidator

    def setup() {
        tokenCookieValidator = new TokenCookieValidator()
    }

    @Unroll
    def "Should throw AuthorizationException if cookie is null or empty"() {
        when:
            tokenCookieValidator.validate(cookie)

        then:
            AuthorizationException authorizationException = thrown()
            with(authorizationException) {
                errorDetails.errorCode == AuthError.TOKEN_COULD_NOT_BE_EXTRACTED.errorCode
                errorDetails.message == AuthError.TOKEN_COULD_NOT_BE_EXTRACTED.message
            }

        where:
            cookie << [null, ""]
    }

    def "Should throw AuthorizationException if cookie is not consisted of two parts"() {
        given:
            def cookie = createNonTwoPartCookie()

        when:
            tokenCookieValidator.validate(cookie)

        then:
            AuthorizationException authorizationException = thrown()
            with(authorizationException) {
                errorDetails.errorCode == AuthError.TOKEN_COULD_NOT_BE_EXTRACTED.errorCode
                errorDetails.message == AuthError.TOKEN_COULD_NOT_BE_EXTRACTED.message
            }
    }

    def "Should throw AuthorizationException if cookie parts do not contain accessToken"() {
        given:
            def cookie = createCookieThatDoesNotContainAccessTokenPart()

        when:
            tokenCookieValidator.validate(cookie)

        then:
            AuthorizationException authorizationException = thrown()
            with(authorizationException) {
                errorDetails.errorCode == AuthError.TOKEN_COULD_NOT_BE_EXTRACTED.errorCode
                errorDetails.message == AuthError.TOKEN_COULD_NOT_BE_EXTRACTED.message
            }
    }

    def "Should throw AuthorizationException if cookie parts do not contain refreshToken"() {
        given:
            def cookie = createCookieThatDoesNotContainRefreshTokenPart()

        when:
            tokenCookieValidator.validate(cookie)

        then:
            AuthorizationException authorizationException = thrown()
            with(authorizationException) {
                errorDetails.errorCode == AuthError.TOKEN_COULD_NOT_BE_EXTRACTED.errorCode
                errorDetails.message == AuthError.TOKEN_COULD_NOT_BE_EXTRACTED.message
            }
    }

    def "Should not throw exception if cookie is valid"() {
        given:
            def cookie = createValidTokenCookie()

        when:
            tokenCookieValidator.validate(cookie)

        then:
            0 * _
    }
}
