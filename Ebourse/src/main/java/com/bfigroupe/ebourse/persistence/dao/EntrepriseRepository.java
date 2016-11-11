package com.bfigroupe.ebourse.persistence.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bfigroupe.ebourse.persistence.model.Entreprise;

public interface EntrepriseRepository extends JpaRepository<Entreprise, Long>{

	public List<Entreprise> findAll();

	public Entreprise findByName(String name);

	public void deleteById(Long id);

}
