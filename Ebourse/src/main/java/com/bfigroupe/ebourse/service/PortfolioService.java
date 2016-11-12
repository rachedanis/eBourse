package com.bfigroupe.ebourse.service;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bfigroupe.ebourse.persistence.dao.PortfolioRepository;
import com.bfigroupe.ebourse.persistence.model.BankAccount;
import com.bfigroupe.ebourse.persistence.model.Portfolio;
import com.bfigroupe.ebourse.persistence.model.User;
import com.bfigroupe.ebourse.persistence.model.Value;

@Service
@Transactional
public class PortfolioService implements IPortfolioService {

	@Autowired
	PortfolioRepository portfolioRepository;
	
	@Autowired
	IBankAccountService baService;
	
	@Override
	public Collection<Portfolio> getAll() {
		return portfolioRepository.findAll();
	}

	@Override
	public Portfolio findById(Long id) {
		return portfolioRepository.findOne(id);
	}

	@Override
	public Portfolio findByNumber(String number) {
		return portfolioRepository.findByNumber(number);
	}

	@Override
	public List<Portfolio> findByOwner(User owner) {
		return portfolioRepository.findByOwner(owner);
	}

	@Override
	public List<Portfolio> findByOwnerEmail(String email) {
		return portfolioRepository.findByOwnerEmail(email);
	}

	@Override
	public void add(Portfolio portfolio) {
		portfolioRepository.save(portfolio);		
	}

	@Override
	public void update(Portfolio portfolio) {
		portfolioRepository.save(portfolio);				
	}

	@Override
	public void delete(Portfolio portfolio) {
		portfolioRepository.delete(portfolio);				
	}

	@Override
	public void deleteById(Long id) {
		portfolioRepository.deleteById(id);						
	}

	@Override
	public void deleteByNumber(String number) {
		portfolioRepository.deleteByNumber(number);		
	}
	
	@Override
	public boolean exists(Portfolio portfolio) {
		if (this.findByNumber(portfolio.getNumber()) == null)
			return false;
		return true;
	}

	@Override
	public List<BankAccount> getBankAccountsByPortfolioNumber(String PortfolioNumber) {
		return (List<BankAccount>) this.findByNumber(PortfolioNumber).getBankAccounts();
	}
	
	@Override
	public List<Value> getValuesByPortfolioNumber(String PortfolioNumber) {
		return (List<Value>) this.findByNumber(PortfolioNumber).getValues();
	}

	@Override
	public List<Portfolio> findByOwnerEmailAndNumberLike(String email, String number) {
		return portfolioRepository.findByOwnerEmailAndNumberIgnoreCaseContaining(email,number);
	}

}