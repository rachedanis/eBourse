package com.bfigroupe.ebourse.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bfigroupe.ebourse.persistence.dao.RoleRepository;
import com.bfigroupe.ebourse.persistence.model.BankAccount;
import com.bfigroupe.ebourse.persistence.model.BestPriceLimitOrder;
import com.bfigroupe.ebourse.persistence.model.CancelRequest;
import com.bfigroupe.ebourse.persistence.model.EditRequest;
import com.bfigroupe.ebourse.persistence.model.Execution;
import com.bfigroupe.ebourse.persistence.model.LimitedPriceOrder;
import com.bfigroupe.ebourse.persistence.model.MarketOrder;
import com.bfigroupe.ebourse.persistence.model.MarketValue;
import com.bfigroupe.ebourse.persistence.model.OperationType;
import com.bfigroupe.ebourse.persistence.model.Order;
import com.bfigroupe.ebourse.persistence.model.OrderState;
import com.bfigroupe.ebourse.persistence.model.OrderType;
import com.bfigroupe.ebourse.persistence.model.Portfolio;
import com.bfigroupe.ebourse.persistence.model.Request;
import com.bfigroupe.ebourse.persistence.model.RequestState;
import com.bfigroupe.ebourse.persistence.model.RequestType;
import com.bfigroupe.ebourse.persistence.model.Role;
import com.bfigroupe.ebourse.persistence.model.User;
import com.bfigroupe.ebourse.persistence.model.WithExecutionOnsetOrder;
import com.bfigroupe.ebourse.web.dto.BankAccountDto;
import com.bfigroupe.ebourse.web.dto.EditRequestDto;
import com.bfigroupe.ebourse.web.dto.EvalRequestDto;
import com.bfigroupe.ebourse.web.dto.ExecutionDto;
import com.bfigroupe.ebourse.web.dto.OrderDto;
import com.bfigroupe.ebourse.web.dto.PortfolioDto;
import com.bfigroupe.ebourse.web.dto.UserRoleDto;
import com.bfigroupe.ebourse.web.error.BankAccountException;
import com.bfigroupe.ebourse.web.error.ExecutionException;
import com.bfigroupe.ebourse.web.error.MarketValueException;
import com.bfigroupe.ebourse.web.error.OrderException;
import com.bfigroupe.ebourse.web.error.PortfolioException;
import com.bfigroupe.ebourse.web.error.RequestException;
import com.bfigroupe.ebourse.web.error.UserNotFoundException;

@Service
@Transactional
public class DAOService implements IDAOService {

	@Autowired
	IBankAccountService bankAccountService;

	@Autowired
	IPortfolioService portfolioService;

	@Autowired
	IUserService userService;

	@Autowired
	IOrderService orderService;

	@Autowired
	IExecutionService executionService;

	@Autowired
	IRequestService requestService;

	@Autowired
	IMarketValueService marketValueService;

	@Autowired
	private RoleRepository roleRepository;

	// private static final Logger logger =
	// LoggerFactory.getLogger(DAOService.class);

	private Order editRequestDtoAndOrderToOrder(EditRequestDto editRequestDto, Order oldOrder) {
		Order order = new Order(oldOrder.getOperation(), editRequestDto.getNewQuantity(),
				editRequestDto.getValidityType().getType(), Date.valueOf(editRequestDto.getValidity()),
				oldOrder.getMarketvalue(), oldOrder.getPortfolio(), oldOrder.getBankAccount());
		return order;
	}

