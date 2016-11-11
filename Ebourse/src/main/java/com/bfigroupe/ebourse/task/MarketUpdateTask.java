package com.bfigroupe.ebourse.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bfigroupe.ebourse.persistence.model.MarketValue;
import com.bfigroupe.ebourse.rest.client.MarketValueRestClientRepository;
import com.bfigroupe.ebourse.rest.client.util.GenericResponse;
import com.bfigroupe.ebourse.service.IDBUpdateService;

@Service
@Transactional
public class MarketUpdateTask {

	 @Autowired
	 IDBUpdateService dbUpdateService;
	
	 @Scheduled(cron = "${updateDB.cron.expression}")
	 public void updateMarketValues() {
	 	List<MarketValue> marketValues;
	 	GenericResponse response = MarketValueRestClientRepository.getMarketValuesWithStatus();
	 	if (response.getStatus().equals("ok")) {
	 		marketValues = response.getMessages();
	 		dbUpdateService.updateMarketValues(marketValues);
	 	} else
	 		System.out.println("Enable to get data - Server status :"+response.getStatus());
	 }
}
