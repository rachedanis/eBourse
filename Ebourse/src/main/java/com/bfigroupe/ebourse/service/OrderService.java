package com.bfigroupe.ebourse.service;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bfigroupe.ebourse.persistence.dao.BestPriceLimitOrderRepository;
import com.bfigroupe.ebourse.persistence.dao.LimitedPriceOrderRepository;
import com.bfigroupe.ebourse.persistence.dao.MarketOrderRepository;
import com.bfigroupe.ebourse.persistence.dao.OrderRepository;
import com.bfigroupe.ebourse.persistence.dao.WithExecutionOnsetOrderRepository;
import com.bfigroupe.ebourse.persistence.model.Order;
import com.bfigroupe.ebourse.persistence.model.OrderType;
import com.bfigroupe.ebourse.persistence.model.Portfolio;
import com.bfigroupe.ebourse.persistence.model.Value;
import com.bfigroupe.ebourse.web.error.OrderException;

@Service
@Transactional
public class OrderService implements IOrderService {

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	LimitedPriceOrderRepository limitedPriceOrderRepository;

	@Autowired
	WithExecutionOnsetOrderRepository withExecutionOnsetOrderRepository;

	@Autowired
	MarketOrderRepository marketOrderRepository;

	@Autowired
	BestPriceLimitOrderRepository bestPriceLimitOrderRepository;

	@Override
	public List<Order> findAll() {
		return orderRepository.findAll();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> findByOrderType(OrderType orderType) {
		List<? extends Order> orders = null;
		switch (orderType) {
		case BEST_PRICE_LIMIT_ORDER:
			orders = bestPriceLimitOrderRepository.findAll();
			break;
		case MARKET_ORDER:
			orders = marketOrderRepository.findAll();
			break;
		case LIMITED_PRICE_ORDER:
			orders = limitedPriceOrderRepository.findAll();
			break;
		case WITH_EXECUTION_ONSET:
			orders = withExecutionOnsetOrderRepository.findAll();
			break;
		default:
			break;
		}
		return (List<Order>) orders;
	}

	@Override
	public Order findById(Long id) {
		Order order = orderRepository.findOne(id);
		if (order == null)
			throw new OrderException("error.order.notFound");
		return order;
	}

	@Override
	public List<Order> findByPortfolio(Portfolio portfolio) {
		return orderRepository.findByPortfolio(portfolio);
	}

	@Override
	public List<Order> findByPortfolioNumber(String portfolioNumber) {
		return orderRepository.findByPortfolioNumber(portfolioNumber);
	}

	@Override
	public List<Order> findByValue(Value value) {
		return orderRepository.findByMarketvalueValue(value);
	}

	@Override
	public List<Order> findByValueIsin(String valueIsin) {
		return orderRepository.findByMarketvalueValueIsin(valueIsin);
	}

	@Override
	public List<Order> findByDate(Date date) {
		return orderRepository.findByCreation(date);
	}

	@Override
	public List<Order> findByDateInterval(Date startDate, Date endDate) {
		return orderRepository.findByCreationBetween(startDate, endDate);
	}

	@Override
	public void add(Order order) {
		orderRepository.save(order);
	}

	@Override
	public void update(Order order) {
		orderRepository.save(order);
	}

	@Override
	public void delete(Order order) {
		orderRepository.delete(order);
	}

	@Override
	public void deleteById(Long id) {
		orderRepository.delete(id);
	}

	@Override
	public List<Order> findByStatus(String status) {
		return orderRepository.findByStatus(status);
	}

	@Override
	public List<Order> findByPortfolioNumberAndMarketvalueValueIsin(String potfolioNumber, String valueIsin) {
		return orderRepository.findByPortfolioNumberAndMarketvalueValueIsin(potfolioNumber, valueIsin);
	}

	@Override
	public List<Order> findByPortfolioNumberAndDateBefore(String portfolioNumber, Date date) {
		return orderRepository.findByPortfolioNumberAndCreationBefore(portfolioNumber, date);
	}

}