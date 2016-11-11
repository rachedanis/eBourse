package com.bfigroupe.ebourse.spring;

import java.sql.Date;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bfigroupe.ebourse.persistence.dao.PrivilegeRepository;
import com.bfigroupe.ebourse.persistence.dao.RoleRepository;
import com.bfigroupe.ebourse.persistence.dao.UserRepository;
import com.bfigroupe.ebourse.persistence.model.BankAccount;
import com.bfigroupe.ebourse.persistence.model.Entreprise;
import com.bfigroupe.ebourse.persistence.model.Execution;
import com.bfigroupe.ebourse.persistence.model.LimitedPriceOrder;
import com.bfigroupe.ebourse.persistence.model.MarketOrder;
import com.bfigroupe.ebourse.persistence.model.MarketValue;
import com.bfigroupe.ebourse.persistence.model.OperationType;
import com.bfigroupe.ebourse.persistence.model.Order;
import com.bfigroupe.ebourse.persistence.model.Portfolio;
import com.bfigroupe.ebourse.persistence.model.Privilege;
import com.bfigroupe.ebourse.persistence.model.Role;
import com.bfigroupe.ebourse.persistence.model.User;
import com.bfigroupe.ebourse.persistence.model.ValidityType;
import com.bfigroupe.ebourse.persistence.model.Value;
import com.bfigroupe.ebourse.persistence.model.WithExecutionOnsetOrder;
import com.bfigroupe.ebourse.service.IBankAccountService;
import com.bfigroupe.ebourse.service.IDAOService;
import com.bfigroupe.ebourse.service.IEntrepriseService;
import com.bfigroupe.ebourse.service.IExecutionService;
import com.bfigroupe.ebourse.service.IMarketValueService;
import com.bfigroupe.ebourse.service.IOrderService;
import com.bfigroupe.ebourse.service.IPortfolioService;
import com.bfigroupe.ebourse.service.IRequestService;
import com.bfigroupe.ebourse.service.IValueService;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

	private boolean alreadySetup = false;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PrivilegeRepository privilegeRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// ______
	@Autowired
	IOrderService orderService;

	@Autowired
	IBankAccountService bankAccountService;

	@Autowired
	IPortfolioService portfolioService;

	@Autowired
	IValueService valueService;

	@Autowired
	IMarketValueService marketValueService;

	@Autowired
	IRequestService requestService;

	@Autowired
	IEntrepriseService entrepriseService;

	@Autowired
	IExecutionService executionService;

	@Autowired
	IDAOService daoService;

	// ______

	// API

	@Override
	@Transactional
	public void onApplicationEvent(final ContextRefreshedEvent event) {
		if (alreadySetup) {
			return;
		}

		// == create initial privileges
		final Privilege serviceAccessPrivilege = createPrivilegeIfNotFound("SERVICE_ACCESS_PRIVILEGE");
		final Privilege usersUpdatePrivilege = createPrivilegeIfNotFound("USERS_UPDATE_PRIVILEGE");
		final Privilege updateOrdersPrivilege = createPrivilegeIfNotFound("UPDATE_ORDERS_PRIVILEGE");
		final Privilege updateRequestsPrivilege = createPrivilegeIfNotFound("UPDATE_REQUESTS_PRIVILEGE");
		final Privilege enableUsersPrivilege = createPrivilegeIfNotFound("ENABLE_USERS_PRIVILEGE");
		final Privilege addBaPrivilege = createPrivilegeIfNotFound("ADD_BANKACCOUNTS_PRIVILEGE");
		final Privilege addPfPrivilege = createPrivilegeIfNotFound("ADD_PORTFOLIOS_PRIVILEGE");
		final Privilege loginPrivilege = createPrivilegeIfNotFound("LOGIN_PRIVILEGE");

		// == create initial roles
		final List<Privilege> userPrivileges = Arrays.asList(loginPrivilege, serviceAccessPrivilege);
		final List<Privilege> adminPrivileges = Arrays.asList(loginPrivilege, usersUpdatePrivilege);
		final List<Privilege> backOfficePrivileges = Arrays.asList(loginPrivilege, updateOrdersPrivilege,
				updateRequestsPrivilege);
		final List<Privilege> frontOfficePrivileges = Arrays.asList(loginPrivilege, enableUsersPrivilege,
				addPfPrivilege, addBaPrivilege);
		final List<Privilege> superPrivileges = Arrays.asList(loginPrivilege, serviceAccessPrivilege,
				usersUpdatePrivilege, enableUsersPrivilege, addPfPrivilege, addBaPrivilege, updateOrdersPrivilege,
				updateRequestsPrivilege);

		createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
		createRoleIfNotFound("ROLE_BACKOFFICE", backOfficePrivileges);
		createRoleIfNotFound("ROLE_FRONTOFFICE", frontOfficePrivileges);
		createRoleIfNotFound("ROLE_USER", userPrivileges);

		createRoleIfNotFound("ROLE_SUPER", superPrivileges);

		// == create initial users
		final Role superRole = roleRepository.findByName("ROLE_SUPER");
		final User user = new User();
		user.setFirstName("Test");
		user.setLastName("Test");
		user.setPassword(passwordEncoder.encode("test"));
		user.setEmail("test@test.com");
		user.setRoles(Arrays.asList(superRole));
		user.setEnabled(true);
		user.setConfirmed(true);

		final Role userRole = roleRepository.findByName("ROLE_USER");
		final User user2 = new User();
		user2.setFirstName("User");
		user2.setLastName("User");
		user2.setPassword(passwordEncoder.encode("user"));
		user2.setEmail("user@user.com");
		user2.setRoles(Arrays.asList(userRole));
		user2.setEnabled(true);
		user2.setConfirmed(true);

		final Role backofficeRole = roleRepository.findByName("ROLE_BACKOFFICE");
		final User user3 = new User();
		user3.setFirstName("backoffice");
		user3.setLastName("backoffice");
		user3.setPassword(passwordEncoder.encode("backoffice"));
		user3.setEmail("backoffice@backoffice.com");
		user3.setRoles(Arrays.asList(backofficeRole));
		user3.setEnabled(true);
		user3.setConfirmed(true);

		final Role frontofficeRole = roleRepository.findByName("ROLE_FRONTOFFICE");
		final User user4 = new User();
		user4.setFirstName("Frontoffice");
		user4.setLastName("Frontoffice");
		user4.setPassword(passwordEncoder.encode("frontoffice"));
		user4.setEmail("frontoffice@frontoffice.com");
		user4.setRoles(Arrays.asList(frontofficeRole));
		user4.setEnabled(true);
		user4.setConfirmed(true);

		final Role adminRole = roleRepository.findByName("ROLE_ADMIN");
		final User user5 = new User();
		user5.setFirstName("admin");
		user5.setLastName("admin");
		user5.setPassword(passwordEncoder.encode("admin"));
		user5.setEmail("admin@admin.com");
		user5.setRoles(Arrays.asList(adminRole));
		user5.setEnabled(true);
		user5.setConfirmed(true);

		userRepository.save(user);
		userRepository.save(user2);
		userRepository.save(user3);
		userRepository.save(user4);
		userRepository.save(user5);

		BankAccount bankAccount1 = new BankAccount("1", 1, new Date(111111L), 11111, user);
		BankAccount bankAccount2 = new BankAccount("2", 2, new Date(222222L), 22222, user);
		BankAccount bankAccount3 = new BankAccount("3", 3, new Date(333333L), 33333, user2);
		BankAccount bankAccount4 = new BankAccount("4", 4, new Date(444444L), 44444, user2);

		Portfolio portfolio1 = new Portfolio("1", user);
		Portfolio portfolio2 = new Portfolio("2", user2);
		Portfolio portfolio3 = new Portfolio("3", user2);
		Portfolio portfolio4 = new Portfolio("4", user2);
		portfolio1.setId(1L);
		portfolio2.setId(2L);
		portfolio3.setId(3L);
		portfolio4.setId(4L);

		bankAccountService.add(bankAccount1);
		bankAccountService.add(bankAccount2);
		bankAccountService.add(bankAccount3);
		bankAccountService.add(bankAccount4);

		portfolioService.add(portfolio1);
		portfolioService.add(portfolio2);
		portfolioService.add(portfolio3);
		portfolioService.add(portfolio4);

		daoService.addBankAccountToPortfolio(bankAccount1, portfolio1);
		daoService.addBankAccountToPortfolio(bankAccount2, portfolio1);
		daoService.addBankAccountToPortfolio(bankAccount3, portfolio2);

		daoService.addPortfolioToBankAccount(bankAccount4, portfolio2);
		daoService.addPortfolioToBankAccount(bankAccount4, portfolio3);
		daoService.addPortfolioToBankAccount(bankAccount4, portfolio4);

		daoService.removePortfolioFromBankAccount(bankAccount4, portfolio4);

		Entreprise entreprise1 = new Entreprise("entreprise1", 1, "desc1");
		Entreprise entreprise2 = new Entreprise("entreprise2", 2, "desc2");
		Entreprise entreprise3 = new Entreprise("entreprise3", 3, "desc3");

		Value value1 = new Value("value1", "isin1", "Action", entreprise1);
		Value value2 = new Value("value2", "isin2", "Action", entreprise1);
		Value value3 = new Value("value3", "isin3", "Action", entreprise2);
		Value value4 = new Value("value4", "isin4", "Action", entreprise3);

		MarketValue marketValue1 = new MarketValue(value1, 1.0);
		MarketValue marketValue2 = new MarketValue(value2, 2.0);
		MarketValue marketValue3 = new MarketValue(value3, 3.0);
		MarketValue marketValue4 = new MarketValue(value4, 4.0);

		entrepriseService.add(entreprise1);
		entrepriseService.add(entreprise2);
		entrepriseService.add(entreprise3);

		valueService.add(value1);
		valueService.add(value2);
		valueService.add(value3);
		valueService.add(value4);

		marketValueService.add(marketValue1);
		marketValueService.add(marketValue2);
		marketValueService.add(marketValue3);
		marketValueService.add(marketValue4);

		Order order1 = new MarketOrder(OperationType.BUY.getType(), 11, ValidityType.DAILY.getType(),
				Date.valueOf("2017-01-01"), marketValue1, portfolio1, bankAccount1);
		Order order2 = new LimitedPriceOrder(OperationType.BUY.getType(), 22, ValidityType.FAK.getType(),
				Date.valueOf("2017-1-2"), marketValue2, portfolio1, bankAccount1, 2.0);
		MarketOrder order3 = new MarketOrder(OperationType.SELL.getType(), 5, ValidityType.FAK.getType(),
				Date.valueOf("2017-01-03"), marketValue1, portfolio1, bankAccount1);
		Order order4 = new WithExecutionOnsetOrder(OperationType.SELL.getType(), 6, ValidityType.GTC.getType(),
				Date.valueOf("2017-01-04"), marketValue2, portfolio1, bankAccount1, 2.0);

		orderService.add(order1);
		orderService.add(order2);
		orderService.add(order3);
		orderService.add(order4);

		portfolio1.addOrder(order1);
		portfolio1.addOrder(order2);
		portfolio1.addOrder(order3);
		portfolio1.addOrder(order4);

		portfolioService.update(portfolio1);
		portfolioService.update(portfolio2);
		portfolioService.update(portfolio3);
		portfolioService.update(portfolio4);

		Execution execution1 = new Execution(5, 1.0);
		Execution execution2 = new Execution(5, 1.0);
		Execution execution3 = new Execution(2, 2.0);
		Execution execution4 = new Execution(2, 2.0);

		daoService.addExecution(order1, execution1);
		order1.reduceQuantity(execution1.getQuantity());
		daoService.addExecution(order2, execution2);
		order2.reduceQuantity(execution2.getQuantity());
		daoService.addExecution(order3, execution3);
		order3.reduceQuantity(execution3.getQuantity());
		daoService.addExecution(order4, execution4);
		order4.reduceQuantity(execution3.getQuantity());

		daoService.updateBankAccountBalance(bankAccount1, +10);

		orderService.update(order1);
		orderService.update(order2);
		orderService.update(order3);
		orderService.update(order4);

		alreadySetup = true;
	}

	@Transactional
	private final Privilege createPrivilegeIfNotFound(final String name) {
		Privilege privilege = privilegeRepository.findByName(name);
		if (privilege == null) {
			privilege = new Privilege(name);
			privilegeRepository.save(privilege);
		}
		return privilege;
	}

	@Transactional
	private final Role createRoleIfNotFound(final String name, final Collection<Privilege> privileges) {
		Role role = roleRepository.findByName(name);
		if (role == null) {
			role = new Role(name);
			role.setPrivileges(privileges);
			roleRepository.save(role);
		}
		return role;
	}

}
