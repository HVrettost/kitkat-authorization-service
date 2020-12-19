package kitkat.auth.service

import kitkat.auth.gateway.UserServiceGateway
import kitkat.auth.model.dto.UserDto
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UserDetailsService
import spock.lang.Specification
import spock.lang.Unroll

class UserDetailsServiceImplSpec extends Specification {

    UserServiceGateway userServiceGateway
    UserDetailsService userDetailsService

    def setup() {
        userServiceGateway = Mock()
        userDetailsService = new UserDetailsServiceImpl(userServiceGateway)
    }

    def "Should load user by username successfully"() {
        given:
            def username = "username"
            def password = 'password'
            UserDto user = new UserDto(username: username, password: password)

        when:
            def response = userDetailsService.loadUserByUsername(username)

        then:
            1 * userServiceGateway.getUserByUsername(username) >> user
            0 * _

        and:
            with(response) {
                it.username == username
                it.password == password
                it.authorities == Collections.unmodifiableSet(Collections.emptySet())
            }
    }

    @Unroll
    def "Should throw BadCredentials exception for empty user or empty password"() {
        given:
            UserDto user = new UserDto(username: username, password: password)

        when:
            userDetailsService.loadUserByUsername(username)

        then:
            1 * userServiceGateway.getUserByUsername(username) >> user
            0 * _

        and:
            BadCredentialsException bcEx =  thrown()
            with(bcEx) {
                bcEx.message == String.format("No user was found for username %s", username)
            }

        where:
            username | password
            ""       | ""
            null     | ""
            ""       | null
    }
}
