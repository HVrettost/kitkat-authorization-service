package kitkat.auth.jwt.util

import spock.lang.Specification

class JwtClaimUtilsSpec extends Specification {

    JwtClaimUtils jwtClaimUtils

    def setup() {
        jwtClaimUtils = new JwtClaimUtils()
    }

    def "Should extract permission claim"() {
        given:
            def accessToken = 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9' +
                    '.eyJzdWIiOiJ1c2VybmFtZSIsImF1dGhzIjoiUkVGUkVTSF9UT0tFTl9ERUxFVEUgUkVGUkVTSF9UT0tFTl9BTExfREVMRVRFIiwiaXNzIjoiZDIwYjcxZTgwMzVkNGYzMDBhZmRlMDEzYTZlOGRkOGQ3ZTg5NTE2OTVmZTk2NTg2NTg4ODQ5MzRlYzM1NTBlNyIsImV4cCI6MTYwODMwNDIwOCwiaWF0IjoxNjA4MzAzOTA4fQ' +
                    '.5WEQoXpENDM7VxkCJu0YUrcLS-zOtItfane-CWTlACk'

        when:
            def response = jwtClaimUtils.extractPermissionsClaim(accessToken)

        then:
            0 * _

        and:
            response == 'REFRESH_TOKEN_DELETE REFRESH_TOKEN_ALL_DELETE'
    }

    def "Should extract subject claim"() {
        given:
            def accessToken = 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9' +
                    '.eyJzdWIiOiJ1c2VybmFtZSIsImF1dGhzIjoiUkVGUkVTSF9UT0tFTl9ERUxFVEUgUkVGUkVTSF9UT0tFTl9BTExfREVMRVRFIiwiaXNzIjoiZDIwYjcxZTgwMzVkNGYzMDBhZmRlMDEzYTZlOGRkOGQ3ZTg5NTE2OTVmZTk2NTg2NTg4ODQ5MzRlYzM1NTBlNyIsImV4cCI6MTYwODMwNDIwOCwiaWF0IjoxNjA4MzAzOTA4fQ' +
                    '.5WEQoXpENDM7VxkCJu0YUrcLS-zOtItfane-CWTlACk'

        when:
            def response = jwtClaimUtils.extractSubjectClaim(accessToken)

        then:
            0 * _

        and:
            response == 'username'
    }
}
