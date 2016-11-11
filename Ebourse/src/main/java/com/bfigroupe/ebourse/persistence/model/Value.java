package com.bfigroupe.ebourse.persistence.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Value implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;
	private String isin;
	private String nature; // ACTION / OBLIGATION

	@OneToMany(mappedBy = "value")
	private Collection<MarketValue> marketvalues;

	@ManyToOne(targetEntity = Entreprise.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "entreprise_name", referencedColumnName = "name")
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
		final Value value = (Value) obj;
		if (!isin.equals(value.isin)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Value [id=" + id + ", name=" + name + ", isin=" + isin + ", nature=" + nature + ", entreprise="
				+ entreprise + "]";
	}
}
