package kitkat.auth.trait

trait CookieHelper {

    String createValidTokenCookie() {
        'accessToken=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.' +
                'eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYXV0aHMiOiJQRVJNSVNTSU9OMSBQRVJNSVNTSU9OMiIsImlhdCI6MTUxNjIzOTAyMn0.cboyQI7SmetOrcqmdhxT69PdqcSmBDmWGuMft0b2Lrg; ' +
                'refreshToken=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c'
    }

    String createNonTwoPartCookie() {
        'accessToken=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.' +
                'eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYXV0aHMiOiJQRVJNSVNTSU9OMSBQRVJNSVNTSU9OMiIsImlhdCI6MTUxNjIzOTAyMn0.cboyQI7SmetOrcqmdhxT69PdqcSmBDmWGuMft0b2Lrg;'
    }

    String createCookieThatDoesNotContainAccessTokenPart() {
        'refreshToken=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c'
    }

    String createCookieThatDoesNotContainRefreshTokenPart() {
        'accessToken=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.' +
                'eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYXV0aHMiOiJQRVJNSVNTSU9OMSBQRVJNSVNTSU9OMiIsImlhdCI6MTUxNjIzOTAyMn0.cboyQI7SmetOrcqmdhxT69PdqcSmBDmWGuMft0b2Lrg'
    }
}
