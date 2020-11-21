package kitkat.auth.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AuthRoleToAuthoritiesDto {

    private UUID id;
    private String role;
    private String authorities;
}
