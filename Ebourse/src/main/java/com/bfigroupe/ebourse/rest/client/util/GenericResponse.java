package com.bfigroupe.ebourse.rest.client.util;

import java.util.List;

import com.bfigroupe.ebourse.persistence.model.MarketValue;

public class GenericResponse {

	private String status; // ok / offline / internalError / empty ..

	private List<MarketValue> messages;

	public GenericResponse() {
		super();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<MarketValue> getMessages() {
		return messages;
	}

	public void setMessages(List<MarketValue> messages) {
		this.messages = messages;
	}

}
