package kitkat.auth.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "REFRESH_TOKEN_WHITELIST")
public class RefreshTokenWhitelist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private UUID id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "USER_AGENT")
    private String userAgent;

    @Column(name = "REFRESH_TOKEN")
    private String refreshToken;
}