	private Order orderDtoToOrder(OrderDto orderDto) {
		Order order;
		OrderType type = orderDto.getType();
		switch (type) {
		case LIMITED_PRICE_ORDER:
			order = new LimitedPriceOrder(orderDto.getOperation().getType(), orderDto.getInitialQuantity(),
					orderDto.getValidityType().getType(), Date.valueOf(orderDto.getValidity()),
					orderDto.getLimitPrice());
			break;
		case BEST_PRICE_LIMIT_ORDER:
			order = new BestPriceLimitOrder(orderDto.getOperation().getType(), orderDto.getInitialQuantity(),
					orderDto.getValidityType().getType(), Date.valueOf(orderDto.getValidity()));
			break;
		case MARKET_ORDER:
			order = new MarketOrder(orderDto.getOperation().getType(), orderDto.getInitialQuantity(),
					orderDto.getValidityType().getType(), Date.valueOf(orderDto.getValidity()));
			break;
		case WITH_EXECUTION_ONSET:
			order = new WithExecutionOnsetOrder(orderDto.getOperation().getType(), orderDto.getInitialQuantity(),
					orderDto.getValidityType().getType(), Date.valueOf(orderDto.getValidity()), orderDto.getOnset());
			break;
		default:
			throw new OrderException("error.order.illegalState");
		}

		Portfolio portfolio = portfolioService.findByNumber(orderDto.getPortfolioNumber());
		BankAccount bankAccount = bankAccountService.findByNumber(orderDto.getBankAccountNumber());
		MarketValue marketValue = marketValueService.findMostRecentMarketValueByIsin(orderDto.getIsin());
		if (portfolio == null) {
			throw new PortfolioException("error.portfolio.notFound");
		} else if (bankAccount == null) {
			throw new BankAccountException("error.bankAccount.notFound");
		} else if (marketValue == null) {
			throw new MarketValueException("error.marketValue.notFound");
		} else {
			order.setMarketvalue(marketValue);
			order.setBankAccount(bankAccount);
			order.setPortfolio(portfolio);
		}
		return order;
	}

	@Override
	public void addBankAccountToPortfolio(BankAccount bankAccount, Portfolio portfolio) {
		portfolio.addBankAccount(bankAccount);
		bankAccount.addPortfolio(portfolio);
		bankAccountService.update(bankAccount);
		portfolioService.update(portfolio);
	}

	@Override
	public List<Order> findOrdersByUserEmail(String email) {
		List<Order> orders = new ArrayList<Order>();
		for (Portfolio porfolio : portfolioService.findByOwnerEmail(email))
			orders.addAll(porfolio.getOrders());
		return orders;
	}

	@Override
	public void addBankAccountToPortfolioByPortfoliotNumber(String PortfolioNumber, BankAccount bankAccount) {
		Portfolio portfolio = portfolioService.findByNumber(PortfolioNumber);
		addBankAccountToPortfolio(bankAccount, portfolio);
	}

	@Override
	public void removeBankAccountFromPortfolio(BankAccount bankAccount, Portfolio portfolio) {
		portfolio.getBankAccounts().remove(bankAccount);
		bankAccount.getPortfolios().remove(portfolio);
		portfolioService.update(portfolio);
		bankAccountService.update(bankAccount);
	}

	@Override
	public void removeBankAccountFromPortfolioByPortfolioNumber(String PortfolioNumber, BankAccount bankAccount) {
		Portfolio portfolio = portfolioService.findByNumber(PortfolioNumber);
		this.removeBankAccountFromPortfolio(bankAccount, portfolio);
	}

	@Override
	public List<Execution> getExecutionsByOrderId(Long orderId) {
		return (List<Execution>) orderService.findById(orderId).getExecutions();
	}

	@Override
	public void addExecution(Order order, Execution execution) {
		order.addExecution(execution);
		execution.setOrder(order);
		executionService.update(execution);
		orderService.update(order);
	}

	@Override
	public void addExecutionByOrderId(Long id, Execution execution) {
		Order order = orderService.findById(id);
		this.addExecution(order, execution);
	}

	@Override
	public Collection<Request> getRequestsByOrderId(Long orderId) {
		return orderService.findById(orderId).getRequests();
	}

	@Override
	public void addRequest(Order order, Request request) {
		order.addRequest(request);
		request.setOldOrder(order);
		requestService.update(request);
		orderService.update(order);
	}

