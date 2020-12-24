package kitkat.auth.request

import groovy.json.JsonBuilder
import groovyx.net.http.ContentType
import groovyx.net.http.RESTClient
import kitkat.auth.enumeration.UserAgent
import kitkat.auth.model.request.AuthenticationRequestDto

import java.net.http.HttpResponse

class AuthenticationRequest {

    private RESTClient restClient
    private AuthenticationRequestDto authenticationRequestDto
    private String path = '/api/auth/token'
    private String body
    private Map<String, String> headers

    AuthenticationRequest(RESTClient restClient, String username, String password) {
        this.restClient = restClient
        authenticationRequestDto = new AuthenticationRequestDto(username: username, password: password)
        body = new JsonBuilder(authenticationRequestDto).toPrettyString()
        headers = ['User-Agent': UserAgent.MACOSXSAFARI11.value, 'Content-Type': 'application/json']
    }

    def authenticate() {
        restClient.post(path: path,
                        body: body,
                        headers: headers,
                        requestContentType: ContentType.JSON)
    }
}
