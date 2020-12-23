package kitkat.auth.filter

import com.auth0.jwt.exceptions.JWTDecodeException
import com.auth0.jwt.exceptions.JWTVerificationException
import com.fasterxml.jackson.databind.ObjectMapper
import kitkat.auth.enumeration.ErrorDetails
import kitkat.auth.exception.AuthorizationException
import kitkat.auth.exception.error.AuthorizationError
import kitkat.auth.jwt.util.JwtClaimUtils
import kitkat.auth.jwt.validator.JwtValidator
import kitkat.auth.util.HeaderUtils
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import spock.lang.Specification
import spock.lang.Unroll

import javax.servlet.FilterChain
import javax.servlet.ServletOutputStream
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthorizationFilterSpec extends Specification {

    static final String ACCESS_TOKEN_ENDPOINT = "/api/auth/token"
    static final String REFRESH_TOKEN_ENDPOINT = "/api/auth/token/refresh"

    JwtValidator jwtValidator
    JwtClaimUtils jwtClaimUtils
    ObjectMapper objectMapper
    HeaderUtils headerUtils
    AuthorizationFilter authorizationFilter
    HttpServletRequest servletRequest
    HttpServletResponse servletResponse
    FilterChain filterChain

    def setup() {
        servletRequest = Mock()
        servletResponse = Mock()
        jwtValidator = Mock()
        jwtClaimUtils = Mock()
        objectMapper = Mock()
        headerUtils = Mock()
        filterChain = Mock()
        authorizationFilter = new AuthorizationFilter(jwtValidator, jwtClaimUtils, objectMapper, headerUtils)
    }

    def "Should delegate call to next filter in chain if is request to authenticate or is option request"() {
        when:
            authorizationFilter.doFilter(servletRequest, servletResponse, filterChain)

        then:
            1 * servletRequest.method >> HttpMethod.POST
            2 * servletRequest.requestURI >> ACCESS_TOKEN_ENDPOINT

        and:
            1 * filterChain.doFilter(servletRequest, servletResponse)
            0 * _
    }

    @Unroll
    def "Should delegate call to next filter in chain if is OPTION request"() {
        when:
            authorizationFilter.doFilter(servletRequest, servletResponse, filterChain)

        then:
            2 * servletRequest.method >> HttpMethod.OPTIONS
            1 * servletRequest.requestURI >> ACCESS_TOKEN_ENDPOINT

        and:
            1 * filterChain.doFilter(servletRequest, servletResponse)
            0 * _
    }

    @Unroll
    def "Should delegate call to next filter if request URI does not start with '/api'"() {
        when:
            authorizationFilter.doFilter(servletRequest, servletResponse, filterChain)

        then:
            1 * servletRequest.requestURI >> '/error'

        and:
            1 * filterChain.doFilter(servletRequest, servletResponse)
            0 * _

        where:
            httpMethod << EnumSet.allOf(HttpMethod)
    }

    def "Should make validations for refresh token and delegate call to next filter if request URI is to update access token"() {
        given:
            def refreshToken = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c'

        when:
            authorizationFilter.doFilter(servletRequest, servletResponse, filterChain)

        then:
            3 * servletRequest.method >> HttpMethod.PUT
            2 * servletRequest.requestURI >> REFRESH_TOKEN_ENDPOINT

        and: 'should extract refresh token'
            1 * headerUtils.extractRefreshToken(servletRequest) >> refreshToken

        and: 'validate refresh token successfully'
            1 * jwtValidator.validate(refreshToken)
            1 * filterChain.doFilter(servletRequest, servletResponse)
            0 * _
    }

    @Unroll
    def "Should validate access token for every api call that is not for authenticate or refreshing the acces token"() {
        given:
            def accessToken = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c'

        when:
            authorizationFilter.doFilter(servletRequest, servletResponse, filterChain)

        then:
            3 * servletRequest.method >> httpMethod
            requestURItimesCalled * servletRequest.requestURI >> "/api/all/other/endpoints"

        and: 'should extract access token'
            1 * headerUtils.extractAccessToken(servletRequest) >> accessToken

        and: 'validate access token successfully'
            1 * jwtValidator.validate(accessToken)
            1 * jwtClaimUtils.extractPermissionsClaim(accessToken) >> 'DELETE_TOKEN DELETE_ALL_TOKENS'
            1 * filterChain.doFilter(servletRequest, servletResponse)
            0 * _

        where:
            httpMethod        | requestURItimesCalled
            HttpMethod.DELETE | 1
            HttpMethod.GET    | 1
            HttpMethod.POST   | 2
            HttpMethod.PUT    | 2
    }

    @Unroll
    def "Should set response with error details if AuthorizationException is thrown because permissions do not exist"() {
        given:
            def errorDetailsAsByteArray = "stringArray".bytes
            ServletOutputStream servletOutputStream = Mock()
            def accessToken = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c'

        when:
            authorizationFilter.doFilter(servletRequest, servletResponse, filterChain)

        then:
            3 * servletRequest.method >> HttpMethod.POST
            2 * servletRequest.requestURI >> "/api/all/other/endpoints"

        and: 'should extract access token'
            1 * headerUtils.extractAccessToken(servletRequest) >> accessToken

        and: 'validate access token successfully'
            1 * jwtValidator.validate(accessToken)
            1 * jwtClaimUtils.extractPermissionsClaim(accessToken) >> permissions

        and: 'Authorization exception is handled and http response is set'
            1 * servletResponse.setStatus(HttpStatus.FORBIDDEN.value())
            1 * objectMapper.writeValueAsBytes(_ as ErrorDetails) >> errorDetailsAsByteArray
            1 * servletResponse.getOutputStream() >> servletOutputStream
            1 * servletOutputStream.write(errorDetailsAsByteArray)
            0 * _

        where:
            permissions << [null, '']
    }

    def "Should set response with error details if AuthorizationException is thrown because extract of token is unsuccessful"() {
        given:
            def errorDetailsAsByteArray = "stringArray".bytes
            ServletOutputStream servletOutputStream = Mock()

        when:
            authorizationFilter.doFilter(servletRequest, servletResponse, filterChain)

        then:
            3 * servletRequest.method >> HttpMethod.POST
            2 * servletRequest.requestURI >> "/api/all/other/endpoints"

        and: 'should extract access token'
            1 * headerUtils.extractAccessToken(servletRequest) >>
                    { throw new AuthorizationException(AuthorizationError.INVALID_TOKEN) }

        and: 'handle authorization exception'
            1 * servletResponse.setStatus(HttpStatus.FORBIDDEN.value())
            1 * objectMapper.writeValueAsBytes(_ as ErrorDetails) >> errorDetailsAsByteArray
            1 * servletResponse.getOutputStream() >> servletOutputStream
            1 * servletOutputStream.write(errorDetailsAsByteArray)
            0 * _
    }

    @Unroll
    def "Should set response with error details if JWTDecodeException is thrown"() {
        given:
            def errorDetailsAsByteArray = "stringArray".bytes
            ServletOutputStream servletOutputStream = Mock()

        when:
            authorizationFilter.doFilter(servletRequest, servletResponse, filterChain)

        then:
            3 * servletRequest.method >> HttpMethod.POST
            2 * servletRequest.requestURI >> "/api/all/other/endpoints"

        and: 'should extract access token'
            1 * headerUtils.extractAccessToken(servletRequest) >>
                    { throw exception }

        and: 'handle JWT exception'
            1 * servletResponse.setStatus(HttpStatus.FORBIDDEN.value())
            1 * objectMapper.writeValueAsBytes(_ as ErrorDetails) >> errorDetailsAsByteArray
            1 * servletResponse.getOutputStream() >> servletOutputStream
            1 * servletOutputStream.write(errorDetailsAsByteArray)
            0 * _

        where:
            exception << [new JWTVerificationException('verification exception message'), new JWTDecodeException('jwt decode exception')]
    }

    @Unroll
    def "Should set response with error details if Exception out of business domain is thrown"() {
        given:
            def errorDetailsAsByteArray = "stringArray".bytes
            ServletOutputStream servletOutputStream = Mock()

        when:
            authorizationFilter.doFilter(servletRequest, servletResponse, filterChain)

        then:
            3 * servletRequest.method >> HttpMethod.POST
            2 * servletRequest.requestURI >> "/api/all/other/endpoints"

        and: 'should extract access token'
            1 * headerUtils.extractAccessToken(servletRequest) >>
                    { throw new Exception('exception') }

        and: 'handle generic exception'
            1 * servletResponse.setStatus(HttpStatus.BAD_REQUEST.value())
            1 * objectMapper.writeValueAsBytes(_ as ErrorDetails) >> errorDetailsAsByteArray
            1 * servletResponse.getOutputStream() >> servletOutputStream
            1 * servletOutputStream.write(errorDetailsAsByteArray)
            0 * _
    }
}
