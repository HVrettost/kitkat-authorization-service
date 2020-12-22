package kitkat.auth.model.entity;

import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "AUTH_ROLE_TO_AUTHORITIES")
public class AuthRoleToAuthorities {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private UUID id;

    @Column(name = "ROLE")
    private String role;

    @Column(name = "ROLE_ID")
    private String roleId;

    @Column(name = "AUTHORITIES")
    private String authorities;
}
