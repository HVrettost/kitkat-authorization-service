package kitkat.auth.model.request

class AuthenticationRequestDto {

    private String username
    private String password

    String getUsername() {
        username
    }

    String setUsername(String username) {
        this.username = username
    }

    String getPassword() {
        password
    }

    String setPassword(String password) {
        this.password = password
    }
}
