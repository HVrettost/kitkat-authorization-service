package kitkat.auth.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class RefreshTokenWhitelistPK implements Serializable {

    private String username;
    private String userAgent;
    private String refreshToken;
}
