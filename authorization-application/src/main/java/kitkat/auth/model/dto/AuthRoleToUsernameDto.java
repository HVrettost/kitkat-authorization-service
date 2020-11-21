package kitkat.auth.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRoleToUsernameDto {

    private String username;
    private String role;
}
