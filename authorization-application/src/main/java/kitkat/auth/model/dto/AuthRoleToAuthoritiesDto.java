package kitkat.auth.model.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthRoleToAuthoritiesDto {

    private UUID id;
    private String role;
    private String authorities;
}
