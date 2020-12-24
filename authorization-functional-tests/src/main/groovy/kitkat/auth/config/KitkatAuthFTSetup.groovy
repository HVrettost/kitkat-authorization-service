package kitkat.auth.config

import groovyx.net.http.RESTClient
import spock.lang.Specification

class KitkatAuthFTSetup extends Specification {

    private static final String HOST = 'https://localhost:8900'
    RESTClient restClient

    def setup() {
        restClient = new RESTClient(HOST)
    }
}
