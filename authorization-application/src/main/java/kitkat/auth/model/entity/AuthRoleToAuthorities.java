package kitkat.auth.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(schema = "AUTH", name = "AUTH_ROLE_TO_AUTHORITIES")
public class AuthRoleToAuthorities {

    @Id
    @Column(name = "ROLE")
    private String role;

    @Column(name = "AUTHORITIES")
    private String authorities;

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
