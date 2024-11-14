package com.om.auth2.as.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name = "ISP_USERS", schema = "SRLP")
@SequenceGenerator(name = "custLoginSequence", sequenceName = "SRLP.ISP_USER_SEQ", allocationSize = 1)
@ToString
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    private static final long serialVersionUID = -412522238111124424L;

    @Id
    @Column(name = "ISP_USER_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "custLoginSequence")
    private Long id;
    @Column(name = "USERNAME")
    private String username;
    @Column(name = "PASSWORD")
    private String password;
    
    @Column(name = "ENABLED")
    @Builder.Default
    private String active = "";
    
    // By default user cannot log in until it is approved
    @Column(name = "STATUS")
    @Builder.Default
    private String status = "P";
    
    @Column(name = "IS_TEMP_PASSWORD")
    private String tempPass;
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;
    @Column(name = "CREATED_BY", length = 20)
    private String createdBy;
    @Column(name = "UPDATED_DATE", nullable = true)
    private LocalDateTime updatedDate;
    @Column(name = "UPDATED_BY", nullable = true, length = 20)
    private String updatedBy;
    @Column(name = "LAST_LOGIN", nullable = true)
    private LocalDateTime lastLoginDate;
    
    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL, mappedBy="user", targetEntity=UserRole.class)    
    @ToString.Exclude
    private List<UserRole> userRoles;

    @Transient
    @ToString.Exclude
    private List<GrantedAuthority> authorities;
    @Transient
    @Builder.Default
    private Integer loginAttempts = 0;
    @Transient
    private String newPassword;
    @Transient
    private String confirmPassword;

    public User() {
    }

    /**
     * Used whend all attributes from db are populated Currently used in
     * findByUsername
     * 
     * @param id
     * @param username
     * @param password
     * @param active
     * @param customerProfileId
     * @param tempPass
     * @param createdDate
     * @param createdBy
     * @param updatedDate
     * @param updatedBy
     */
    public User(Long id, String username, String password, String active,
            String tempPass, LocalDateTime createdDate, String createdBy, LocalDateTime updatedDate, String updatedBy) {

        this.id = id;
        this.username = username;
        this.password = password;
        this.active = active;        
        this.tempPass = tempPass;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
    }

    /**
     * Used in CustomerLoginDataRepositoryImpl.findCustomerLoginByUserId
     * 
     * @param id
     * @param customerProfileId
     * @param customerProfileIspUniqueId
     */
    public User(Long id) {
        this.id = id;        
    }

    /**
     * Used in ICustomerLoginRepository.loadByUsername
     * 
     * @param username
     * @param password
     * @param active
     */
    public User(String username, String password, String active) {
        this.username = username;
        this.password = password;
        this.active = active;

    }

   
    @Transient
    @JsonIgnore
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Transient
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return loginAttempts < 6;
    }

    
    @Transient
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    
    @Transient
    @JsonIgnore
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }

}
