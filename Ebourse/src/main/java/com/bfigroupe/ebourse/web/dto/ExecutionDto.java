package com.bfigroupe.ebourse.web.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.bfigroupe.ebourse.validation.ValidDate;

public class ExecutionDto {

	@NotNull
	Long orderId;

	@ValidDate(message = "{validityDate.invalid}")
	String date;

	@Min(value = 1, message = "{quantity.positive}")
	int quantity;

	@Min(value = 1, message = "{price.positive}")
	double price;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
