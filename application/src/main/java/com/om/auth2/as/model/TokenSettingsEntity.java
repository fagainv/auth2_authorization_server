package com.om.auth2.as.model;

import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * We use the <code>TokenSettingsEntity</code> class as a facility to store the
 * settings in the db
 * 
 * See this
 * {@link org.springframework.security.oauth2.server.authorization.settings.TokenSettings}
 * 
 * @author vfranzoni
 * 
 * 
 *         return new
 *         Builder().authorizationCodeTimeToLive(Duration.ofMinutes(5))
 *         .accessTokenTimeToLive(Duration.ofMinutes(5))
 *         .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
 *         .deviceCodeTimeToLive(Duration.ofMinutes(5))
 *         .reuseRefreshTokens(true)
 *         .refreshTokenTimeToLive(Duration.ofMinutes(60))
 *         .idTokenSignatureAlgorithm(SignatureAlgorithm.RS256)
 *         .x509CertificateBoundAccessTokens(false);
 */
@Entity
@Table(name = "TOKEN_SETTINGS", schema = "SRLP")
@jakarta.persistence.SequenceGenerator(name = "token_settings_id_seq", sequenceName = "SRLP.TOKEN_SETTINGS_SEQ_ID", allocationSize = 1, schema = "SRLP")
@Getter
@Setter
@ToString
@lombok.Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenSettingsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "token_settings_id_seq")
	private Long id;

	@Column(name = "AUTHORIZATION_CODE_TTL", columnDefinition = "5") // 5 minutes
	@lombok.Builder.Default
	private Long authorizationCodeTimeToLive = 5L;
	
	@Column(name = "ACCESS_TOKEN_TTL", columnDefinition = "5")
	@lombok.Builder.Default
	private Long accessTokenTimeToLive = 5L;
	
	@Column(name = "ACCESS_TOKEN_FORMAT", columnDefinition = "SELF_CONTAINED") // see {@link OAuth2TokenFormat} by default is SELF_CONTAINED
	@lombok.Builder.Default
	private String accessTokenFormat = OAuth2TokenFormat.SELF_CONTAINED.getValue();
	
	@Column(name = "DEVICE_CODE_TTL")
	@lombok.Builder.Default
	private Long deviceCodeTimeToLive = 5L;
	
	@Column(name = "REUSE_REFRESH_TOKENS")
	@lombok.Builder.Default
	private boolean reuseRefreshTokens = Boolean.FALSE;
	
	@Column(name = "REFRESH_TOKEN_TTL", columnDefinition = "5")
	@lombok.Builder.Default
	private Long refreshTokenTimeToLive = 60L;
	
	@Column(name = "ID_TOKEN_SIGNATURE_ALGORITHM")
	@lombok.Builder.Default
	private String idTokenSignatureAlgorithm = SignatureAlgorithm.RS256.getName();
	
	@Column(name = "X509_CERTIFICATE_BAT")
	@lombok.Builder.Default
	private boolean x509CertificateBoundAccessTokens = Boolean.FALSE;
	
	@OneToOne()
	@JoinColumn(name = "REGISTERED_CLIENT_ID")
	@JsonBackReference
	private RegisteredClientEntity registeredClientEntity;

}
