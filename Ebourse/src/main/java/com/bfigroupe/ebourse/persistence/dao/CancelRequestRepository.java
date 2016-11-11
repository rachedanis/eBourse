package com.bfigroupe.ebourse.persistence.dao;

import javax.transaction.Transactional;

import com.bfigroupe.ebourse.persistence.model.CancelRequest;

@Transactional
public interface CancelRequestRepository extends BaseRequestRepository<CancelRequest> {

}
