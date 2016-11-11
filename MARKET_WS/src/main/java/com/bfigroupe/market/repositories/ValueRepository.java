package com.bfigroupe.market.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bfigroupe.market.model.Value;

public interface ValueRepository extends MongoRepository<Value, String> {

	public Value findByIsin(String isin);
	
	public Value findByEntrepriseName(String entrepriseName);

	public List<Value> findAll();

	public void deleteById(String id);

	@SuppressWarnings("unchecked")
	public Value save(Value value);

	public Value findByName(String name);
}