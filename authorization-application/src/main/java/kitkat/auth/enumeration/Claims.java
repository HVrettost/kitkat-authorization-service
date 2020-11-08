package kitkat.auth.enumeration;

public enum Claims {

    PERMISSIONS("perms");

    private final String value;

    Claims(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
