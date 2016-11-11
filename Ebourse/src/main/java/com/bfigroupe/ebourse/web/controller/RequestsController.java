package com.bfigroupe.ebourse.web.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bfigroupe.ebourse.persistence.model.Request;
import com.bfigroupe.ebourse.service.IDAOService;
import com.bfigroupe.ebourse.web.util.GenericResponse;

@Controller
public class RequestsController {

	@Autowired
	IDAOService daoService;

	@Autowired
	private MessageSource messages;

	@RequestMapping(value = "/requests")
	@PreAuthorize("hasAuthority('SERVICE_ACCESS_PRIVILEGE')")
	public String requests(Model model, final Locale locale) {
		List<Request> requests = null;
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		requests = daoService.findRequestsByUserEmail(email);
		if (requests == null)
			return "redirect:/requests?message=" + messages.getMessage("error.requests.notFound", null, locale);
		model.addAttribute("RequestsByUser", requests);
		return "requests";
	}

	@PreAuthorize("hasAuthority('SERVICE_ACCESS_PRIVILEGE')")
	@RequestMapping(value = "/requests/delete/{id}", method = RequestMethod.GET, headers = "Accept=application/json", produces = "application/json")
	@ResponseBody
	public GenericResponse deleteRequest(final Locale locale, final Model model, @PathVariable final String id) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		daoService.deleteRequestByIdAndEmail(Long.parseLong(id), email);
		return new GenericResponse(messages.getMessage("message.request.delete.success", null, locale), "empty");
	}

}
