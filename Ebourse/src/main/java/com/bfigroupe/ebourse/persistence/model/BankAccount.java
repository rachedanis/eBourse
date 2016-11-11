package com.bfigroupe.ebourse.persistence.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bfigroupe.ebourse.web.error.BankAccountException;

@Entity
@Table(name = "BankAccounts")
public class BankAccount implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(unique = true)
	private String accountNumber;

	private int accountSecretCode;
	private Date expirationDate;
	private double balance;

	@ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "user_id", referencedColumnName = "id")
	private User owner;

	@ManyToMany
	@JoinTable(name = "bankAccounts_portfolios", joinColumns = @JoinColumn(name = "bankAccount_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "portfolio_id", referencedColumnName = "id"))
	private Collection<Portfolio> portfolios;

	public BankAccount() {
	}

	public BankAccount(String accountNumber, int accountSecretCode, Date expirationDate, double balance, User owner) {
		super();
		this.accountNumber = accountNumber;
		this.accountSecretCode = accountSecretCode;
		this.expirationDate = expirationDate;
		this.balance = balance;
		this.owner = owner;
	}

	public BankAccount(String accountNumber, int accountSecretCode, Date expirationDate, double balance, User owner,
			Collection<Portfolio> portfolios) {
		super();
		this.accountNumber = accountNumber;
		this.accountSecretCode = accountSecretCode;
		this.expirationDate = expirationDate;
		this.balance = balance;
		this.owner = owner;
		this.portfolios = portfolios;
	}

	public void addPortfolio(Portfolio portfolio) {
		if (portfolios == null)
			portfolios = new ArrayList<Portfolio>();
		if (!portfolios.contains(portfolio))
			portfolios.add(portfolio);
	}

	public boolean debit(double debit) {
		if (debit <= balance) {
			this.balance -= debit;
			return true;
		}
		throw new BankAccountException("error.bankAccount.checkBalance");
	}

	public void credit(double credit) {
		this.balance += credit;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public int getAccountSecretCode() {
		return accountSecretCode;
	}

	public void setAccountSecretCode(int accountSecretCode) {
		this.accountSecretCode = accountSecretCode;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Collection<Portfolio> getPortfolios() {
		return portfolios;
	}

	public void setPortfolios(Collection<Portfolio> portfolios) {
		this.portfolios = portfolios;
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
		final BankAccount ba = (BankAccount) obj;
		if (!this.accountNumber.equals(ba.getAccountNumber())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "BankAccount [id=" + id + ", accountNumber=" + accountNumber + ", expirationDate=" + expirationDate
				+ ", balance=" + balance + ", owner=" + owner + ", portfolios=" + portfolios + "]";
	}

}
