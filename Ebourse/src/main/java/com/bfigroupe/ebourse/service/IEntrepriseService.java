package com.bfigroupe.ebourse.service;

import java.util.Collection;

import com.bfigroupe.ebourse.persistence.model.Entreprise;

public interface IEntrepriseService {
	
	Collection<Entreprise> getAll();

	Entreprise findByName(String name);
	
	public Entreprise findById(Long id);

	public void update(Entreprise Execution);
	
	void add(Entreprise entreprise);
	
	public void delete(Entreprise Execution);

	public void deleteById(Long id);

	boolean exists(Entreprise entreprise);

}
