package com.bfigroupe.ebourse.persistence.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Portfolios")
public class Portfolio implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Transient
	private double valorization;

	@Column(unique = true)
	private String number;

	@ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "user_id", referencedColumnName = "id")
	private User owner;

	@OneToMany(mappedBy = "portfolio")
	private Collection<Order> orders;

	@ManyToMany(mappedBy = "portfolios")
	private Collection<BankAccount> bankAccounts;
	
	@OneToMany
	private Collection<Value> values;

	public Portfolio() {
		super();
	}

	public Portfolio(String number, User owner) {
		super();
		this.number = number;
		this.owner = owner;
	}

	public Portfolio(String number, User owner, Collection<BankAccount> bankAccounts) {
		super();
		this.number = number;
		this.owner = owner;
		this.bankAccounts = bankAccounts;
	}

	public void addBankAccount(BankAccount bankAccount) {
		if (this.bankAccounts == null)
			this.bankAccounts = new ArrayList<BankAccount>();
		if (!this.bankAccounts.contains(bankAccount))
			this.bankAccounts.add(bankAccount);
	}

	public void addOrder(Order order) {
		if (this.orders == null)
			this.orders = new ArrayList<Order>();
		if (!this.orders.contains(order))
			this.orders.add(order);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Collection<Order> getOrders() {
		return orders;
	}

	public void setOrders(Collection<Order> orders) {
		this.orders = orders;
	}

	public Collection<BankAccount> getBankAccounts() {
		return bankAccounts;
	}

	public void setBankAccounts(Collection<BankAccount> bankAccounts) {
		this.bankAccounts = bankAccounts;
	}

	public double getValorization() {
		return valorization;
	}

	public void setValorization(double valorization) {
		this.valorization = valorization;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Portfolio pf = (Portfolio) obj;
		if (!this.number.equals(pf.getNumber())) {
			return false;
		}
		return true;
	}

	public Collection<Value> getValues() {
		return values;
	}

	public void setValues(Collection<Value> values) {
		this.values = values;
	}

	@Override
	public String toString() {
		return "Portfolio [id=" + id + ", number=" + number + ", owner=" + owner +", values=" + values + "]";
	}
}
