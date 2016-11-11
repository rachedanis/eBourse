package com.bfigroupe.market.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bfigroupe.market.model.MarketValueOld;

public interface MarketRepositoryOld extends MongoRepository<MarketValueOld, String> {

	public MarketValueOld findByIsin(String isin);

	public List<MarketValueOld> findAll();

	public void deleteById(String id);

	@SuppressWarnings("unchecked")
	public MarketValueOld save(MarketValueOld marketValue);
}