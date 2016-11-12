package com.bfigroupe.ebourse.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bfigroupe.ebourse.persistence.dao.BankAccountRepository;
import com.bfigroupe.ebourse.persistence.model.BankAccount;
import com.bfigroupe.ebourse.persistence.model.User;
import com.bfigroupe.ebourse.web.error.BankAccountException;

@Service
@Transactional
public class BankAccountService implements IBankAccountService {

	@Autowired
	BankAccountRepository repository;

	@Override
	public List<BankAccount> getAll() {
		return repository.findAll();
	}

	@Override
	public BankAccount findById(Long id) {
		return repository.findOne(id);
	}

	@Override
	public BankAccount findByNumber(String number) {
		return repository.findByAccountNumber(number);
	}

	@Override
	public List<BankAccount> findByOwner(User owner) {
		return (List<BankAccount>) repository.findByOwner(owner);
	}

	@Override
	public List<BankAccount> findByOwnerEmail(String email) {
		List<BankAccount> bas = (List<BankAccount>) repository.findByOwnerEmail(email);
		if (bas == null)
			throw new BankAccountException("error.bankAccounts.notFound");
		if (bas.isEmpty())
			throw new BankAccountException("error.bankAccounts.notFound");
		return bas;
	}

	@Override
	public void add(BankAccount bankAccount) {
		repository.save(bankAccount);
	}

	@Override
	public void update(BankAccount bankAccount) {
		repository.save(bankAccount);
	}

	@Override
	public void delete(BankAccount bankAccount) {
		repository.delete(bankAccount);
	}

	@Override
	public void deleteById(Long id) {
		repository.delete(id);
	}

	@Override
	public void deleteByNumber(String number) {
		repository.delete(this.findByNumber(number));
	}

	@Override
	public boolean exists(String number) {
		if (this.findByNumber(number) != null)
			return true;
		return false;
	}

}