package com.bfigroupe.ebourse.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bfigroupe.ebourse.persistence.model.BankAccount;
import com.bfigroupe.ebourse.persistence.model.MarketValue;
import com.bfigroupe.ebourse.persistence.model.Portfolio;
import com.bfigroupe.ebourse.rest.client.MarketValueRestClientRepository;
import com.bfigroupe.ebourse.rest.client.util.GenericResponse;
import com.bfigroupe.ebourse.service.IDAOService;
import com.bfigroupe.ebourse.web.error.MarketValueException;

@Controller
public class MarketValuesController {

	@Autowired
	IDAOService daoService;

	@Autowired
	private MessageSource messages;

	private List<MarketValue> marketValues;

	// GET new market values
	@PreAuthorize("hasAuthority('SERVICE_ACCESS_PRIVILEGE')")
	@RequestMapping(value = "/marketvalues")
	public String marketvalues(final Model model, final Locale locale) {
		updateMarketValues();
		model.addAttribute("marketvalues", marketValues);
		if (marketValues == null)
			return "redirect:/marketvalues?message=" + messages.getMessage("error.marketvalues.notFound", null, locale);
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
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

		return "marketvalues";
	}

	// GET market values by ISIN
	@PreAuthorize("hasAuthority('SERVICE_ACCESS_PRIVILEGE')")
	@RequestMapping(value = "/marketvalues/isin/{isin}", method = RequestMethod.GET, headers = "Accept=application/json", produces = "application/json")
	@ResponseBody
	public MarketValue getMarketValueByIsin(@PathVariable final String isin) {
		MarketValue marketValueSearched = null;
		if (marketValues == null)
			throw new MarketValueException("error.marketValues.null");
		for (MarketValue mv : marketValues)
			if (mv.getValue().getIsin().equals(isin))
				marketValueSearched = mv;
		if (marketValueSearched == null) {
			throw new MarketValueException("error.marketValue.notFound");
		}
		return marketValueSearched;
	}

	@PreAuthorize("hasAuthority('SERVICE_ACCESS_PRIVILEGE')")
	@RequestMapping(value = "/marketvalues/minified", method = RequestMethod.GET, headers = "Accept=application/json", produces = "application/json")
	@ResponseBody
	public List<HashMap<String, String>> getMarketValuesMinified() {
		List<HashMap<String, String>> marketvalues = new ArrayList<HashMap<String, String>>();
		if (marketValues == null)
			throw new MarketValueException("error.marketValues.notFound");
		for (MarketValue mv : marketValues) {
			HashMap<String, String> marketValue = new HashMap<String, String>();
			marketValue.put("isin", mv.getValue().getIsin());
			marketValue.put("cours", String.valueOf(mv.getCours()));
			marketValue.put("date", String.valueOf(mv.getDate()));
			marketvalues.add(marketValue);
		}
		return marketvalues;
	}

	@PreAuthorize("hasAuthority('SERVICE_ACCESS_PRIVILEGE')")
	@RequestMapping(value = "/marketvalues/minified/isin/{isin}", method = RequestMethod.GET, headers = "Accept=application/json", produces = "application/json")
	@ResponseBody
	public HashMap<String, String> getMarketValuesMinifiedByIsin(@PathVariable final String isin) {
		HashMap<String, String> marketvalueMinified = null;
		for (HashMap<String, String> mvMinified : this.getMarketValuesMinified())
			if (mvMinified.get("isin").equals(isin))
				marketvalueMinified = mvMinified;
		if (marketvalueMinified == null)
			throw new MarketValueException("error.marketValue.notFound");
		return marketvalueMinified;
	}

	@Scheduled(cron = "${update.cron.expression}")
	public void updateMarketValues() {
		GenericResponse response = MarketValueRestClientRepository.getMarketValuesWithStatus();
		if (response.getStatus().equals("ok")) {
			marketValues = response.getMessages();
			System.out.println("MARKETVALUES UPDATED IN CONTROLLER");
		} else
			throw new MarketValueException("error.bourse.offline");
	}
}
