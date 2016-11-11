package com.bfigroupe.ebourse.service;

import java.util.Collection;
import java.util.List;

import com.bfigroupe.ebourse.persistence.model.BankAccount;
import com.bfigroupe.ebourse.persistence.model.Execution;
import com.bfigroupe.ebourse.persistence.model.Order;
import com.bfigroupe.ebourse.persistence.model.Portfolio;
import com.bfigroupe.ebourse.persistence.model.Request;
import com.bfigroupe.ebourse.persistence.model.RequestState;
import com.bfigroupe.ebourse.persistence.model.User;
import com.bfigroupe.ebourse.web.dto.BankAccountDto;
import com.bfigroupe.ebourse.web.dto.EditRequestDto;
import com.bfigroupe.ebourse.web.dto.EvalRequestDto;
import com.bfigroupe.ebourse.web.dto.ExecutionDto;
import com.bfigroupe.ebourse.web.dto.OrderDto;
import com.bfigroupe.ebourse.web.dto.PortfolioDto;
import com.bfigroupe.ebourse.web.dto.UserRoleDto;

public interface IDAOService {

	public void addBankAccountToPortfolio(BankAccount bankAccount, Portfolio portfolio);

	public void addBankAccountToPortfolioByPortfoliotNumber(String PortfolioNumber, BankAccount bankAccount);

	public void removeBankAccountFromPortfolio(BankAccount bankAccount, Portfolio portfolio);

	public void removeBankAccountFromPortfolioByPortfolioNumber(String PortfolioNumber, BankAccount bankAccount);

	public List<Execution> getExecutionsByOrderId(Long orderId);

	public void addExecution(Order order, Execution execution);

	public void addExecutionByOrderId(Long id, Execution execution);

	public Collection<Request> getRequestsByOrderId(Long orderId);

	public void addRequest(Order order, Request request);

	public void addRequestByOrderId(Long id, Request request);

	public void addPortfolioToBankAccount(BankAccount bankAccount, Portfolio portfolio);

	public void addPortfolioToBankAccountByBankAcccountNumber(String bankAccountNumber, Portfolio portfolio);

	public void removePortfolioFromBankAccount(BankAccount bankAccount, Portfolio portfolio);

	public void removePortfolioFromBankAccountByBankAccountNumber(String bankAccountNumber, Portfolio portfolio);

	public List<Order> findOrdersByUserEmail(String email);

	public List<Order> findOrdersByUserEmailAndIsin(String email, String isin);

	public List<Portfolio> findPortfoliosByUserEmail(String email);

	public List<Portfolio> findPortfoliosByUserEmailAndNumberLike(String email, String number);

	public List<Request> findRequestsByUserEmail(String email);

	public List<Request> findRequestsByUserEmailAndIsin(String email, String isin);

	public List<Execution> findExecutionsByUserEmail(String email);

	public boolean executeOrder(Long id, ExecutionDto executionDto);

	public List<Execution> findExecutionsByUserEmailAndIsin(String email, String isin);

	public boolean createCancelRequestByOrderId(Long id);

	public boolean createCancelRequestByOrderIdAndEmail(Long id, String email);

	public boolean createCancelRequestByOrder(Order order);

	public double valorizePortfolio(String portfolioNumber);

	public double valorizeExecution(Execution execution);

	public void createEditRequest(EditRequestDto editRequestDto);

	public boolean orderIsValidForUpdate(Order order);

	public void updateBankAccountBalance(BankAccount bankAccount, double val);

	public void deleteRequestByIdAndEmail(Long id, String email);

	public void deleteRequest(Request request);
	
	public void createNewOrder(OrderDto orderDto);

	public void createPortfolio(PortfolioDto portfolioDto);

	public void createBankAccount(BankAccountDto bankAccountDto);

	public void forcedDeleteUser(User user);

	public void forcedDeleteOrder(Order order);

	public void evaluateRequest(EvalRequestDto evalRequestDto);

	public void forcedRequestDelete(Request request);

	public void executeOrder(ExecutionDto executionDto);

	public void updateRequestStatus(Request request, RequestState state);

	public void setUserRole(UserRoleDto userRoleDto);

	public List<BankAccount> findBankAccountsByUserEmail(String email);

}
