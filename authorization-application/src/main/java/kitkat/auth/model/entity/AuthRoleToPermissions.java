package kitkat.auth.model.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(schema = "AUTH", name = "AUTH_ROLE_TO_PERMISSIONS")
public class AuthRoleToPermissions {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private UUID id;

    @Column(name = "ROLE")
    private String role;

    @Column(name = "PERMISSIONS")
    private String permissions;

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

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }
}
