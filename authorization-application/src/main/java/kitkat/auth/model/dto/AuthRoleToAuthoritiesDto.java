package kitkat.auth.model.dto;

import java.util.UUID;

public class AuthRoleToAuthoritiesDto {

    private UUID id;
    private String role;
    private String authorities;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }
}