	@Override
	public void addRequestByOrderId(Long id, Request request) {
		Order order = orderService.findById(id);
		this.addRequest(order, request);
	}

	@Override
	public void addPortfolioToBankAccount(BankAccount bankAccount, Portfolio portfolio) {
		bankAccount.addPortfolio(portfolio);
		portfolio.addBankAccount(bankAccount);
		bankAccountService.update(bankAccount);
		portfolioService.update(portfolio);
	}

	@Override
	public void addPortfolioToBankAccountByBankAcccountNumber(String bankAccountNumber, Portfolio portfolio) {
		BankAccount bankAccount = bankAccountService.findByNumber(bankAccountNumber);
		this.addPortfolioToBankAccount(bankAccount, portfolio);
	}

	@Override
	public void removePortfolioFromBankAccount(BankAccount bankAccount, Portfolio portfolio) {
		bankAccount.getPortfolios().remove(portfolio);
		portfolio.getBankAccounts().remove(bankAccount);
		bankAccountService.update(bankAccount);
		portfolioService.update(portfolio);
	}

	@Override
	public void removePortfolioFromBankAccountByBankAccountNumber(String bankAccountNumber, Portfolio portfolio) {
		BankAccount bankAccount = bankAccountService.findByNumber(bankAccountNumber);
		this.removePortfolioFromBankAccount(bankAccount, portfolio);
	}

	@Override
	public List<Order> findOrdersByUserEmailAndIsin(String email, String isin) {
		List<Order> orders = new ArrayList<Order>();
		for (Portfolio portfolio : portfolioService.findByOwnerEmail(email))
			orders = orderService.findByPortfolioNumberAndMarketvalueValueIsin(portfolio.getNumber(), isin);
		return orders;
	}

	@Override
	public List<Portfolio> findPortfoliosByUserEmail(String email) {
		List<Portfolio> portfolios = portfolioService.findByOwnerEmail(email);
		return portfolios;
	}

	@Override
	public List<Portfolio> findPortfoliosByUserEmailAndNumberLike(String email, String number) {
		return portfolioService.findByOwnerEmailAndNumberLike(email, number);
	}

	@Override
	public List<Request> findRequestsByUserEmail(String email) {
		List<Request> requests = new ArrayList<Request>();
		for (Order order : this.findOrdersByUserEmail(email))
			requests.addAll(order.getRequests());
		return requests;
	}

	@Override
	public List<Request> findRequestsByUserEmailAndIsin(String email, String isin) {
		List<Request> requests = new ArrayList<Request>();
		for (Order order : this.findOrdersByUserEmailAndIsin(email, isin))
			requests.addAll(order.getRequests());
		return requests;
	}

	@Override
	public List<Execution> findExecutionsByUserEmail(String email) {
		List<Execution> executions = new ArrayList<Execution>();
		for (Order order : this.findOrdersByUserEmail(email))
			executions.addAll(order.getExecutions());
		return executions;
	}

	@Override
	public boolean executeOrder(Long id, ExecutionDto executionDto) {
		Order order = orderService.findById(id);
		if (order == null)
			throw new OrderException("error.order.notFound");
		Execution execution = new Execution(Date.valueOf(executionDto.getDate()), executionDto.getQuantity(), order,
				executionDto.getPrice());
		if (order.reduceQuantity(execution.getQuantity())) {
			this.addExecutionByOrderId(id, execution);
			return true;
		}
		return false;
	}

	@Override
	public List<Execution> findExecutionsByUserEmailAndIsin(String email, String isin) {
		List<Execution> executions = new ArrayList<Execution>();
		for (Order order : this.findOrdersByUserEmailAndIsin(email, isin))
			executions.addAll(order.getExecutions());
		return executions;
	}

	@Override
	public boolean createCancelRequestByOrderId(Long id) {
		Order order = orderService.findById(id);
		return this.createCancelRequestByOrder(order);
	}

