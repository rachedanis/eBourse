package com.bfigroupe.market.model;

public class ValueDetail {
	private String name;
	private String isin;
	private Entreprise entreprise;

	public ValueDetail() {
		super();
	}

	public ValueDetail(String name, String isin, Entreprise entreprise) {
		super();
		this.name = name;
		this.isin = isin;
		this.entreprise = entreprise;
	}

	public String getName() {
		return name;
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

	public Entreprise getEntreprise() {
		return entreprise;
	}

	public void setEntreprise(Entreprise entreprise) {
		this.entreprise = entreprise;
	}

}
