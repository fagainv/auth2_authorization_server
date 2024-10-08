package com.om.auth2.as.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "REGISTERED_CLIENTS", schema = "SRLP")
@jakarta.persistence.SequenceGenerator(name = "registered_clients_id_seq", sequenceName = "SRLP.REGISTERED_CLIENTS_SEQ_ID", allocationSize = 1, schema = "SRLP")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisteredClientEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "registered_clients_id_seq")
	private Long id;
	@Column(name = "CLIENT_ID", nullable = false)
	private String clientId;
	@Column(name = "CLIENT_SECRET", nullable = false)
	private String clientSecret;
	@Column(name = "CLIENT_NAME", nullable = true)
	private String clientName;
	@Column(name = "AUTHORIZATION_GRANT_TYPES", length = 1024, nullable = false)
	private String authorizationGrantTypes;
	@Column(name = "REDIRECT_URIS", length = 1024)
	private String redirectUris;
	@Column(name = "SCOPES", length = 1024)
	private String scopes;
	@OneToOne(mappedBy = "registeredClientEntity", fetch = FetchType.EAGER,  cascade = CascadeType.ALL)
	@JoinColumn(name = "TOKEN_SETTINGS_ENTITY_ID")	
	@JsonManagedReference
	private TokenSettingsEntity tokenSettingsEntity;
	

}
