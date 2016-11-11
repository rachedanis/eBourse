package com.bfigroupe.ebourse.persistence.model;

import java.sql.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("LIMITED_PRICE")
public class LimitedPriceOrder extends Order {

	private static final long serialVersionUID = 1L;
	private double priceLimit;

	public LimitedPriceOrder() {
		super();
	}

	public LimitedPriceOrder(String operation, int initialQuantity, String validityType, Date validity,
			double priceLimit) {
		super(operation, initialQuantity, validityType, validity);
		this.priceLimit = priceLimit;
	}

	public LimitedPriceOrder(String operation, int initialQuantity, String validityType, Date validity, Date creation,
			MarketValue marketvalue, Portfolio portfolio, BankAccount bankAccount, double priceLimit) {
		super(operation, initialQuantity, validityType, validity, creation, marketvalue, portfolio, bankAccount);
		this.priceLimit = priceLimit;
	}

	public LimitedPriceOrder(String operation, int initialQuantity, String validityType, Date validity,
			MarketValue marketvalue, Portfolio portfolio, BankAccount bankAccount, double priceLimit) {
		super(operation, initialQuantity, validityType, validity, marketvalue, portfolio, bankAccount);
		this.priceLimit = priceLimit;
	}

	public double getPriceLimit() {
		return priceLimit;
	}

	public void setPriceLimit(double priceLimit) {
		this.priceLimit = priceLimit;
	}

}