	@Override
	public boolean createCancelRequestByOrder(Order order) {
		if (order != null) {
			CancelRequest cancelRequest = new CancelRequest(order);
			this.addRequest(order, cancelRequest);
			return true;
		}
		return false;
	}

	@Override
	public boolean createCancelRequestByOrderIdAndEmail(Long id, String email) {
		Order order = orderService.findById(id);
		if (order == null)
			throw new OrderException("error.order.notFound");

		if (!order.getPortfolio().getOwner().getEmail().equals(email))
			throw new OrderException("message.unauth");

		for (Request request : order.getRequests()) {
			if (request.getType().equals(RequestType.CANCEL.getType())
					&& (request.getStatus().equals(RequestState.NOT_SENT.getState())
							|| request.getStatus().equals(RequestState.SENT.getState())))
				throw new RequestException("error.request.cancel.alreadyAdded");
			if (request.getType().equals(RequestType.EDIT.getType())
					&& (request.getStatus().equals(RequestState.NOT_SENT.getState())
							|| request.getStatus().equals(RequestState.SENT.getState())))
				throw new RequestException("error.request.edit.alreadyAdded");
		}

		/*
		 * IF YOU WANT THAT A USER CAN CANCEL AN ORDER THAT HAS AN EDIT REQUEST
		 * PADDING (and so delete it), UNCOMMENT THIS AND COMMENT THE PREVIOUS
		 * FOR LOOP
		 */
		// for (Iterator<Request> iterator = order.getRequests().iterator();
		// iterator.hasNext();) {
		// Request request = iterator.next();
		// if (request.getType().equals(RequestType.EDIT.getType())
		// && (request.getStatus().equals(RequestState.NOT_SENT.getState())
		// || request.getStatus().equals(RequestState.SENT.getState()))) {
		// iterator.remove();
		// deleteRequest(request);
		// }
		// }

		CancelRequest cancelRequest = new CancelRequest(order);
		this.addRequest(order, cancelRequest);
		return true;

	}

	@Override
	public double valorizePortfolio(String portfolioNumber) {
		Portfolio portfolio = portfolioService.findByNumber(portfolioNumber);
		if (portfolio == null)
			throw new OrderException("error.portfolio.notFound");
		double valorization = 0;
		if (portfolio.getOrders() == null)
			throw new OrderException("error.orders.notFound");
		List<String> isins = getPortfolioIsin(portfolio);
		for (String isin : isins)
			valorization += getMarketValueQuantityByPortfolioAndIsin(portfolioNumber, isin)
					* getMostRecentMarketValueCoursByIsin(isin);
		return valorization;
	}

	private List<String> getPortfolioIsin(Portfolio portfolio) {
		List<String> isins = new ArrayList<String>();
		for (Order or : portfolio.getOrders())
			if (!isins.contains(or.getMarketvalue().getValue().getIsin())) {
				isins.add(or.getMarketvalue().getValue().getIsin());
			}
		return isins;
	}

	@Override
	public void updateBankAccountBalance(BankAccount bankAccount, double val) {
		bankAccount.credit(bankAccount.getBalance() + val);
		bankAccountService.update(bankAccount);
	}

	@Override
	public double valorizeExecution(Execution execution) {
		return execution.getQuantity() * execution.getPrice();
	}

	public double getMostRecentMarketValueCoursByIsin(String isin) {
		MarketValue marketValue = marketValueService.findMostRecentMarketValueByIsin(isin);
		if (marketValue == null)
			throw new MarketValueException("error.marketvalue.notFound");
		double mostRecentMarketValueCours = marketValue.getCours();
		return mostRecentMarketValueCours;
	}

