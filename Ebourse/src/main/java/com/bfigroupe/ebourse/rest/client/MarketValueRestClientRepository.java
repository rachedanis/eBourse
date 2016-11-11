package com.bfigroupe.ebourse.rest.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.bfigroupe.ebourse.persistence.model.MarketValue;
import com.bfigroupe.ebourse.rest.client.util.GenericResponse;

@Component
public final class MarketValueRestClientRepository {
	private static final String GET_MV_URL = "http://localhost:9000/marketvalues/withstatus";
	private static final RestTemplate restTemplate = new RestTemplate();

	public static GenericResponse getMarketValuesWithStatus() {
		return restTemplate.getForObject(GET_MV_URL, GenericResponse.class);
	}

	public static List<MarketValue> getMarketValues() {
		List<MarketValue> marketValues = new ArrayList<MarketValue>();
		GenericResponse response = MarketValueRestClientRepository.getMarketValuesWithStatus();
		if (response.getStatus().equals("ok")) {
			marketValues = response.getMessages();
			System.out.println("MARKETVALUES UPDATED");
		} else
			System.out.println("Enable to get data - Server status :" + response.getStatus());
		return marketValues;
	}

	public static MarketValue getCurrentMarketValueByIsin(String isin) {
		List<MarketValue> marketValues = MarketValueRestClientRepository.getMarketValues();
		for (MarketValue mv : marketValues)
			if (mv.getValue().getIsin().equals(isin))
				return mv;
		return null;
	}
}
