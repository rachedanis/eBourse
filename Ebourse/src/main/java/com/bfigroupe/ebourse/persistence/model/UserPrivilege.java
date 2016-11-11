package com.bfigroupe.ebourse.persistence.model;

public enum UserPrivilege {

	SERVICE_ACCESS_PRIVILEGE("SERVICE_ACCESS_PRIVILEGE"), USERS_UPDATE_PRIVILEGE(
			"USERS_UPDATE_PRIVILEGE"), UPDATE_ORDERS_PRIVILEGE("UPDATE_ORDERS_PRIVILEGE"), UPDATE_REQUESTS_PRIVILEGE(
					"UPDATE_REQUESTS_PRIVILEGE"), ENABLE_USERS_PRIVILEGE(
							"ENABLE_USERS_PRIVILEGE"), ADD_BANKACCOUNTS_PRIVILEGE(
									"ADD_BANKACCOUNTS_PRIVILEGE"), ADD_PORTFOLIOS_PRIVILEGE(
											"ADD_PORTFOLIOS_PRIVILEGE"),;

	String privilege;

	UserPrivilege(String privilege) {
		this.privilege = privilege;
	}

	public String getPrivilege() {
		return privilege;
	}
}
