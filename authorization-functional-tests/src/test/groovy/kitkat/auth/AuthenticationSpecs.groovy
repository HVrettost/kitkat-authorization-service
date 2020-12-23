package kitkat.auth

import groovy.json.JsonBuilder
import groovyx.net.http.ContentType
import kitkat.auth.config.KitkatAuthFTSetup
import kitkat.auth.model.request.AuthenticationRequestDto


class AuthenticateSpecs extends KitkatAuthFTSetup {

    def "Should throw exception trying to authenticate"() {
        given:
            def authenticationRequest = new AuthenticationRequestDto(username: 'username', password: 'password')

        when:
            def response = restClient.post(path: '/api/auth/token',
                                       body: new JsonBuilder(authenticationRequest).toPrettyString(),
                                       headers: createHeaders(),
                                       requestContentType: ContentType.JSON)

        then:
            null == null
            //response.responseBase.h.original.headergroup.headers[3].value == null
    }


}
