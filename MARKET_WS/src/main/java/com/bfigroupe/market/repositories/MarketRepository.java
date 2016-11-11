package com.bfigroupe.market.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bfigroupe.market.model.MarketValue;

public interface MarketRepository extends MongoRepository<MarketValue, String> {

	public List<MarketValue> findAll();

	public void deleteById(String id);

	@SuppressWarnings("unchecked")
	public MarketValue save(MarketValue marketValue);

	public MarketValue findByValueIsin(String isin);
}