	@Override
	public boolean orderIsValidForUpdate(Order order) {
		if (order.getOperation().equals(OperationType.SELL.getType())) {
			int valueQuantity = this.getMarketValueQuantityByPortfolioAndIsin(order.getPortfolio().getNumber(),
					order.getMarketvalue().getValue().getIsin());
			if (order.getInitialQuantity() > valueQuantity)
				throw new OrderException("error.order.checkQuantity");
			return true;
		} else {
			BankAccount bankAccount = order.getBankAccount();
			double bankAccountValorization = bankAccount.getBalance();
			double orderValorization = valorizeOrderInWorstBuyCases(order);
			double portfolioOrdersValorization = 0;
			for (Order or : order.getPortfolio().getOrders())
				if (or != null)
					portfolioOrdersValorization += valorizeOrderInWorstCases(or);
			if (bankAccountValorization + portfolioOrdersValorization >= orderValorization)
				return true;
			else
				throw new OrderException("error.order.checkAccounts");
		}
	}

	private double valorizeOrderInWorstCases(Order order) {
		double valorization = 0;
		if (order.getStatus().equals(OrderState.EXECUTED.getState())
				|| order.getStatus().equals(OrderState.EXECUTING.getState())
				|| order.getStatus().equals(OrderState.EDIT_PADDING.getState())) {
			if (order instanceof LimitedPriceOrder) {
				if (order.getOperation().equals(OperationType.SELL.getType())) {
					valorization = 0;
				} else if (order.getOperation().equals(OperationType.BUY.getType())) {
					valorization = ((LimitedPriceOrder) order).getPriceLimit() * order.getRemainingQuantity() * -1;
				} else
					throw new OrderException("error.order.illegalState");
			} else if (order instanceof BestPriceLimitOrder) {
				double v = getMostRecentMarketValueCoursByIsin(order.getMarketvalue().getValue().getIsin());
				if (order.getOperation().equals(OperationType.SELL.getType())) {
					valorization = 0;
				} else if (order.getOperation().equals(OperationType.BUY.getType())) {
					valorization = -1 * v * order.getRemainingQuantity();
				} else
					throw new OrderException("error.order.illegalState");
			} else if (order instanceof MarketOrder) {
				double v = getMostRecentMarketValueCoursByIsin(order.getMarketvalue().getValue().getIsin());
				if (order.getOperation().equals(OperationType.SELL.getType())) {
					valorization = 0;
				} else if (order.getOperation().equals(OperationType.BUY.getType())) {
					valorization = -1 * v * order.getRemainingQuantity();
				} else
					throw new OrderException("error.order.illegalState");
			} else if (order instanceof WithExecutionOnsetOrder) {
				if (order.getOperation().equals(OperationType.SELL.getType())) {
					valorization = 0;
				} else if (order.getOperation().equals(OperationType.BUY.getType())) {
					valorization = -((WithExecutionOnsetOrder) order).getOnset() * (order.getRemainingQuantity()) * -1;
				} else
					throw new OrderException("error.order.illegalState");
			} else
				throw new OrderException("error.order.illegalType");
		}
		return valorization;
	}

	@SuppressWarnings("unused")
	private double valorizeOrderInBestSellCases(Order order) {
		double valorization = 0;
		if (order.getOperation().equals(OperationType.BUY.getType())) {
			if (order.getStatus().equals(OrderState.EXECUTED.getState())
					|| order.getStatus().equals(OrderState.EXECUTING.getState())
					|| order.getStatus().equals(OrderState.EDIT_PADDING.getState())
					|| order.getStatus().equals(OrderState.NOT_EXECUTED.getState())) {
				if (order instanceof LimitedPriceOrder) {
					valorization = ((LimitedPriceOrder) order).getPriceLimit() * order.getRemainingQuantity() * -1;
				} else if (order instanceof BestPriceLimitOrder) {
					double v = getMostRecentMarketValueCoursByIsin(order.getMarketvalue().getValue().getIsin());
					valorization = -1 * v * order.getRemainingQuantity();
				} else if (order instanceof MarketOrder) {
					double v = getMostRecentMarketValueCoursByIsin(order.getMarketvalue().getValue().getIsin());
					valorization = -1 * v * order.getRemainingQuantity();
				} else if (order instanceof WithExecutionOnsetOrder) {
					valorization = -((WithExecutionOnsetOrder) order).getOnset() * (order.getRemainingQuantity()) * -1;
				} else
					throw new OrderException("error.order.illegalType");
			}
		} else
			throw new OrderException("error.order.illegalState");
		return valorization;
	}

