package com.bfigroupe.ebourse.web.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bfigroupe.ebourse.persistence.model.BankAccount;
import com.bfigroupe.ebourse.persistence.model.User;
import com.bfigroupe.ebourse.service.IBankAccountService;
import com.bfigroupe.ebourse.service.IDAOService;
import com.bfigroupe.ebourse.service.IUserService;
import com.bfigroupe.ebourse.web.dto.BankAccountDto;
import com.bfigroupe.ebourse.web.dto.PortfolioDto;
import com.bfigroupe.ebourse.web.error.BankAccountException;
import com.bfigroupe.ebourse.web.error.UserException;
import com.bfigroupe.ebourse.web.util.GenericResponse;

@Controller
public class FrontOfficeController {

	@Autowired
	private MessageSource messages;

	@Autowired
	private IUserService userService;

	@Autowired
	private IBankAccountService bankAccountService;

	@Autowired
	private IDAOService daoService;

	private List<User> users;

	// GET Users
	@PreAuthorize("hasAuthority('ENABLE_USERS_PRIVILEGE')")
	@RequestMapping(value = "/frontoffice")
	public String frontOffice(final Model model, final Locale locale) {
		users = userService.getAll();
		if (users == null)
			return "redirect:/frontoffice?message=" + messages.getMessage("error.frontoffice.notFound", null, locale);
		for (Iterator<User> iterator = users.iterator(); iterator.hasNext();) {
			User user = iterator.next();
			if (!user.isConfirmed()) {
				iterator.remove();
			}
		}
		model.addAttribute("Users", users);
		return "frontoffice";
	}

	// API
	@RequestMapping(value = "/frontoffice/deleteuser/{id}", method = RequestMethod.GET, headers = "Accept=application/json", produces = "application/json")
	@PreAuthorize("hasAuthority('ENABLE_USERS_PRIVILEGE')")
	@ResponseBody
	public GenericResponse removeUser(final Locale locale, @PathVariable final String id) {
		final User user = new User();
		user.setId(Long.parseLong(id));
		if (user.isEnabled())
			throw new UserException("error.user.enabled");
		userService.deleteUser(user);
		return new GenericResponse(messages.getMessage("message.user.delete.success", null, locale), "empty");
	}

	@RequestMapping(value = "/frontoffice/enableuser/{id}", method = RequestMethod.GET, headers = "Accept=application/json", produces = "application/json")
	@PreAuthorize("hasAuthority('ENABLE_USERS_PRIVILEGE')")
	@ResponseBody
	public GenericResponse enableUser(final Model model, final Locale locale, @PathVariable final String id) {
		User user = userService.findById(Long.parseLong(id));
		if (user.isEnabled())
			throw new UserException("error.user.aleardyEnabled");
		user.setEnabled(true);
		userService.saveRegisteredUser(user);
		return new GenericResponse(messages.getMessage("message.user.enable.success", null, locale), "empty");
	}

	@RequestMapping(value = "/frontoffice/addBankAccount", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json")
	@PreAuthorize("hasAuthority('ADD_BANKACCOUNTS_PRIVILEGE')")
	@ResponseBody
	public GenericResponse addBankAccount(final Locale locale, @Valid BankAccountDto bankAccountDto,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return new GenericResponse(bindingResult.getFieldErrors(), bindingResult.getGlobalErrors());
		daoService.createBankAccount(bankAccountDto);
		return new GenericResponse(messages.getMessage("message.bankAccount.add.success", null, locale), "empty");
	}

	@RequestMapping(value = "/frontoffice/addPortfolio", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json")
	@PreAuthorize("hasAuthority('ADD_PORTFOLIOS_PRIVILEGE')")
	@ResponseBody
	public GenericResponse addPortfolio(final Locale locale, @Valid PortfolioDto portfolioDto,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return new GenericResponse(bindingResult.getFieldErrors(), bindingResult.getGlobalErrors());
		daoService.createPortfolio(portfolioDto);
		return new GenericResponse(messages.getMessage("message.portfolio.add.success", null, locale), "empty");
	}

	@PreAuthorize("hasAuthority('ADD_BANKACCOUNTS_PRIVILEGE')")
	@RequestMapping(value = "/frontoffice/userBankAccounts/{id}", method = RequestMethod.GET, headers = "Accept=application/json", produces = "application/json")
	@ResponseBody
	public List<String> getBankAccountsNumbers(final Locale locale, final Model model, @PathVariable final String id) {
		List<String> bankAccountsNumbers = new ArrayList<String>();
		String email = userService.findById(Long.parseLong(id)).getEmail();
		List<BankAccount> bankAccounts = bankAccountService.findByOwnerEmail(email);
		System.out.println(bankAccounts);
		for (BankAccount ba : bankAccounts) {
			bankAccountsNumbers.add(ba.getAccountNumber());
		}
		if (bankAccountsNumbers.isEmpty())
			throw new BankAccountException("error.bankAccounts.notFound");
		return bankAccountsNumbers;
	}
}
