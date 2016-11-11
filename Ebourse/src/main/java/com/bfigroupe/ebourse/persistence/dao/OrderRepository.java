package com.bfigroupe.ebourse.persistence.dao;

import javax.transaction.Transactional;

import com.bfigroupe.ebourse.persistence.model.Order;

@Transactional
public interface OrderRepository extends BaseOrderRepository<Order> {

}
