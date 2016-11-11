package com.bfigroupe.ebourse.persistence.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bfigroupe.ebourse.persistence.model.Execution;

public interface ExecutionRepository extends JpaRepository<Execution, Long>{

	public List<Execution> findAll();

	public void deleteById(Long id);

}
