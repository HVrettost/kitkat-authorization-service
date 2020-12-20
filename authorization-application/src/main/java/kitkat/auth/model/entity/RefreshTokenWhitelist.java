package kitkat.auth.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.IdClass;
import javax.persistence.Id;
import javax.persistence.Column;

@Getter
@Setter
@Entity
@Table(schema = "AUTH", name = "REFRESH_TOKEN_WHITELIST")
@IdClass(value = RefreshTokenWhitelistPK.class)
public class RefreshTokenWhitelist {

    @Id
    @Column(name = "USERNAME")
    private String username;

    @Id
    @Column(name = "USER_AGENT")
    private String userAgent;

    @Id
    @Column(name = "REFRESH_TOKEN")
    private String refreshToken;
}
