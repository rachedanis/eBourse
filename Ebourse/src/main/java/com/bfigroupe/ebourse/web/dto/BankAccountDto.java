package com.bfigroupe.ebourse.web.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.bfigroupe.ebourse.validation.ValidDate;

public class BankAccountDto {

	@NotNull
	Long userId;

	@NotNull
	@Size(min = 10, max = 12)
	private String accountNumber;

	@Min(value = 1000, message = "{secretCode.lengh}")
	private int accountSecretCode;

	@ValidDate(message = "{validityDate.invalid}")
	private String expirationDate;

	@Min(0)
	double balance;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "BankAccountDto [accountNumber=" + accountNumber + ", userId=" + userId + ", expirationDate="
				+ expirationDate + ", balance=" + balance + "]";
	}

}
