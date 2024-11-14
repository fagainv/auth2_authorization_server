package com.om.auth2.as;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ContextConfiguration( classes = TestSecurityConfig.class)
class Oauth2AuthorizationServerApplicationTests {

	
	private static final String CLIENT_ID= "message_client";
	private static final String CLIENT_SECRET = "secret";
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void performTokenRequestWhenValidClientCredentialsThenOk() throws Exception {
		// @formatter:off
		this.mockMvc.perform(post("/oauth2/token")
				.param("grant_type", "client_credentials")
				.param("scope", "message:read")
				.with(httpBasic(CLIENT_ID,CLIENT_SECRET)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.access_token").isString())
				.andExpect(jsonPath("$.expires_in").isNumber())
				.andExpect(jsonPath("$.scope").value("message:read"))
				.andExpect(jsonPath("$.token_type").value("Bearer"));	
		// @formatter:on
	}
	
	@Test
	void performTokenRequestWhenMissingScopeThenOk() throws Exception {
		// @formatter:off
		this.mockMvc.perform(post("/oauth2/token")
				.param("grant_type", "client_credentials")
				.param("scope", "message:read message:write")
				.with(httpBasic(CLIENT_ID, CLIENT_SECRET)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.access_token").isString())
				.andExpect(jsonPath("$.expires_in").isNumber())
				.andExpect(jsonPath("$.scope").value("message:read message:write"))
				.andExpect(jsonPath("$.token_type").value("Bearer"));
		// @formatter:on
	}

	
	@Test
	void performTokenRequestWhenInvalidClientCredentialsThenUnauthorized() throws Exception {
		// @formatter:off
		this.mockMvc.perform(post("/oauth2/token")
				.param("grant_type", "client_credentials")
				.param("scope", "message:read")
				.with(httpBasic("invalid_user","invalid_password")))
				.andExpect(status().isUnauthorized())
				 .andExpect(jsonPath("$.error").value("invalid_client"));
				
		// @formatter:on
	}
	
	@Test
	void performTokenRequestWhenMissingGrantTypeThenUnauthorized() throws Exception {
		// @formatter:off
		this.mockMvc.perform(post("/oauth2/token")
				.with(httpBasic("bad", "password")))
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.error").value("invalid_client"));
		// @formatter:on
	}
	
	@Test
	void performTokenRequestWhenGrantTypeNotRegisteredThenBadRequest() throws Exception {
		// @formatter:off
		this.mockMvc.perform(post("/oauth2/token")
				.param("grant_type", "client_credentials")				
				.with(httpBasic("login-client","openid-connect")))
				.andExpect(status().isBadRequest())
				 .andExpect(jsonPath("$.error").value("unauthorized_client"));
				
		// @formatter:on
	}
	
	@Test
	void performIntrospectionRequestWhenValidTokenThenOk() throws Exception {
		// @formatter:off
		this.mockMvc.perform(post("/oauth2/introspect")
				.param("token", getAccessToken())				
				.with(httpBasic(CLIENT_ID,CLIENT_SECRET)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.active").value("true"))				
				.andExpect(jsonPath("$.aud[0]").value(CLIENT_ID))
				.andExpect(jsonPath("$.client_id").value(CLIENT_ID))
				.andExpect(jsonPath("$.exp").isNumber())
				.andExpect(jsonPath("$.iat").isNumber())
				.andExpect(jsonPath("$.nbf").isNumber())
				.andExpect(jsonPath("$.scope").value("message:read"))
				.andExpect(jsonPath("$.iss").value("http://localhost:9000"))
				.andExpect(jsonPath("$.sub").value(CLIENT_ID))
				.andExpect(jsonPath("$.token_type").value("Bearer"));
		// @formatter:on
	}
	
	private String getAccessToken() throws Exception {
		// @formatter:off
		MvcResult mvcResult = this.mockMvc.perform(post("/oauth2/token")
				  .param("grant_type", "client_credentials")
				  .param("scope", "message:read")
				  .with(httpBasic(CLIENT_ID, CLIENT_SECRET)))
				  .andExpect(status().isOk())
				  .andExpect(jsonPath("$.access_token").exists())
				  .andReturn();
		
		String tokenResponseJson = mvcResult.getResponse().getContentAsString();
		Map<String, Object> tokenResponse = this.objectMapper.readValue(tokenResponseJson, new TypeReference<>() {});
		// @formatter:on
		
		return tokenResponse.get("access_token").toString();
	}

	@Test
	void performIntrospectionRequestWhenInvalidCredentialsThenUnathorized() throws Exception {
		// @formatter:off
		this.mockMvc.perform(post("/oauth2/introspect")
				.param("token", getAccessToken())				
				.with(httpBasic("invalid","invalid")))
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.error").value("invalid_client"));
		// @formatter:on
				
	}
	
	
}
