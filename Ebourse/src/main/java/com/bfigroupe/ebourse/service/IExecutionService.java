package com.bfigroupe.ebourse.service;

import java.util.Collection;

import com.bfigroupe.ebourse.persistence.model.Execution;

public interface IExecutionService {

	public Collection<Execution> getAll();

	public Execution findById(Long id);

	public void add(Execution Execution);

	public void update(Execution Execution);

	public void delete(Execution Execution);

	public void deleteById(Long id);
		
}
