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

import com.bfigroupe.ebourse.persistence.model.Portfolio;
import com.bfigroupe.ebourse.service.IDAOService;

@Controller
public class PortfoliosController {

	@Autowired
	IDAOService daoService;

	@Autowired
	private MessageSource messages;

	@RequestMapping(value = "/portfolios")
	@PreAuthorize("hasAuthority('SERVICE_ACCESS_PRIVILEGE')")
	public String portfolios(Model model, final Locale locale) {
		List<Portfolio> portfolios = null;
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		portfolios = daoService.findPortfoliosByUserEmail(email);
		if (portfolios == null)
			return "redirect:/portfolios?message=" + messages.getMessage("error.portfolios.notFound", null, locale);
		for (Portfolio pf : portfolios)
			pf.setValorization(daoService.valorizePortfolio(pf.getNumber()));
		model.addAttribute("PortfoliosByUser", portfolios);
		return "portfolios";
	}
}
