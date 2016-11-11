package com.bfigroupe.ebourse.persistence.model;

import java.sql.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("MARKET_ORDER")
public class MarketOrder extends Order {

	private static final long serialVersionUID = 1L;

	public MarketOrder() {
		super();
	}

	public MarketOrder(String operation, int initialQuantity, String validityType, Date validity) {
		super(operation, initialQuantity, validityType, validity);
	}

	public MarketOrder(String operation, int initialQuantity, String validityType, Date validity, Date creation,
			MarketValue marketvalue, Portfolio portfolio, BankAccount bankAccount) {
		super(operation, initialQuantity, validityType, validity, creation, marketvalue, portfolio, bankAccount);
	}

	public MarketOrder(String operation, int initialQuantity, String validityType, Date validity,
			MarketValue marketvalue, Portfolio portfolio, BankAccount bankAccount) {
		super(operation, initialQuantity, validityType, validity, marketvalue, portfolio, bankAccount);
	}
	
}
