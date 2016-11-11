package com.bfigroupe.ebourse.persistence.model;

public enum OperationType {
	
	BUY("BUY"), SELL("SELL");

	private String type;

	OperationType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
