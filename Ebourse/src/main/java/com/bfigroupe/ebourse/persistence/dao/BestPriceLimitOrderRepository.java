package com.bfigroupe.ebourse.persistence.dao;

import javax.transaction.Transactional;

import com.bfigroupe.ebourse.persistence.model.BestPriceLimitOrder;

@Transactional
public interface BestPriceLimitOrderRepository extends BaseOrderRepository<BestPriceLimitOrder> {

}