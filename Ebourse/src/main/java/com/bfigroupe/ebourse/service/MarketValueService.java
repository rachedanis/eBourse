package com.bfigroupe.ebourse.service;

import java.util.List;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bfigroupe.ebourse.persistence.dao.MarketValueRepository;
import com.bfigroupe.ebourse.persistence.model.MarketValue;
import com.bfigroupe.ebourse.persistence.model.Value;

@Service
@Transactional
public class MarketValueService implements IMarketValueService {

	@Autowired
	MarketValueRepository repository;

	@Override
	public void add(MarketValue marketValue) {
		repository.save(marketValue);
	}

	@Override
	public boolean exists(MarketValue marketValue) {
		if (repository.findOne(marketValue.getId()) == null)
			return false;
		return true;
	}

	@Override
	public List<MarketValue> getAll() {
		return repository.findAll();
	}

	@Override
	public List<MarketValue> findByValue(Value value) {
		return repository.findByValue(value);
	}

	@Override
	public List<MarketValue> findByValueIsin(String isin) {
		return repository.findByValueIsin(isin);
	}
	
	@Override
	public List<MarketValue> findByDate(Date date) {
		return repository.findByDate(date);
	}
	
	@Override
	public MarketValue findByIsinAndDate(String isin, Date date) {
		return repository.findByValueIsinAndDate(isin,date);
	}

	@Override
	public MarketValue findById(Long id) {
		return repository.findOne(id);
	}

	@Override
	public void update(MarketValue marketValue) {
		repository.save(marketValue);
	}

	@Override
	public void delete(MarketValue marketValue) {
		repository.delete(marketValue);
	}

	@Override
	public void deleteById(Long id) {
		repository.delete(id);
	}

	@Override
	public MarketValue findMostRecentMarketValueByIsinAndDate(String isin, Date date) {
		return repository.findTopByValueIsinAndDateOrderByDateDesc(isin, date);
	}

	@Override
	public MarketValue findMostRecentMarketValueByIsin(String isin) {
		return repository.findTopByValueIsinOrderByDateDesc(isin);
	}

}