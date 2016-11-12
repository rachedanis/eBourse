package com.bfigroupe.ebourse.service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bfigroupe.ebourse.persistence.dao.ValueRepository;
import com.bfigroupe.ebourse.persistence.model.Value;

@Service
@Transactional
public class ValueService implements IValueService {

	@Autowired
	ValueRepository repository;

	@Override
	public void add(Value value) {
		repository.save(value);
	}

	@Override
	public boolean exists(Value value) {
		if (repository.findByIsin(value.getIsin()) == null)
			return false;
		return true;
	}

	@Override
	public Collection<Value> getAll() {
		return repository.findAll();
	}

	@Override
	public Value findByIsin(String isin) {
		return repository.findByIsin(isin);
	}

	@Override
	public Value findById(Long id) {
		return repository.findOne(id);
	}

	@Override
	public void update(Value value) {
		repository.save(value);
	}

	@Override
	public void delete(Value value) {
		repository.delete(value);
	}

	@Override
	public void deleteById(Long id) {
		repository.delete(id);
	}

}