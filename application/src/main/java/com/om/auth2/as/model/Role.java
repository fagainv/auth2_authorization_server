package com.om.auth2.as.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "ISP_ROLES", schema = "SRLP")
@SequenceGenerator(name = "customerRoleSeq", sequenceName = "SRLP.ISP_ROLE_ID_SEQ", allocationSize = 1)
@Getter
@Setter
@ToString
public class Role implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ISP_ROLE_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customerRoleSeq")
    private Long id;
    @Column(name = "ROLE")
    private String role;
    @Column(name = "IS_ACTIVE")
    private String isActive;
    @Column(name = "CREATE_BY")
    private String createdBy;
    @Column(name = "CREATED_DATE")
    private Date createdDate;
    @Column(name = "UPDATE_BY")
    private String updatedBy;
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((role == null) ? 0 : role.hashCode());
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
		Role other = (Role) obj;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		return true;
	}


    
}
