package com.bfigroupe.ebourse.persistence.model;

import java.sql.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("BEST_PRICE_LIMIT")
public class BestPriceLimitOrder extends Order {

	private static final long serialVersionUID = 1L;

	public BestPriceLimitOrder() {
		super();
	}

	public BestPriceLimitOrder(String operation, int initialQuantity, String validityType, Date validity) {
		super(operation, initialQuantity, validityType, validity);
	}

	public BestPriceLimitOrder(String operation, int initialQuantity, String validityType, Date validity, Date creation,
			MarketValue marketvalue, Portfolio portfolio, BankAccount bankAccount) {
		super(operation, initialQuantity, validityType, validity, creation, marketvalue, portfolio, bankAccount);
	}

	public BestPriceLimitOrder(String operation, int initialQuantity, String validityType, Date validity,
			MarketValue marketvalue, Portfolio portfolio, BankAccount bankAccount) {
		super(operation, initialQuantity, validityType, validity, marketvalue, portfolio, bankAccount);
	}

}
