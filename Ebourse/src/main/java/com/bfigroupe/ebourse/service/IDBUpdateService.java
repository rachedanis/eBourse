package com.bfigroupe.ebourse.service;

import java.util.List;

import com.bfigroupe.ebourse.persistence.model.MarketValue;

public interface IDBUpdateService {
	public void updateMarketValues(List<MarketValue> marketvalues);
}
