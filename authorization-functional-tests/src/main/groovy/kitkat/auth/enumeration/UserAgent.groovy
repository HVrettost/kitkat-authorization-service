package kitkat.auth.enumeration

enum UserAgent {

    MACOSXSAFARI11('Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/11.1.2 Safari/605.1.15'),
    ANDROIDCHROME68('Mozilla/5.0 (Linux; Android 6.0.1; RedMi Note 5 Build/RB3N5C; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/68.0.3440.91 Mobile Safari/537.36'),
    SYMBIANNOKIA('Nokia7610/2.0 (5.0509.0) SymbianOS/7.0s Series60/2.1 Profile/MIDP-2.0 Configuration/CLDC-1.0')

    private String value

    UserAgent(String value) {
        this.value = value
    }

    String getValue() {
        value
    }

}