package kitkat.auth.gateway

import kitkat.auth.exception.AuthenticationException
import kitkat.auth.exception.CoreException
import kitkat.auth.exception.error.AuthenticationError
import kitkat.auth.exception.mapper.UserExceptionMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

import kitkat.auth.config.properties.UserServiceConfigProperties
import kitkat.auth.model.dto.UserCredentialsDto

import spock.lang.Specification

class UserServiceGatewayImplSpec extends Specification {

    static final String USERS_BASE_URI = "/api/users"
    static final String USER_URI = "/%s/credentials"
    static final String HOST = "http://localhost:8905"

    RestTemplate restTemplate
    UserServiceConfigProperties userServiceConfigProperties
    UserServiceGateway userServiceGateway
    UserExceptionMapper userExceptionMapper

    def setup() {
        restTemplate = Mock()
        userServiceConfigProperties = Mock()
        userExceptionMapper = Mock()
        userServiceGateway =new UserServiceGatewayImpl(userServiceConfigProperties, restTemplate, userExceptionMapper)
    }

    def "should make call to user service to get user by username successfully"() {
        given:
            def username = "username"
            def password = "password"
            ResponseEntity<UserCredentialsDto> userEntity = new ResponseEntity<>(new UserCredentialsDto(username: username, password: password), HttpStatus.OK)

        when:
            def response = userServiceGateway.getUserCredentialsByUsername(username)

        then:
            1 * userServiceConfigProperties.host >> HOST
            1 * restTemplate.getForEntity(HOST + USERS_BASE_URI + String.format(USER_URI, username), UserCredentialsDto) >> userEntity
            0 * _

        and:
            with(response) {
                it == userEntity.getBody()
                it.username == userEntity.getBody().username
                it.password == userEntity.getBody().password
            }
    }

    def "Should throw AuthenticationException if HttpClientErrorException is thrown when calling User Service"() {
        given:
            def username = "username"
            HttpClientErrorException httpClientErrorException = new HttpClientErrorException(HttpStatus.CONFLICT)
            AuthenticationException authExceptionThrown = new AuthenticationException(AuthenticationError.BAD_CREDENTIALS)

        when:
            userServiceGateway.getUserCredentialsByUsername(username)

        then:
            1 * userServiceConfigProperties.host >> HOST
            1 * restTemplate.getForEntity(HOST + USERS_BASE_URI + String.format(USER_URI, username), UserCredentialsDto) >> { throw httpClientErrorException }
            1 * userExceptionMapper.mapToAuthenticationException(httpClientErrorException) >> authExceptionThrown
            0 * _

        and:
            AuthenticationException authenticationException = thrown()
            with (authenticationException) {
                errorDetails.errorCode == AuthenticationError.BAD_CREDENTIALS.errorCode
                errorDetails.message == AuthenticationError.BAD_CREDENTIALS.message
            }
    }

    def "Should throw CoreException if any other exception than HttpClientErrorException is thrown when calling User Service"() {
        given:
            def username = "username"
            Exception exception = new Exception('exception message')

        when:
            userServiceGateway.getUserCredentialsByUsername(username)

        then:
            1 * userServiceConfigProperties.host >> HOST
            1 * restTemplate.getForEntity(HOST + USERS_BASE_URI + String.format(USER_URI, username), UserCredentialsDto) >> { throw exception }
            0 * _

        and:
            CoreException coreException = thrown()
            with (coreException) {
                message == 'exception message'
            }
    }
}
