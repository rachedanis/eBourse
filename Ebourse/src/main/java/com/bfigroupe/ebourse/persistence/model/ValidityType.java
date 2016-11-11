package com.bfigroupe.ebourse.persistence.model;

public enum ValidityType {

	DAILY("DAILY"), FAK("FILL_AND_KILL"), GTC("GOOD_TILL_CANCEL");

	private String type;

	ValidityType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