	private int getMarketValueQuantityByPortfolioAndIsin(String number, String isin) {
		List<Order> portfolioOrders = orderService.findByPortfolioNumberAndMarketvalueValueIsin(number, isin);
		if (portfolioOrders == null)
			throw new PortfolioException("error.portfolios.notFound");
		if (portfolioOrders.isEmpty())
			throw new PortfolioException("error.portfolios.notFound");
		int valueQuantity = 0;
		for (Order or : portfolioOrders)
			if (or.getStatus().equals(OrderState.EXECUTING.getState())
					|| or.getStatus().equals(OrderState.EXECUTED.getState()))
				if (or.getOperation().equals(OperationType.BUY.getType()))
					valueQuantity += or.getInitialQuantity() - or.getRemainingQuantity();
				else
					valueQuantity -= or.getInitialQuantity() - or.getRemainingQuantity();
		return valueQuantity;
	}

	private double valorizeOrderInWorstBuyCases(Order order) {
		double valorization = 0;
		if (order.getStatus().equals(OrderState.EXECUTED.getState())
				|| order.getStatus().equals(OrderState.EXECUTING.getState())
				|| order.getStatus().equals(OrderState.EDIT_PADDING.getState())) {
			if (order instanceof LimitedPriceOrder) {
				if (order.getOperation().equals(OperationType.SELL.getType())) {
					valorization = 0;
				} else if (order.getOperation().equals(OperationType.BUY.getType())) {
					valorization = ((LimitedPriceOrder) order).getPriceLimit() * order.getRemainingQuantity() * -1;
				} else
					throw new OrderException("error.order.illegalState");
			} else if (order instanceof BestPriceLimitOrder) {
				double v = getMostRecentMarketValueCoursByIsin(order.getMarketvalue().getValue().getIsin());
				if (order.getOperation().equals(OperationType.SELL.getType())) {
					valorization = 0;
				} else if (order.getOperation().equals(OperationType.BUY.getType())) {
					valorization = -1 * v * order.getRemainingQuantity();
				} else
					throw new OrderException("error.order.illegalState");
			} else if (order instanceof MarketOrder) {
				double v = getMostRecentMarketValueCoursByIsin(order.getMarketvalue().getValue().getIsin());
				if (order.getOperation().equals(OperationType.SELL.getType())) {
					valorization = 0;
				} else if (order.getOperation().equals(OperationType.BUY.getType())) {
					valorization = -1 * v * order.getRemainingQuantity();
				} else
					throw new OrderException("error.order.illegalState");
			} else if (order instanceof WithExecutionOnsetOrder) {
				if (order.getOperation().equals(OperationType.SELL.getType())) {
					valorization = 0;
				} else if (order.getOperation().equals(OperationType.BUY.getType())) {
					valorization = -((WithExecutionOnsetOrder) order).getOnset() * (order.getRemainingQuantity()) * -1;
				} else
					throw new OrderException("error.order.illegalState");
			} else
				throw new OrderException("error.order.illegalType");
		}
		return valorization;
	}

	@Override
	public void createEditRequest(EditRequestDto editRequestDto) {
		Order order = orderService.findById(editRequestDto.getOrderId());
		if (order == null)
			throw new OrderException("error.order.notFound");
		if (order.isValidForEdit()) {
			EditRequest request = new EditRequest(order);
			Order newOrder = editRequestDtoAndOrderToOrder(editRequestDto, order);
			if (!orderIsValidForUpdate(newOrder)) {
				throw new OrderException("error.order.invalidForUpdate");
			}
			newOrder.setStatus(OrderState.EDIT_PADDING.getState());
			request.setNewOrder(newOrder);
			orderService.add(newOrder);
			addRequest(order, request);
		}
	}

