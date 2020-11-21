package kitkat.auth.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(schema = "AUTH", name = "AUTH_ROLE_TO_AUTHORITIES")
public class AuthRoleToAuthorities {

    @Id
    @Column(name = "ROLE")
    private String role;

    @Column(name = "AUTHORITIES")
    private String authorities;
}
