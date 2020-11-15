package kitkat.auth.enumeration;

public enum CustomJwtClaims {

    AUTHORITIES("auths"), SUBJECT("sub");

    private final String value;

    CustomJwtClaims(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
