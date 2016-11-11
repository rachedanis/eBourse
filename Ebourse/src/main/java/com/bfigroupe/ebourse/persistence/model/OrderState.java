package com.bfigroupe.ebourse.persistence.model;

public enum OrderState {
	NOT_EXECUTED("NOT_EXECUTED"), EXECUTING("EXECUTING"), EXECUTED("EXECUTED"), EDIT_PADDING(
			"EDIT_PADDING"), EDIT_ACCEPTED("EDIT_ACCEPTED"), CANCELED("CANCELED"), REFUSED("REFUSED");

	private final String state;

	OrderState(String state) {
		this.state = state;
	}

	public String getState() {
		return state;
	}

	public boolean isInMarket() {
		switch (state) {
		case "NOT_EXECUTED":
		case "EXECUTING":
		case "EXECUTED":
			return true;
		default:
			return false;
		}
	}
}
