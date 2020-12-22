package kitkat.auth.gateway

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import org.springframework.web.server.ResponseStatusException

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

    def setup() {
        restTemplate = Mock()
        userServiceConfigProperties = Mock()
        userServiceGateway =new UserServiceGatewayImpl(userServiceConfigProperties, restTemplate)
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

    def "Should return empty user if any exception is thrown when calling User Service"() {
        given:
            def username = "username"

        when:
            def response = userServiceGateway.getUserCredentialsByUsername(username)

        then:
            1 * userServiceConfigProperties.host >> HOST
            1 * restTemplate.getForEntity(HOST + USERS_BASE_URI + String.format(USER_URI, username), UserCredentialsDto) >> { throw new ResponseStatusException() }
            0 * _

        and:
            with(response) {
                it.username == null
                it.password == null
            }
    }
}
