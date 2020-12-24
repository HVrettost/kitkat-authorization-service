package kitkat.auth

import kitkat.auth.config.KitkatAuthFTSetup
import kitkat.auth.enumeration.UserAgent
import kitkat.auth.request.AuthenticationRequest
import org.apache.http.client.HttpResponseException
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.test.context.SpringBootTest

@SpringBootConfiguration
@SpringBootTest
class AuthenticationSpecs extends KitkatAuthFTSetup {

    def "Should throw exception trying to authenticate if username does not exist in the user service"() {
        given: 'the setup for the authentication request'
            AuthenticationRequest request =
                    new AuthenticationRequest(restClient, 'invalid-username', 'password', UserAgent.MACOSXSAFARI11.value)

        when: 'an authentication call is made'
            request.authenticate()

        then: 'exception is thrown for invalid credentials'
            HttpResponseException exception = thrown()
            exception.statusCode == 401
            exception.response.responseData['errorCode'] == 1002
            exception.response.responseData['message'] == 'Username and password could not be verified'
    }

    def "Should throw exception trying to authenticate if username is valid but password is invalid"() {
        given: 'the setup for the authentication request'
            AuthenticationRequest request =
                    new AuthenticationRequest(restClient, 'username2', 'invalid-password', UserAgent.MACOSXSAFARI11.value)

        when: 'an authentication call is made'
            request.authenticate()

        then: 'exception is thrown for invalid credentials'
            HttpResponseException exception = thrown()
            exception.statusCode == 401
            exception.response.responseData['errorCode'] == 1002
            exception.response.responseData['message'] == 'Username and password could not be verified'
    }

    def "Should make valid authenticaton and return refresh and access token cookies"() {
        given: 'the setup for the authentication request'
            AuthenticationRequest request =
                    new AuthenticationRequest(restClient, 'username', 'password', UserAgent.MACOSXSAFARI11.value)

        when: 'an authentication call is made'
            def response = request.authenticate()

        then: 'response is returned with valid cookie headers'
            with(response.responseBase.h.original.headergroup.headers.findAll {it.name == 'Set-Cookie'}) {
                it.size() == 2
                with (it[0]) {
                    buffer.toString().contains('accessToken')
                }

                with (it[1]) {
                    buffer.toString().contains('refreshToken')
                }
            }
            response.status == 204
    }
}
