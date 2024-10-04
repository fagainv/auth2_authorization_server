package com.om.auth2.as.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ISP_USER_ROLES", schema = "SRLP")
@jakarta.persistence.SequenceGenerator(name = "userrole_id_seq", sequenceName = "SRLP.ISP_USER_ROLE_ID_SEQ", allocationSize = 1, schema = "SRLP")
@Getter
@Setter
@JsonInclude(Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserRole implements Serializable {

    private static final long serialVersionUID = -41252238111124424L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userrole_id_seq")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "ISP_USER_ID")
    private User user;

    @OneToOne
    @JoinColumn(name = "ISP_ROLE_ID")
    private Role customerRole;
    @Column(name = "CREATE_DATE", nullable = true)
    private LocalDateTime createdDate;
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "UPDATE_DATE")
    private LocalDateTime updatedDate;
    @Column(name = "UPDATED_BY")
    private String updatedBy;
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((customerRole == null) ? 0 : customerRole.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserRole other = (UserRole) obj;
        if (customerRole == null) {
            if (other.customerRole != null)
                return false;
        } else if (!customerRole.equals(other.customerRole))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
    
    

}
