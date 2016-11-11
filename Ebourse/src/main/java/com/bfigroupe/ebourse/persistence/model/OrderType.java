package com.bfigroupe.ebourse.persistence.model;

public enum OrderType {
	LIMITED_PRICE_ORDER("LIMITED_PRICE"), BEST_PRICE_LIMIT_ORDER("BEST_PRICE_LIMIT"), MARKET_ORDER(
			"MARKET_ORDER"), WITH_EXECUTION_ONSET("WITH_EXECUTION_ONSET");

	private String type;

	OrderType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
