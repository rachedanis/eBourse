package com.bfigroupe.ebourse.persistence.dao;

import javax.transaction.Transactional;

import com.bfigroupe.ebourse.persistence.model.MarketOrder;

@Transactional
public interface MarketOrderRepository extends BaseOrderRepository<MarketOrder> {

}
