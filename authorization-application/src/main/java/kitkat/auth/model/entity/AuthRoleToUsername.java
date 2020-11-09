package kitkat.auth.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "AUTH", name = "AUTH_ROLE_TO_USERNAME")
public class AuthRoleToUsername {

    @Id
    @Column(name = "USERNAME")
    private String username;

    @Column(name = "ROLE")
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
