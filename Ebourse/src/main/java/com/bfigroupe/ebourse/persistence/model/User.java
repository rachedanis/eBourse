package com.bfigroupe.ebourse.persistence.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String firstName;

	private String lastName;

	private String email;

	private String cin;

	@Column(length = 60)
	private String password;

	private boolean enabled;

	private boolean confirmed;

	@ManyToMany
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Collection<Role> roles;

	@OneToMany(mappedBy = "owner")
	private Collection<BankAccount> bankAccounts;

	@OneToMany(mappedBy = "owner")
	private Collection<Portfolio> portfolios;

	public User() {
		super();
		this.enabled = false;
		this.confirmed = false;
	}

	public void addPortfolio(Portfolio portfolio) {
		if (portfolios == null)
			portfolios = new ArrayList<Portfolio>();
		if (!portfolios.contains(portfolio))
			portfolios.add(portfolio);
	}

	public void addBankAccount(BankAccount bankAccount) {
		if (bankAccounts == null)
			bankAccounts = new ArrayList<BankAccount>();
		if (!bankAccounts.contains(bankAccount))
			bankAccounts.add(bankAccount);
	}

	public void secureData() {
		this.bankAccounts = null;
		this.portfolios = null;
		this.roles = null;
		this.password = null;
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String username) {
		this.email = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(final Collection<Role> roles) {
		this.roles = roles;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(final boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	public Collection<BankAccount> getBankAccounts() {
		return bankAccounts;
	}

	public void setBankAccounts(Collection<BankAccount> bankAccounts) {
		this.bankAccounts = bankAccounts;
	}

	public Collection<Portfolio> getPortfolios() {
		return portfolios;
	}

	public void setPortfolios(Collection<Portfolio> portfolios) {
		this.portfolios = portfolios;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((email == null) ? 0 : email.hashCode());
		return result;
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
		final User user = (User) obj;
		if (!email.equals(user.email)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("User [firstName=").append(firstName).append("]").append("[lastName=").append(lastName)
				.append("]").append("[username").append("[cin=").append(cin).append("]").append(email).append("]");
		return builder.toString();
	}

}
