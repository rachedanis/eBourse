package com.bfigroupe.ebourse.persistence.dao;

import javax.transaction.Transactional;

import com.bfigroupe.ebourse.persistence.model.EditRequest;

@Transactional
public interface EditRequestRepository extends BaseRequestRepository<EditRequest> {

}
