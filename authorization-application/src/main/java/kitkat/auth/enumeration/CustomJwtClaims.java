package kitkat.auth.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomJwtClaims {

    AUTHORITIES("auths"), SUBJECT("sub");

    private final String value;
}
