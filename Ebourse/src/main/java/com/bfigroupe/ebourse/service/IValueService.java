package com.bfigroupe.ebourse.service;

import java.util.Collection;

import com.bfigroupe.ebourse.persistence.model.Value;

public interface IValueService {

	public void add(Value value);

	public Collection<Value> getAll();

	public Value findByIsin(String isin);

	public Value findById(Long id);

	public void update(Value value);

	public void delete(Value value);

	public void deleteById(Long id);

	public boolean exists(Value value);

}
