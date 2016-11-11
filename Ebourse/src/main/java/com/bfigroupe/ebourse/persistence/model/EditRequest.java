package com.bfigroupe.ebourse.persistence.model;

import java.sql.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("EDIT")
public class EditRequest extends Request {

	private static final long serialVersionUID = 1L;

	@OneToOne(targetEntity = Order.class)
	@JoinColumn(name = "newOrder_id", referencedColumnName = "id")
	private Order newOrder;

	public EditRequest() {
		super();
	}

	public EditRequest(Order oldOrder) {
		super(oldOrder);
	}

	public EditRequest(Order oldOrder, Date date, Order newOrder) {
		super(oldOrder, date);
		this.newOrder = newOrder;
	}

	public EditRequest(Order oldOrder, Order newOrder) {
		super(oldOrder);
		this.newOrder = newOrder;
	}

	public Order getNewOrder() {
		return newOrder;
	}

	public void setNewOrder(Order newOrder) {
		this.newOrder = newOrder;
	}

	@Override
	public String toString() {
		return "EditRequest [newOrder=" + newOrder + "]";
	}

}
