package com.bfigroupe.market.model;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.data.annotation.Id;

public class Value {

	@Id
	private String id;

	private String name;
	private String isin;
	private String nature; // ACTION / OBLIGATION

	private Collection<MarketValue> marketvalues;

	private Entreprise entreprise;

	public Value() {
		super();
	}

	public Value(String name, String isin, String nature, Entreprise entreprise) {
		super();
		this.name = name;
		this.nature = nature;
		this.isin = isin;
		this.entreprise = entreprise;
	}

	public void addMarketValue(MarketValue marketValue) {
		if (this.marketvalues == null)
			this.marketvalues = new ArrayList<MarketValue>();
		if (!this.marketvalues.contains(marketValue))
			this.marketvalues.add(marketValue);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsin() {
		return isin;
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

	public Collection<MarketValue> getMarketvalues() {
		return marketvalues;
	}

	public void setMarketvalues(Collection<MarketValue> marketvalues) {
		this.marketvalues = marketvalues;
	}

	public Entreprise getEntreprise() {
		return entreprise;
	}

	public void setEntreprise(Entreprise entreprise) {
		this.entreprise = entreprise;
	}

	@Override
	public String toString() {
		return "Value [id=" + id + ", name=" + name + ", isin=" + isin + ", nature=" + nature + ", marketvalues="
				+ marketvalues + ", entreprise=" + entreprise + "]";
	}
}
