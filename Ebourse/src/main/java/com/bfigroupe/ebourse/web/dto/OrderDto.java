package com.bfigroupe.ebourse.web.dto;

import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.bfigroupe.ebourse.persistence.model.OperationType;
import com.bfigroupe.ebourse.persistence.model.OrderType;
import com.bfigroupe.ebourse.persistence.model.ValidityType;
import com.bfigroupe.ebourse.validation.ValidDate;

public class OrderDto {

	@NotNull
	@Enumerated
	OperationType operation;

	@Min(value = 1, message = "{quantity.positive}")
	int initialQuantity;

	@NotNull
	@Enumerated
	ValidityType validityType;

	@ValidDate(message = "{validityDate.invalid}")
	String validity;

	@NotBlank
	String isin;

	@NotBlank
	String portfolioNumber;

	@NotBlank
	String bankAccountNumber;

	@NotNull
	@Enumerated
	OrderType type;

	double limitPrice;
	double onset;

	public OperationType getOperation() {
		return operation;
	}

	public void setOperation(OperationType operation) {
		this.operation = operation;
	}

	public int getInitialQuantity() {
		return initialQuantity;
	}

	public void setInitialQuantity(int initialQuantity) {
		this.initialQuantity = initialQuantity;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	public String getIsin() {
		return isin;
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

	public String getPortfolioNumber() {
		return portfolioNumber;
	}

	public void setPortfolioNumber(String portfolioNumber) {
		this.portfolioNumber = portfolioNumber;
	}

	public String getBankAccountNumber() {
		return bankAccountNumber;
	}

	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}

	public double getLimitPrice() {
		return limitPrice;
	}

	public void setLimitPrice(double limitPrice) {
		this.limitPrice = limitPrice;
	}

	public double getOnset() {
		return onset;
	}

	public void setOnset(double onset) {
		this.onset = onset;
	}

	public ValidityType getValidityType() {
		return validityType;
	}

	public void setValidityType(ValidityType validityType) {
		this.validityType = validityType;
	}

	public OrderType getType() {
		return type;
	}

	public void setType(OrderType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "OrderDto [operation=" + operation.getType() + ", initialQuantity=" + initialQuantity + ", validityType="
				+ validityType.getType() + ", validity=" + validity + ", isin=" + isin + ", portfolioNumber="
				+ portfolioNumber + ", bankAccountNumber=" + bankAccountNumber + ", type=" + type.getType()
				+ ", limitPrice=" + limitPrice + ", onset=" + onset + "]";
	}

}
