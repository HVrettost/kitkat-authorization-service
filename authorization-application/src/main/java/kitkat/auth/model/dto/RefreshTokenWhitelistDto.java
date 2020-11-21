package kitkat.auth.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshTokenWhitelistDto {

    private String username;
    private String refreshToken;
    private String userAgent;
}
