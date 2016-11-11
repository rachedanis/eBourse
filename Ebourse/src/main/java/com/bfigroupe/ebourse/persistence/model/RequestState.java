package com.bfigroupe.ebourse.persistence.model;

public enum RequestState {

	ACCEPTED("ACCEPTED"), REFUSED("REFUSED"), NOT_SENT("NOT_SENT"), SENT("SENT");

	private final String state;

	RequestState(String state) {
		this.state = state;
	}

	public String getState() {
		return state;
	}
}
