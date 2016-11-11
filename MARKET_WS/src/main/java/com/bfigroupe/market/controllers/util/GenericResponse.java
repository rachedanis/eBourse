package com.bfigroupe.market.controllers.util;

import java.util.List;

import com.bfigroupe.market.model.MarketValue;

public class GenericResponse {

	private String status; // ok / offline / internalError / emptyResponse /
							// count : ??
	private List<MarketValue> messages;

	public GenericResponse(List<MarketValue> messages) {
		super();
		this.messages = messages;
		if (messages.isEmpty())
			status = "empty";
		else {
			for (MarketValue mv : messages) {
				mv.setId(null);
				mv.getValue().setId(null);
				mv.getValue().getEntreprise().setId(null);
			}
			status = "ok";
		}
	}

	public GenericResponse(String status, List<MarketValue> messages) {
		super();
		this.messages = messages;
		this.status = status;
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
