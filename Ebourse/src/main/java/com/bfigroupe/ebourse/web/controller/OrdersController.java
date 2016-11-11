package com.bfigroupe.ebourse.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bfigroupe.ebourse.persistence.model.BankAccount;
import com.bfigroupe.ebourse.persistence.model.Order;
import com.bfigroupe.ebourse.persistence.model.Portfolio;
import com.bfigroupe.ebourse.service.IDAOService;
import com.bfigroupe.ebourse.web.dto.EditRequestDto;
import com.bfigroupe.ebourse.web.dto.OrderDto;
import com.bfigroupe.ebourse.web.util.GenericResponse;

@Controller
public class OrdersController {

	@Autowired
	IDAOService daoService;

	@Autowired
	private MessageSource messages;

	// Generate Page
	@RequestMapping(value = "/orders")
	@PreAuthorize("hasAuthority('SERVICE_ACCESS_PRIVILEGE')")
	public String orders(Model model, final Locale locale) {
		List<Order> orders = new ArrayList<Order>();
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		orders = daoService.findOrdersByUserEmail(email);
		if (orders == null)
			return "redirect:/orders?message=" + messages.getMessage("error.orders.notFound", null, locale);
		model.addAttribute("OrdersByUser", orders);
		model.addAttribute("editRequestDto", new EditRequestDto());
		List<Portfolio> portfolios = daoService.findPortfoliosByUserEmail(email);

		HashMap<String, List<String>> securedPortfolios = new HashMap<String, List<String>>();
		List<String> numbers = new ArrayList<String>();
		for (Portfolio portfolio : portfolios) {
			numbers = new ArrayList<String>();
			for (BankAccount ba : portfolio.getBankAccounts())
				numbers.add(ba.getAccountNumber());
			securedPortfolios.put(portfolio.getNumber(), numbers);
		}

		model.addAttribute("PortfoliosByUser", securedPortfolios);
		return "orders";
	}

	// API
	@RequestMapping(value = "/orders/edit", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json")
	@PreAuthorize("hasAuthority('SERVICE_ACCESS_PRIVILEGE')")
	@ResponseBody
	public GenericResponse editOrder(final Locale locale, Model model, @Valid EditRequestDto editRequestDto,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return new GenericResponse(bindingResult.getFieldErrors(), bindingResult.getGlobalErrors());
		daoService.createEditRequest(editRequestDto);
		return new GenericResponse(messages.getMessage("message.edit.add.success", null, locale), "empty");
	}

	@RequestMapping(value = "/orders/add", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json")
	@PreAuthorize("hasAuthority('SERVICE_ACCESS_PRIVILEGE')")
	@ResponseBody
	public GenericResponse addOrder(final Locale locale, Model model, @Valid OrderDto orderDto,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return new GenericResponse(bindingResult.getFieldErrors(), bindingResult.getGlobalErrors());
		daoService.createNewOrder(orderDto);
		return new GenericResponse(messages.getMessage("message.order.add.success", null, locale), "empty");
	}

	@PreAuthorize("hasAuthority('SERVICE_ACCESS_PRIVILEGE')")
	@RequestMapping(value = "/orders/cancel/{id}", method = RequestMethod.GET, headers = "Accept=application/json", produces = "application/json")
	@ResponseBody
	public GenericResponse cancelOrder(final Locale locale, final Model model, @PathVariable final String id) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		daoService.createCancelRequestByOrderIdAndEmail(Long.parseLong(id), email);
		return new GenericResponse(messages.getMessage("message.cancel.add.success", null, locale), "empty");
	}
}
