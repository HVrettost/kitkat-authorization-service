package kitkat.auth.enumeration;

public enum Claims {

    USER_ID("userId");

    private final String value;

    Claims(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
