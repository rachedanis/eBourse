package com.bfigroupe.ebourse.web.dto;

import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import com.bfigroupe.ebourse.persistence.model.UserRole;

public class UserRoleDto {
   
	@NotNull
    private Long userId;

    @NotNull
    @Enumerated
    private UserRole role;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}
    
    
}
