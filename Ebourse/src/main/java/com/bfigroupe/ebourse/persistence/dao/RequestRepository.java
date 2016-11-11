package com.bfigroupe.ebourse.persistence.dao;

import javax.transaction.Transactional;

import com.bfigroupe.ebourse.persistence.model.Request;

@Transactional
public interface RequestRepository extends BaseRequestRepository<Request> {

}
