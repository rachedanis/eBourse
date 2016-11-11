package com.bfigroupe.ebourse.persistence.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Executions")
public class Execution implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Date date;
	private int quantity;
	private Double price;

	@ManyToOne(targetEntity = Order.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "order_id", referencedColumnName = "id")
	private Order order;

	public Execution() {
		super();
	}

	public Execution(int quantity, Double price) {
		super();
		this.date = new Date(new java.util.Date().getTime());
		this.quantity = quantity;
		this.price = price;
	}

	public Execution(int quantity, Order order, Double price) {
		super();
		this.date = new Date(new java.util.Date().getTime());
		this.quantity = quantity;
		this.order = order;
		this.price = price;
	}

	public Execution(Date date, int quantity, Order order, Double price) {
		super();
		this.date = date;
		this.quantity = quantity;
		this.order = order;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Double getPrice() {
		return price;
	}

	public void setOrder(Double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Execution [id=" + id + ", date=" + date + ", quantity=" + quantity + ", price=" + price + ", order="
				+ order + "]";
	}

}
