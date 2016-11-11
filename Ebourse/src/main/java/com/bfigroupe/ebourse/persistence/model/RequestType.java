package com.bfigroupe.ebourse.persistence.model;

public enum RequestType {

	EDIT("EDIT"), CANCEL("CANCEL");

	String type;

	RequestType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}
}
