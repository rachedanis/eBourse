package com.bfigroupe.ebourse.web.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PortfolioDto {
	
	@NotNull
	@Size(min = 10, max = 12)
	private String number;

	@NotNull(message="{bankAccounts.select}")
	private String [] associatedBankAccounts;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String [] getAssociatedBankAccounts() {
		return associatedBankAccounts;
	}

	public void setAssociatedBankAccounts(String [] associatedBankAccounts) {
		this.associatedBankAccounts = associatedBankAccounts;
	}

	@Override
	public String toString() {
		return "PortfolioDto [number=" + number + ", associatedBankAccounts=" + associatedBankAccounts + "]";
	}

}
