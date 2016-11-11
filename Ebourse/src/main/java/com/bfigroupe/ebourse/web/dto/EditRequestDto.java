package com.bfigroupe.ebourse.web.dto;

import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.bfigroupe.ebourse.persistence.model.ValidityType;
import com.bfigroupe.ebourse.validation.ValidDate;

public class EditRequestDto {

	@NotNull
	Long orderId;

	@Min(value = 1, message = "{quantity.positive}")
	int newQuantity;

	@NotNull
	@Enumerated
	ValidityType validityType;

	@ValidDate(message = "{validityDate.invalid}")
	String validity;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public int getNewQuantity() {
		return newQuantity;
	}

	public void setNewQuantity(int newQuantity) {
		this.newQuantity = newQuantity;
	}

	public ValidityType getValidityType() {
		return validityType;
	}

	public void setValidityType(ValidityType validityType) {
		this.validityType = validityType;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

}
