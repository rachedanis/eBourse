package com.bfigroupe.market.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bfigroupe.market.model.Entreprise;

public interface EntrepriseRepository extends MongoRepository<Entreprise, String> {

	public Entreprise findByName(String name);

	public List<Entreprise> findAll();

	public void deleteById(String id);

	@SuppressWarnings("unchecked")
	public Entreprise save(Entreprise entreprise);
}