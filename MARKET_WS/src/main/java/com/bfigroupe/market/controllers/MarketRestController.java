package com.bfigroupe.market.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bfigroupe.market.controllers.util.GenericResponse;
import com.bfigroupe.market.model.Entreprise;
import com.bfigroupe.market.model.MarketValue;
import com.bfigroupe.market.model.Value;
import com.bfigroupe.market.repositories.EntrepriseRepository;
import com.bfigroupe.market.repositories.MarketRepository;
import com.bfigroupe.market.repositories.ValueRepository;;

@RestController
public class MarketRestController {

	@Autowired
	private MarketRepository repository;

	@Autowired
	private ValueRepository valueRepository;

	@Autowired
	private EntrepriseRepository entrepriseRepository;

	@RequestMapping(value = "/marketvalues/withstatus", method = RequestMethod.GET, headers = "Accept=application/json")
	public GenericResponse getMarketValues() {
		List<MarketValue> listOfMarketValues = repository.findAll();
		return new GenericResponse(listOfMarketValues);
	}

	@RequestMapping(value = "/values", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<Value> getValues() {
		List<Value> listOfValues = valueRepository.findAll();
		return listOfValues;
	}

	@RequestMapping(value = "/valuebyentrepriseName/{entrepriseName}", method = RequestMethod.GET, headers = "Accept=application/json")
	public Value getValueByEntrepriseName(@PathVariable("entrepriseName") String entrepriseName) {
		Value value = valueRepository.findByEntrepriseName(entrepriseName);
		return value;
	}

	@RequestMapping(value = "/entreprises", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<Entreprise> getEntreprises() {
		List<Entreprise> listOfEntreprises = entrepriseRepository.findAll();
		return listOfEntreprises;
	}

	@RequestMapping(value = "/entreprisebyname/{name}", method = RequestMethod.GET, headers = "Accept=application/json")
	public Entreprise getEntrepriseByIsin(@PathVariable("name") String name) {
		Entreprise en = entrepriseRepository.findByName(name);
		return en;
	}

	@RequestMapping(value = "/marketvalues", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<MarketValue> getMarketValuesNoStatus() {
		List<MarketValue> listOfMarketValues = repository.findAll();
		return listOfMarketValues;
	}

	@RequestMapping(value = "/valuebyisin/{isin}", method = RequestMethod.GET, headers = "Accept=application/json")
	public MarketValue getMarketValueByIsin(@PathVariable("isin") String isin) {
		MarketValue mv = repository.findByValueIsin(isin);
		return mv;
	}

	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/marketvalues", method = RequestMethod.POST, headers = "Accept=application/json")
	public void addMarketValue(@RequestBody MarketValue marketValue) {
		marketValue.setDate(new Date());
		Value value = valueRepository.findByIsin(marketValue.getValue().getIsin());
		marketValue.setValue(value);
		repository.save(marketValue);
	}

	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/marketvalues/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public void deleteMarketValueById(@PathVariable String id) {
		repository.deleteById(id);
	}

	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/marketvalues/{id}", method = RequestMethod.PUT)
	public void updateMarketValue(@PathVariable("id") String id, @RequestBody MarketValue marketValue) {
		MarketValue mv = repository.findOne(id);
		System.out.println(mv);
		System.out.println(marketValue);
		mv.setDate(new Date());
		mv.setCours(marketValue.getCours());
		repository.save(mv);
	}

	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/values", method = RequestMethod.POST, headers = "Accept=application/json")
	public void addValue(@RequestBody Value value) {
		Value v = valueRepository.findByIsin(value.getIsin());
		if (v != null)
			value.setId(v.getId());
		else {
			Entreprise entreprise = entrepriseRepository.findByName(value.getEntreprise().getName());
			value.setEntreprise(entreprise);
		}
		valueRepository.save(value);

	}

	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/values/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public void deleteValueById(@PathVariable String id) {
		valueRepository.deleteById(id);
	}

	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/values/{id}", method = RequestMethod.PUT)
	public void updateValue(@PathVariable("id") String id, @RequestBody Value value) {
		value.setId(id);
		valueRepository.save(value);
	}

	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/entreprises", method = RequestMethod.POST, headers = "Accept=application/json")
	public void addEntreprise(@RequestBody Entreprise en) {
		Entreprise e = entrepriseRepository.findByName(en.getName());
		if (e != null)
			en.setId(e.getId());
		entrepriseRepository.save(en);
	}

	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/entreprises/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public void deleteEntrepriseById(@PathVariable String id) {
		entrepriseRepository.deleteById(id);
	}

	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/entreprises/{id}", method = RequestMethod.PUT)
	public void updateEntreprise(@PathVariable("id") String id, @RequestBody Entreprise en) {
		en.setId(id);
		entrepriseRepository.save(en);
	}

}
