package com.bfigroupe.ebourse.persistence.dao;

import javax.transaction.Transactional;

import com.bfigroupe.ebourse.persistence.model.WithExecutionOnsetOrder;

@Transactional
public interface WithExecutionOnsetOrderRepository extends BaseOrderRepository<WithExecutionOnsetOrder> {

}