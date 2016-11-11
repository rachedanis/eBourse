package com.bfigroupe.ebourse.web.controller;

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

import com.bfigroupe.ebourse.persistence.model.Order;
import com.bfigroupe.ebourse.persistence.model.Request;
import com.bfigroupe.ebourse.persistence.model.RequestState;
import com.bfigroupe.ebourse.service.IDAOService;
import com.bfigroupe.ebourse.service.IOrderService;
import com.bfigroupe.ebourse.service.IRequestService;
import com.bfigroupe.ebourse.web.dto.EvalRequestDto;
import com.bfigroupe.ebourse.web.dto.ExecutionDto;
import com.bfigroupe.ebourse.web.util.GenericResponse;

@Controller
public class BackOfficeController {

	@Autowired
	private IOrderService orderService;

	@Autowired
	private IRequestService requestService;

	@Autowired
	private IDAOService daoService;

	@Autowired
	private MessageSource messages;

	@PreAuthorize("hasAuthority('UPDATE_ORDERS_PRIVILEGE')")
	@RequestMapping(value = "/backoffice")
	public String backoffice(final Model model, final Locale locale) {
		List<Order> orders = orderService.findAll();
		List<Request> requests = requestService.getAll();
		if (orders == null && requests == null)
			return "redirect:/portfolios?message="
					+ messages.getMessage("error.ordersAndRequests.notFound", null, locale);
		model.addAttribute("Requests", requests);
		model.addAttribute("Orders", orders);
		return "backoffice";
	}

	// API

	// GET Orders
	@PreAuthorize("hasAuthority('UPDATE_ORDERS_PRIVILEGE')")
	@RequestMapping(value = "/backoffice/orders", method = RequestMethod.GET, headers = "Accept=application/json", produces = "application/json")
	@ResponseBody
	public List<Order> getOrders() {
		List<Order> orders;
		orders = orderService.findAll();
		return orders;
	}

	// Remove a Order
	@RequestMapping(value = "/backoffice/deleteOrder/{id}", method = RequestMethod.GET, headers = "Accept=application/json", produces = "application/json")
	@PreAuthorize("hasAuthority('UPDATE_ORDERS_PRIVILEGE')")
	@ResponseBody
	public GenericResponse deleteOrder(final Locale locale, @PathVariable final String id) {
		Order order = orderService.findById(Long.parseLong(id));
		daoService.forcedDeleteOrder(order);
		return new GenericResponse(messages.getMessage("message.order.delete.success", null, locale), "empty");
	}

	@RequestMapping(value = "/backoffice/executeOrder", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json")
	@PreAuthorize("hasAuthority('UPDATE_ORDERS_PRIVILEGE')")
	@ResponseBody
	public GenericResponse executeOrder(final Locale locale, @Valid ExecutionDto executionDto,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return new GenericResponse(bindingResult.getFieldErrors(), bindingResult.getGlobalErrors());
		daoService.executeOrder(executionDto);
		return new GenericResponse(messages.getMessage("message.order.execute.success", null, locale), "empty");
	}

	@RequestMapping(value = "/backoffice/evaluateRequest", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json")
	@PreAuthorize("hasAuthority('UPDATE_REQUESTS_PRIVILEGE')")
	@ResponseBody
	public GenericResponse evaluate(final Locale locale, @Valid EvalRequestDto evalRequestDto,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return new GenericResponse(bindingResult.getFieldErrors(), bindingResult.getGlobalErrors());
		daoService.evaluateRequest(evalRequestDto);
		return new GenericResponse(messages.getMessage("message.request.evaluate.success", null, locale), "empty");
	}

	@RequestMapping(value = "/backoffice/deleteRequest/{id}", method = RequestMethod.GET, headers = "Accept=application/json", produces = "application/json")
	@PreAuthorize("hasAuthority('UPDATE_REQUESTS_PRIVILEGE')")
	@ResponseBody
	public GenericResponse deleteRequest(final Locale locale, @PathVariable final String id) {
		Request request = requestService.findById(Long.parseLong(id));
		daoService.forcedRequestDelete(request);
		return new GenericResponse(messages.getMessage("message.request.delete.success", null, locale), "empty");
	}

	@RequestMapping(value = "/backoffice/sendRequest/{id}", method = RequestMethod.GET, headers = "Accept=application/json", produces = "application/json")
	@PreAuthorize("hasAuthority('UPDATE_REQUESTS_PRIVILEGE')")
	@ResponseBody
	public GenericResponse sendRequest(final Locale locale, @PathVariable final String id) {
		Request request = requestService.findById(Long.parseLong(id));
		daoService.updateRequestStatus(request, RequestState.SENT);
		return new GenericResponse(messages.getMessage("message.request.sent.success", null, locale), "empty");
	}
}
