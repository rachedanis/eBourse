package com.bfigroupe.ebourse.persistence.model;

public enum UserRole {

	ROLE_USER("ROLE_USER"), ROLE_ADMIN("ROLE_ADMIN"), ROLE_BACKOFFICE("ROLE_BACKOFFICE"), ROLE_FRONTOFFICE(
			"ROLE_FRONTOFFICE"), ROLE_SUPER("ROLE_SUPER");

	String role;

	UserRole(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}
}
