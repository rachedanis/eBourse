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

import com.bfigroupe.ebourse.persistence.model.Execution;
import com.bfigroupe.ebourse.service.IDAOService;

@Controller
public class ExecutionsController {

	@Autowired
	IDAOService daoService;

	@Autowired
	private MessageSource messages;

	@RequestMapping(value = "/executions")
	@PreAuthorize("hasAuthority('SERVICE_ACCESS_PRIVILEGE')")
	public String executions(Model model, final Locale locale) {
		List<Execution> executions = null;
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		executions = daoService.findExecutionsByUserEmail(email);
		if (executions == null)
			return "redirect:/executions?message=" + messages.getMessage("error.executions.notFound", null, locale);
		model.addAttribute("ExecutionsByUser", executions);
		return "executions";
	}

}
