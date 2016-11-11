package com.bfigroupe.ebourse.persistence.model;

import java.sql.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("WITH_EXECUTION_ONSET")
public class WithExecutionOnsetOrder extends Order {

	private static final long serialVersionUID = 1L;
	private double onset;

	public WithExecutionOnsetOrder() {
		super();
	}

	public WithExecutionOnsetOrder(String operation, int initialQuantity, String validityType, Date validity,
			double onset) {
		super(operation, initialQuantity, validityType, validity);
		this.onset = onset;
	}

	public WithExecutionOnsetOrder(String operation, int initialQuantity, String validityType, Date validity,
			Date creation, MarketValue marketvalue, Portfolio portfolio, BankAccount bankAccount, double onset) {
		super(operation, initialQuantity, validityType, validity, creation, marketvalue, portfolio, bankAccount);
		this.onset = onset;
	}

	public WithExecutionOnsetOrder(String operation, int initialQuantity, String validityType, Date validity,
			MarketValue marketvalue, Portfolio portfolio, BankAccount bankAccount, double onset) {
		super(operation, initialQuantity, validityType, validity, marketvalue, portfolio, bankAccount);
		this.onset = onset;
	}

	public double getOnset() {
		return onset;
	}

	public void setOnset(double onset) {
		this.onset = onset;
	}

}
