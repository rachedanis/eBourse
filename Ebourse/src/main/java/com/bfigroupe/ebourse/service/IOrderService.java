package com.bfigroupe.ebourse.service;

import java.sql.Date;
import java.util.List;

import com.bfigroupe.ebourse.persistence.model.Order;
import com.bfigroupe.ebourse.persistence.model.OrderType;
import com.bfigroupe.ebourse.persistence.model.Portfolio;
import com.bfigroupe.ebourse.persistence.model.Value;

public interface IOrderService {
	
	public List<Order> findAll();
	
	public List<Order> findByOrderType(OrderType orderType);

	public Order findById(Long id);
	
	public List<Order> findByPortfolio(Portfolio portfolio);
	
	public List<Order> findByPortfolioNumber(String portfolioNumber);
	
	public List<Order> findByValue(Value value);
	
	public List<Order> findByStatus(String status);
	
	public List<Order> findByValueIsin(String valueIsin);
	
	public List<Order> findByDate(Date date);

	public List<Order> findByDateInterval(Date startDate, Date endDate);
	
	public void add(Order order);
	
	public void update(Order order);

	public void delete(Order order);

	public void deleteById(Long id);

	public List<Order> findByPortfolioNumberAndMarketvalueValueIsin(String potfolioNumber, String valueIsin);

	public List<Order> findByPortfolioNumberAndDateBefore(String portfolioNumber, Date date);

}
