package kitkat.auth.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(schema = "AUTH", name = "AUTH_ROLE_TO_USERNAME")
public class AuthRoleToUsername {

    @Id
    @Column(name = "USERNAME")
    private String username;

    @Column(name = "ROLE")
    private String role;
}
