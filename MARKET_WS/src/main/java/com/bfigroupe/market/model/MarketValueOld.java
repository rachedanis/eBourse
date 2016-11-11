package com.bfigroupe.market.model;

import org.springframework.data.annotation.Id;

public class MarketValueOld {

	@Id
	private String id;
	private String nature; // ACTION / OBLIGATION ...
	private String isin;
	private Double valeur;


	public MarketValueOld() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public String getISIN() {
		return isin;
	}

	public void setISIN(String isin) {
		this.isin = isin;
	}

	public Double getValeur() {
		return valeur;
	}

	public void setValeur(Double valeur) {
		this.valeur = valeur;
	}

}
