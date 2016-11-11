package com.bfigroupe.ebourse.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bfigroupe.ebourse.persistence.dao.CancelRequestRepository;
import com.bfigroupe.ebourse.persistence.dao.EditRequestRepository;
import com.bfigroupe.ebourse.persistence.dao.RequestRepository;
import com.bfigroupe.ebourse.persistence.model.Request;
import com.bfigroupe.ebourse.persistence.model.RequestType;

@Service
@Transactional
public class RequestService implements IRequestService {

	@Autowired
	RequestRepository repository;

	@Autowired
	CancelRequestRepository cancelReqRepository;

	@Autowired
	EditRequestRepository editReqRepository;

	@Override
	public void add(Request request) {
		repository.save(request);
	}

	@Override
	public List<Request> getAll() {
		return repository.findAll();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Request> findByType(RequestType type) {
		List<?> requests = null;
		switch (type) {
		case CANCEL:
			requests = cancelReqRepository.findAll();
			break;
		case EDIT:
			requests = editReqRepository.findAll();
			break;
		default:
			break;
		}
		return (List<Request>) requests;
	}

	@Override
	public List<Request> findByDate(Date date) {
		return repository.findByDate(date);
	}

	@Override
	public Request findById(Long id) {
		return repository.findOne(id);
	}

	@Override
	public void update(Request request) {
		repository.save(request);
	}

	@Override
	public void delete(Request request) {
		repository.delete(request);
	}

	@Override
	public void deleteById(Long id) {
		repository.delete(id);
	}

	@Override
	public boolean exists(Request request) {
		if (this.findById(request.getId()) != null)
			return true;
		return false;
	}

}