	@Override
	public void deleteRequestByIdAndEmail(Long id, String email) {
		Request request = requestService.findById(id);
		if (request == null)
			throw new RequestException("error.request.notFound");
		if (request.getStatus().equals(RequestState.NOT_SENT.getState())) {
			if (request.getOldOrder().getPortfolio().getOwner().getEmail().equals(email)) {
				deleteRequest(request);
			} else
				throw new RequestException("message.unauth");
		} else if (request.getStatus().equals(RequestState.SENT.getState()))
			throw new RequestException("error.request.delete.sent");
		else
			throw new RequestException("error.request.delete.evaluated");
	}

	@Override
	public void deleteRequest(Request request) {
		Order order = request.getOldOrder();
		order.getRequests().remove(request);
		if (request.getType().equals(RequestType.EDIT.getType())) {
			Order newOrder = ((EditRequest) request).getNewOrder();
			requestService.delete(request);
			orderService.delete(newOrder);
		} else
			requestService.delete(request);
		orderService.update(order);
	}

	@Override
	public void forcedRequestDelete(Request request) {
		if (request == null)
			throw new RequestException("error.request.notFound");
		Order order = request.getOldOrder();
		order.getRequests().remove(request);
		if (request.getType().equals(RequestType.EDIT.getType())) {
			Order newOrder = ((EditRequest) request).getNewOrder();
			requestService.delete(request);
			orderService.delete(newOrder);
		} else
			requestService.delete(request);
		forcedDeleteOrder(order);
	}

	@Override
	public void createNewOrder(OrderDto orderDto) {
		Order newOrder = orderDtoToOrder(orderDto);
		if (orderIsValidForUpdate(newOrder)) {
			orderService.add(newOrder);
		} else
			throw new OrderException("message.order.invalidForUpdate");
	}

	@Override
	public void createPortfolio(PortfolioDto portfolioDto) {
		Portfolio portfolio = portfolioService.findByNumber(portfolioDto.getNumber());
		if (portfolio != null)
			throw new PortfolioException("error.portfolio.alreadyExists");
		String[] bankAccountsNumbers = portfolioDto.getAssociatedBankAccounts();
		List<BankAccount> bankAccounts = new ArrayList<BankAccount>();
		for (int i = 0; i < bankAccountsNumbers.length; i++) {
			BankAccount ba = bankAccountService.findByNumber(bankAccountsNumbers[i]);
			if (ba != null)
				bankAccounts.add(ba);
		}
		if (bankAccounts.isEmpty())
			throw new BankAccountException("error.bankAccounts.notFound");
		User user = bankAccounts.get(0).getOwner();
		portfolio = new Portfolio(portfolioDto.getNumber(), user);
		user.addPortfolio(portfolio);
		this.addPortfolioToBankAccounts(bankAccounts, portfolio);
		userService.save(user);
	}

	private void addPortfolioToBankAccounts(List<BankAccount> bankAccounts, Portfolio portfolio) {
		for (BankAccount bankAccount : bankAccounts)
			portfolio.addBankAccount(bankAccount);
		portfolioService.add(portfolio);
		for (BankAccount bankAccount : bankAccounts) {
			bankAccount.addPortfolio(portfolio);
			bankAccountService.update(bankAccount);
		}
	}

	@Override
	public void createBankAccount(BankAccountDto bankAccountDto) {
		BankAccount bankAccount = bankAccountService.findByNumber(bankAccountDto.getAccountNumber());
		if (bankAccount != null)
			throw new BankAccountException("error.bankAccount.alreadyExists");
		User user = userService.findById(bankAccountDto.getUserId());
		if (user == null)
			throw new UserNotFoundException();
		bankAccount = new BankAccount(bankAccountDto.getAccountNumber(), bankAccountDto.getAccountSecretCode(),
				Date.valueOf(bankAccountDto.getExpirationDate()), bankAccountDto.getBalance(), user);
		bankAccountService.add(bankAccount);
	}

