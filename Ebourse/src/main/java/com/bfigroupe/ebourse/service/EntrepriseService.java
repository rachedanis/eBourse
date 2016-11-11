package com.bfigroupe.ebourse.service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bfigroupe.ebourse.persistence.dao.EntrepriseRepository;
import com.bfigroupe.ebourse.persistence.model.Entreprise;

@Service
@Transactional
public class EntrepriseService implements IEntrepriseService {

	@Autowired
	EntrepriseRepository repository;

	@Override
	public void add(Entreprise entreprise) {
		repository.save(entreprise);
	}

	@Override
	public Entreprise findByName(String name) {
		return repository.findByName(name);
	}

	@Override
	public boolean exists(Entreprise entreprise) {
		if (this.findByName(entreprise.getName()) == null)
			return false;
		return true;
	}

	@Override
	public Collection<Entreprise> getAll() {
		return repository.findAll();
	}

	@Override
	public Entreprise findById(Long id) {
		return repository.findOne(id);
	}

	@Override
	public void update(Entreprise execution) {
		repository.save(execution);
	}

	@Override
	public void delete(Entreprise execution) {
		repository.delete(execution);
	}

	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);
	}
}
