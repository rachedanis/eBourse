package com.bfigroupe.ebourse.service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bfigroupe.ebourse.persistence.dao.ExecutionRepository;
import com.bfigroupe.ebourse.persistence.model.Execution;

@Service
@Transactional
public class ExecutionService implements IExecutionService {

	@Autowired
	ExecutionRepository repository;

	@Override
	public Collection<Execution> getAll() {
		return repository.findAll();
	}

	@Override
	public Execution findById(Long id) {
		return repository.findOne(id);
	}

	@Override
	public void add(Execution execution) {
		repository.save(execution);
	}

	@Override
	public void update(Execution execution) {
		repository.save(execution);
	}

	@Override
	public void delete(Execution execution) {
		repository.delete(execution);
	}

	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

}