	@Override
	public void forcedDeleteUser(User user) {
		List<Portfolio> userPortfolios = (List<Portfolio>) user.getPortfolios();
		List<BankAccount> userBankAccounts = (List<BankAccount>) user.getBankAccounts();
		List<Order> userOrders = new ArrayList<Order>();
		if (userPortfolios != null) {
			for (Portfolio pf : userPortfolios)
				userOrders.addAll((List<Order>) pf.getOrders());
			for (Order order : userOrders)
				forcedDeleteOrder(order);
			for (Portfolio pf : userPortfolios)
				portfolioService.delete(pf);
		}
		if (userBankAccounts != null)
			for (BankAccount ba : userBankAccounts)
				bankAccountService.delete(ba);
		userService.deleteUser(user);
	}

	@Override
	public void forcedDeleteOrder(Order order) {
		if (order == null)
			throw new OrderException("error.order.notFound");
		List<Request> userRequests = (List<Request>) order.getRequests();
		List<Execution> userExecutions = (List<Execution>) order.getExecutions();
		for (Request req : userRequests)
			forcedRequestDelete(req);
		for (Execution ex : userExecutions)
			executionService.delete(ex);

		orderService.delete(order);
	}

	@Override
	public void setUserRole(UserRoleDto userRoleDto) {
		User user = userService.findById(userRoleDto.getUserId());
		if (user == null)
			throw new UserNotFoundException();
		Role role = roleRepository.findByName(userRoleDto.getRole().getRole());
		user.setRoles(Arrays.asList(role));
		userService.save(user);
	}

	@Override
	public void evaluateRequest(EvalRequestDto evalRequestDto) {
		Request request = requestService.findById(evalRequestDto.getRequestId());
		if (request == null)
			throw new RequestException("error.request.notFound");
		Order oldOrder = request.getOldOrder();
		switch (evalRequestDto.getStatus()) {
		case ACCEPTED:
			if (request instanceof EditRequest) {
				Order newOrder = ((EditRequest) request).getNewOrder();
				oldOrder.setValidity(newOrder.getValidity());
				oldOrder.setValidityType(newOrder.getValidityType());
				oldOrder.setInitialQuantity(newOrder.getInitialQuantity());
				newOrder.setStatus(OrderState.EDIT_ACCEPTED.getState());
				orderService.update(newOrder);
			} else {
				oldOrder.setStatus(OrderState.CANCELED.getState());
			}
			orderService.update(oldOrder);
			break;
		case REFUSED:
			if (request instanceof EditRequest) {
				Order newOrder = ((EditRequest) request).getNewOrder();
				newOrder.setStatus(OrderState.REFUSED.getState());
			}
			break;
		default:
			throw new RequestException("error.request.illegalState");
		}
		updateRequestStatus(request, evalRequestDto.getStatus());

	}

	@Override
	public void executeOrder(ExecutionDto executionDto) {
		Order order = orderService.findById(executionDto.getOrderId());
		if (order.getRemainingQuantity() < executionDto.getQuantity())
			throw new ExecutionException("error.execution.invalide");
		Execution execution = new Execution(executionDto.getQuantity(), order, executionDto.getPrice());
		execution.setDate(Date.valueOf(executionDto.getDate()));
		BankAccount ba = order.getBankAccount();
		if (order.getOperation().equals(OperationType.BUY.getType()))
			ba.debit(valorizeExecution(execution));
		else if (order.getOperation().equals(OperationType.SELL.getType()))
			ba.credit(valorizeExecution(execution));
		else
			throw new ExecutionException("error.execution.invalide");
		addExecution(order, execution);
		order.reduceQuantity(execution.getQuantity());
		orderService.update(order);
	}

	@Override
	public void updateRequestStatus(Request request, RequestState state) {
		request.updateState(state);
		requestService.update(request);
	}

	@Override
	public List<BankAccount> findBankAccountsByUserEmail(String email) {
		return bankAccountService.findByOwnerEmail(email);
	}
}
