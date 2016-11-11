package com.bfigroupe.ebourse.persistence.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bfigroupe.ebourse.persistence.model.Value;

public interface ValueRepository extends JpaRepository<Value, Long>{
	
	public List<Value> findAll();

	public Value findByIsin(String isin);

}
