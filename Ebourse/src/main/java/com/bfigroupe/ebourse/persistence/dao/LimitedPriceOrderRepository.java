package com.bfigroupe.ebourse.persistence.dao;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import com.bfigroupe.ebourse.persistence.model.LimitedPriceOrder;

@Transactional
public interface LimitedPriceOrderRepository extends BaseOrderRepository<LimitedPriceOrder> {
	
	List<LimitedPriceOrder> findByCreationBetween(Date startDate,Date endDate);

}
