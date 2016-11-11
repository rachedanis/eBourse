package com.bfigroupe.ebourse.persistence.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bfigroupe.ebourse.web.error.RequestException;

@Entity
@Table(name = "Orders")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "order_type", discriminatorType = DiscriminatorType.STRING)
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "order_type", insertable = false, updatable = false)
	private String type;

	private String operation; // ACHAT OU VENTE
	private int initialQuantity;
	private int remainingQuantity;
	private String validityType;

	private Date creation;

	private Date validity;
	private String status;

	@ManyToOne(targetEntity = MarketValue.class)
	@JoinColumn(name = "marketvalue_id", referencedColumnName = "id")
	private MarketValue marketvalue;

	@Transient
	MarketValue currentMarketvalue;
	
	@OneToMany(mappedBy = "order")
	private Collection<Execution> executions;

	@OneToMany(mappedBy = "oldOrder")
	private Collection<Request> requests;

	@ManyToOne(targetEntity = Portfolio.class)
	@JoinColumn(name = "portfolio_number", referencedColumnName = "number")
	private Portfolio portfolio;

	@ManyToOne(targetEntity = BankAccount.class)
	@JoinColumn(name = "bankAccount_number", referencedColumnName = "accountNumber")
	private BankAccount bankAccount;

	public Order() {
		super();
	}
	
	public Order(String operation, int initialQuantity, String validityType, Date validity) {
		super();
		this.operation = operation;
		this.initialQuantity = initialQuantity;
		this.remainingQuantity = initialQuantity;
		this.validityType = validityType;
		this.validity = validity;
		this.creation = new Date(new java.util.Date().getTime());
		this.status = OrderState.NOT_EXECUTED.getState();
	}

	public Order(String operation, int initialQuantity, String validityType, Date validity, MarketValue marketvalue,
			Portfolio portfolio, BankAccount bankAccount) {
		super();
		this.operation = operation;
		this.initialQuantity = initialQuantity;
		this.remainingQuantity = initialQuantity;
		this.validityType = validityType;
		this.validity = validity;
		this.marketvalue = marketvalue;
		this.portfolio = portfolio;
		this.bankAccount = bankAccount;
		this.creation = new Date(new java.util.Date().getTime());
		this.status = OrderState.NOT_EXECUTED.getState();
	}

	public Order(String operation, int initialQuantity, String validityType, Date validity, Date creation,
			MarketValue marketvalue, Portfolio portfolio, BankAccount bankAccount) {
		super();
		this.operation = operation;
		this.initialQuantity = initialQuantity;
		this.remainingQuantity = initialQuantity;
		this.validityType = validityType;
		this.validity = validity;
		this.marketvalue = marketvalue;
		this.portfolio = portfolio;
		this.bankAccount = bankAccount;
		this.creation = creation;
		this.status = OrderState.NOT_EXECUTED.getState();
	}
	
	public boolean isValidForEdit() {
		String status = this.getStatus();
		if (status.equals(OrderState.EXECUTING.getState()) || status.equals(OrderState.NOT_EXECUTED.getState()))
			return true;
		else
			throw new RequestException("error.request.illegalState");
	}

	public boolean reduceQuantity(int quantity) {
		if (this.remainingQuantity >= quantity) {
			this.remainingQuantity -= quantity;
			if (this.remainingQuantity == 0)
				this.status = OrderState.EXECUTED.getState();
			else
				this.status = OrderState.EXECUTING.getState();
			return true;
		}
		return false;
	}

	public void addExecution(Execution exection) {
		if (this.executions == null)
			executions = new ArrayList<Execution>();
		if (!this.executions.contains(exection))
			executions.add(exection);
	}

	public void addRequest(Request request) {
		if (this.requests == null)
			this.requests = new ArrayList<Request>();
		if (!this.requests.contains(request))
			requests.add(request);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public int getInitialQuantity() {
		return initialQuantity;
	}

	public void setInitialQuantity(int initialQuantity) {
		this.initialQuantity = initialQuantity;
	}

	public int getRemainingQuantity() {
		return remainingQuantity;
	}

	public void setRemainingQuantity(int remainingQuantity) {
		this.remainingQuantity = remainingQuantity;
	}

	public String getValidityType() {
		return validityType;
	}

	public void setValidityType(String validityType) {
		this.validityType = validityType;
	}

	public Date getValidity() {
		return validity;
	}

	public void setValidity(Date validity) {
		this.validity = validity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public MarketValue getMarketvalue() {
		return marketvalue;
	}

	public void setMarketvalue(MarketValue marketvalue) {
		this.marketvalue = marketvalue;
	}

	public Collection<Execution> getExecutions() {
		return executions;
	}

	public void setExecutions(Collection<Execution> executions) {
		this.executions = executions;
	}

	public Collection<Request> getRequests() {
		return requests;
	}

	public void setRequests(Collection<Request> requests) {
		this.requests = requests;
	}

	public Portfolio getPortfolio() {
		return portfolio;
	}

	public void setPortfolio(Portfolio portfolio) {
		this.portfolio = portfolio;
	}

	public BankAccount getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}

	public Date getCreation() {
		return creation;
	}

	public void setCreation(Date creation) {
		this.creation = creation;
	}

	public String getType() {
		return type;
	}

	public MarketValue getCurrentMarketvalue() {
		return currentMarketvalue;
	}

	public void setCurrentMarketvalue(MarketValue currentMarketvalue) {
		this.currentMarketvalue = currentMarketvalue;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + "type=" + type + ", operation=" + operation + ", initialQuantity=" + initialQuantity
				+ ", remainingQuantity=" + remainingQuantity + ", validityType=" + validityType + ", creation="
				+ creation + ", validity=" + validity + ", status=" + status + ", marketvalue=" + marketvalue
				+ ", portfolio=" + portfolio + ", bankAccount=" + bankAccount + "]";
	}

}
