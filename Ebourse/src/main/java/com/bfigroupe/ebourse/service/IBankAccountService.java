package com.bfigroupe.ebourse.service;

import java.util.List;

import com.bfigroupe.ebourse.persistence.model.BankAccount;
import com.bfigroupe.ebourse.persistence.model.User;

public interface IBankAccountService {

	public List<BankAccount> getAll();

	public BankAccount findById(Long id);

	public BankAccount findByNumber(String number);
	
	public List<BankAccount> findByOwner(User owner);
	
	public List<BankAccount> findByOwnerEmail(String email);

	public void add(BankAccount bankAccount);

	public void update(BankAccount bankAccount);

	public void delete(BankAccount bankAccount);

	public void deleteById(Long id);

	public void deleteByNumber(String number);
	
	public boolean exists(String number);

}
