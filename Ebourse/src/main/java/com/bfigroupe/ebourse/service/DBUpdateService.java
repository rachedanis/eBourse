package com.bfigroupe.ebourse.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bfigroupe.ebourse.persistence.dao.EntrepriseRepository;
import com.bfigroupe.ebourse.persistence.dao.MarketValueRepository;
import com.bfigroupe.ebourse.persistence.dao.ValueRepository;
import com.bfigroupe.ebourse.persistence.model.Entreprise;
import com.bfigroupe.ebourse.persistence.model.MarketValue;
import com.bfigroupe.ebourse.persistence.model.Value;

@Service
@Transactional
public class DBUpdateService implements IDBUpdateService {

	@Autowired
	MarketValueRepository repository;

	@Autowired
	ValueRepository vRepository;

	@Autowired
	EntrepriseRepository eRepository;

	@Autowired
	IValueService valueService;

	@Override
	public void updateMarketValues(List<MarketValue> marketvalues) {
		MarketValue marketValue;
		for (MarketValue mv : marketvalues) {
			marketValue = repository.findByValueIsinAndDate(mv.getValue().getIsin(), mv.getDate());
			if (marketValue == null) {			
				System.out.println("*********** UPDATING ***********");
				Value value = mv.getValue();
				String name = value.getEntreprise().getName();
				Entreprise e = eRepository.findByName(name);
				if (e == null) {
					eRepository.save(value.getEntreprise());
					e = eRepository.findByName(value.getEntreprise().getName());
				}
				Value v = vRepository.findByIsin(value.getIsin());
				if (v == null) {
					value.setEntreprise(e);
					vRepository.save(value);
					v = vRepository.findByIsin(value.getIsin());
				}
				mv.setValue(v);
				marketValue = mv;
				repository.save(marketValue);
			}
		}
	}
}
