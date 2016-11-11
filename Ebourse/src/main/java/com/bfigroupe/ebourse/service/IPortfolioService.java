package com.bfigroupe.ebourse.service;

import java.util.Collection;
import java.util.List;

import com.bfigroupe.ebourse.persistence.model.BankAccount;
import com.bfigroupe.ebourse.persistence.model.Portfolio;
import com.bfigroupe.ebourse.persistence.model.User;
import com.bfigroupe.ebourse.persistence.model.Value;

public interface IPortfolioService {
	
	public Collection<Portfolio> getAll();

	public Portfolio findById(Long id);

	public Portfolio findByNumber(String number);

	public List<Portfolio> findByOwner(User owner);

	public List<Portfolio> findByOwnerEmail(String email);
	
	public void add(Portfolio portfolio);

	public void update(Portfolio portfolio);

	public void delete(Portfolio portfolio);

	public void deleteById(Long id);

	public void deleteByNumber(String number);

	public List<BankAccount> getBankAccountsByPortfolioNumber(String PortfolioNumber);
	
	public boolean exists(Portfolio portfolio);

	public List<Portfolio> findByOwnerEmailAndNumberLike(String email, String number);

	List<Value> getValuesByPortfolioNumber(String PortfolioNumber);

}
