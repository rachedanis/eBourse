package com.bfigroupe.ebourse.persistence.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import com.bfigroupe.ebourse.persistence.model.Order;
import com.bfigroupe.ebourse.persistence.model.Portfolio;
import com.bfigroupe.ebourse.persistence.model.Value;

@NoRepositoryBean
public interface BaseOrderRepository<T extends Order> extends JpaRepository<T, Long> {

	public List<T> findAll();

	List<T> findByPortfolio(Portfolio portfolio);

	List<T> findByPortfolioNumber(String portfolioNumber);

	List<T> findByCreation(Date date);

	List<T> findByCreationAfter(Date date);

	List<T> findByCreationBefore(Date date);

	List<T> findByCreationBetween(Date startDate, Date endDate);

	List<T> findByMarketvalueValueIsin(String valueIsin);

	List<T> findByPortfolioNumberAndMarketvalueValueIsin(String potfolioNumber, String valueIsin);

	List<T> findByMarketvalueValue(Value value);

	List<T> findByStatus(String status);

	List<T> findByPortfolioNumberAndCreationBefore(String portfolioNumber, Date date);

}
