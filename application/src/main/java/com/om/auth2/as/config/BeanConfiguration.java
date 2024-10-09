package com.om.auth2.as.config;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
public class BeanConfiguration {

	@Value("${authorizationServerSettingsIssuer}")
	private String authorizationServerSettingsIssuer;
	
	@Autowired
	private CustomJwtCustomizer customJwtCustomizer;
	
	@Bean
	PasswordEncoder passwordEncoder() {
		Map<String, PasswordEncoder> encoders = new HashMap<>();
		encoders.put("bcrypt", new BCryptPasswordEncoder());
		
		return new DelegatingPasswordEncoder("bcrypt", encoders);
	}
	
	@Bean
	JWKSource<SecurityContext> jwkSource(KeyPair keyPair) {
		// the public key
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		// the private key
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

		// the rsaKey for the jwkSet
		RSAKey rsaKey = new RSAKey.Builder(publicKey).privateKey(privateKey).keyID(UUID.randomUUID().toString())
				.build();
		JWKSet jwkSet = new JWKSet(rsaKey);
		return new ImmutableJWKSet<>(jwkSet);
	}

	@Bean
	AuthorizationServerSettings providerSettings() {
		
		return AuthorizationServerSettings.builder()
			
				.issuer(authorizationServerSettingsIssuer).build();															
	}
	
	
	@Bean
	@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
	KeyPair generateRsaKey() {
		KeyPair keyPair;
		
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(2048);
			keyPair = keyPairGenerator.generateKeyPair();
		}
		catch(Exception ex) {
			throw new IllegalStateException(ex);
		}
		
		return keyPair;
	}
	
	@Bean
	OAuth2TokenGenerator<Jwt> jwtTokenGenerator(JwtEncoder jwtEncoder) {
		JwtGenerator jwtGenerator = new JwtGenerator(jwtEncoder);
		
		jwtGenerator.setJwtCustomizer(customJwtCustomizer);
		
		return jwtGenerator;
	}

	
	
}
