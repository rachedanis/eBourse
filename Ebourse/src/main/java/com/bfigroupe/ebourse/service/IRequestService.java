package com.bfigroupe.ebourse.service;

import java.util.Date;
import java.util.List;

import com.bfigroupe.ebourse.persistence.model.Request;
import com.bfigroupe.ebourse.persistence.model.RequestType;

public interface IRequestService {
	
	public void add(Request request);

	public List<Request> getAll();

	public List<Request> findByType(RequestType type);
	
	public List<Request> findByDate(Date date);

	public Request findById(Long id);

	public void update(Request request);

	public void delete(Request request);

	public void deleteById(Long id);

	public boolean exists(Request request);
}
