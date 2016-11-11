package com.bfigroupe.ebourse.service;

import java.util.List;
import java.util.Date;

import com.bfigroupe.ebourse.persistence.model.MarketValue;
import com.bfigroupe.ebourse.persistence.model.Value;

public interface IMarketValueService {

	void add(MarketValue marketValue);

	List<MarketValue> getAll();

	List<MarketValue> findByValue(Value value);

	List<MarketValue> findByValueIsin(String isin);

	List<MarketValue> findByDate(Date date);

	MarketValue findByIsinAndDate(String isin, Date date);

	public MarketValue findMostRecentMarketValueByIsinAndDate(String isin, Date date);

	public MarketValue findMostRecentMarketValueByIsin(String isin);
	
	public MarketValue findById(Long id);

	public void update(MarketValue marketValue);

	public void delete(MarketValue marketValue);

	public void deleteById(Long id);

	boolean exists(MarketValue marketValue);
}
