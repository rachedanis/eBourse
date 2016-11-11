package com.bfigroupe.ebourse.web.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bfigroupe.ebourse.persistence.model.BankAccount;
import com.bfigroupe.ebourse.service.IDAOService;

@Controller
public class BankAccountsController {

	@Autowired
	IDAOService daoService;

	@Autowired
	private MessageSource messages;

	@RequestMapping(value = "/bankaccounts")
	@PreAuthorize("hasAuthority('SERVICE_ACCESS_PRIVILEGE')")
	public String bankaccounts(Model model, final Locale locale) {
		List<BankAccount> bankAccounts = null;
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		bankAccounts = daoService.findBankAccountsByUserEmail(email);
		if (bankAccounts == null)
			return "redirect:/bankaccounts?message=" + messages.getMessage("error.bankAccounts.notFound", null, locale);
		model.addAttribute("BankAccountsByUser", bankAccounts);
		return "bankaccounts";
	}
}
