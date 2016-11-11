package com.bfigroupe.ebourse.persistence.dao;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bfigroupe.ebourse.persistence.model.BankAccount;
import com.bfigroupe.ebourse.persistence.model.User;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
	
	public BankAccount findByAccountNumber(String accountNumber);

	public Collection<BankAccount> findByOwnerEmail(String email);

	public void deleteByAccountNumber(String number);

	public Collection<BankAccount> findByOwner(User owner);
	
}
