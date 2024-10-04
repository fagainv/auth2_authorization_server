package com.om.auth2.as.repository;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import  org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Service;

import com.om.auth2.as.model.RegisteredClientEntity;

@Service
public class RegisteredClientRepositoryImpl implements RegisteredClientRepository {

	
	@Autowired
	private RegisteredClientJpaRepository registeredClientJpaRepository;
	
	@Override
	public void save(RegisteredClient registeredClient) {
		
		RegisteredClientEntity entity = RegisteredClientEntity.builder().build();
		
		entity.setId(Long.valueOf(registeredClient.getId()));
		entity.setClientId(registeredClient.getClientId());
		entity.setClientSecret(registeredClient.getClientSecret());
		entity.setClientName(registeredClient.getClientName());
		
		String authorizationGrantTypes = registeredClient.getAuthorizationGrantTypes().stream()
				.map(AuthorizationGrantType::getValue)
				.collect(Collectors.joining(","));
		entity.setAuthorizationGrantTypes(authorizationGrantTypes);
		entity.setRedirectUris(registeredClient.getPostLogoutRedirectUris().stream().collect(Collectors.joining(",")));
		
		entity.setScopes(registeredClient.getScopes().stream().collect(Collectors.joining(",")));
		
		//entity.setTokenSettings(getTokenSettings());
		
		
		registeredClientJpaRepository.save(entity);
		
	}
	
	private TokenSettings getTokenSettings() {
		return TokenSettings.builder()
				.accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
				.accessTokenTimeToLive(Duration.ofMinutes(30))
				.authorizationCodeTimeToLive(Duration.ofMinutes(10))
				.refreshTokenTimeToLive(Duration.ofDays(30))
				.reuseRefreshTokens(false)
				.build();
	}

	@Override
	public RegisteredClient findById(String id) {
		return registeredClientJpaRepository.findById(Long.valueOf(id)).map(this::toRegisteredClient).orElse(null);
	}

	@Override
	public RegisteredClient findByClientId(String clientId) {
		return registeredClientJpaRepository.findByClientId(clientId).map(this::toRegisteredClient).orElse(null);
				
	}
	
	private RegisteredClient toRegisteredClient(RegisteredClientEntity entity) {
		
		
		return RegisteredClient.withId(entity.getId().toString())
				.clientId(entity.getClientId())
				.clientSecret(entity.getClientSecret())
				.authorizationGrantTypes(convertToGrantTypeConsumer(entity.getAuthorizationGrantTypes()))
				.redirectUris(convertToConsumerSetString(entity.getRedirectUris(), ","))
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.scopes(convertToConsumerSetString(entity.getScopes(), ","))
				.build();
	}
	
	private static Consumer<Set<String>> convertToConsumerSetString(String string, String splitBy) {
		if(string == null || string.isBlank()) {
			return strings -> {
				strings.addAll(Collections.emptySet());
			};
		};
		return strings -> {
			Set<String> set = Arrays.stream(string.split(splitBy)).map(String::trim).collect(Collectors.toSet());
			strings.addAll(set);
		};
	}

	
	private static Consumer<Set<AuthorizationGrantType>> convertToGrantTypeConsumer(String grantTypesString) {
		if(grantTypesString == null || grantTypesString.isBlank()) return null;
		return grantTypes -> {
			Set<AuthorizationGrantType> set = parseGrantTypes(grantTypesString);
			grantTypes.addAll(set);
		};
	}
	
	private static Set<AuthorizationGrantType> parseGrantTypes(String grantTypesString) {
		return Arrays.stream(grantTypesString.split(",")).map(String::trim)
				.map(RegisteredClientRepositoryImpl::mapToAuthorizationGrantType)
				.collect(Collectors.toSet());
	}
	
	private static AuthorizationGrantType mapToAuthorizationGrantType(String grantType) {
		switch(grantType.toLowerCase()) {
		case "authorization_code":
			return AuthorizationGrantType.AUTHORIZATION_CODE;
		case "client_credentials":
			return AuthorizationGrantType.CLIENT_CREDENTIALS;
		case "refresh_token":
			return AuthorizationGrantType.REFRESH_TOKEN;
		default:
			throw new IllegalArgumentException("Unknown grant type: " + grantType);
		}
	}
}
