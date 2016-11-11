package com.bfigroupe.market.model;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class MarketValue {

	@Id
	private String id;
		
	private Value value;

	private Date date;
	private double cours;

	public MarketValue() {
	}

	public MarketValue(Value value, Date date, double cours) {
		super();
		this.value = value;
		this.date = date;
		this.cours = cours;
	}

	public MarketValue(Value value, double cours) {
		super();
		this.value = value;
		this.cours = cours;
		this.date = new Date();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Value getValue() {
		return value;
	}

	public void setValue(Value value) {
		this.value = value;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getCours() {
		return cours;
	}

	public void setCours(double cours) {
		this.cours = cours;
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final MarketValue mv = (MarketValue) obj;
		if (!this.value.equals(mv.getValue())) {
			return false;
		}
		if (this.date != mv.getDate() || this.value.getNature() != mv.getValue().getNature()) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "MarketValue [id=" + id + ", value=" + value + ", date=" + date + ", cours=" + cours + "]";
	}

}
