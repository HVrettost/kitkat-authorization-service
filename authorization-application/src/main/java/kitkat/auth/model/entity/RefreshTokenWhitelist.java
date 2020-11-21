package kitkat.auth.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(schema = "AUTH", name = "REFRESH_TOKEN_WHITELIST")
public class RefreshTokenWhitelist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private UUID id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "REFRESH_TOKEN")
    private String refreshToken;

    @Column(name = "USER_AGENT")
    private String userAgent;
}
