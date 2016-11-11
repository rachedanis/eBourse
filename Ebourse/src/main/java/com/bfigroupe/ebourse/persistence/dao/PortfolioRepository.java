package com.bfigroupe.ebourse.persistence.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bfigroupe.ebourse.persistence.model.Portfolio;
import com.bfigroupe.ebourse.persistence.model.User;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

	public List<Portfolio> findAll();

	public Portfolio findByNumber(String number);

	public List<Portfolio> findByOwnerEmail(String email);

	public List<Portfolio> findByOwner(User owner);

	public void deleteById(Long id);

	public void deleteByNumber(String number);

	public List<Portfolio> findByOwnerEmailAndNumberIgnoreCaseContaining(String email, String number);

}
