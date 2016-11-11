package com.bfigroupe.market.model;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.data.annotation.Id;

public class Entreprise {
	@Id
	private String id;

	Collection<Value> values;

	private String name;
	private double capital;
	private String description;

	public Entreprise() {

	}

	public Entreprise(String name, double capital, String description) {
		super();
		this.name = name;
		this.capital = capital;
		this.description = description;
	}

	public void addValue(Value value) {
		if (this.values == null)
			this.values = new ArrayList<Value>();
		if (!values.contains(value))
			this.values.add(value);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Collection<Value> getValues() {
		return values;
	}

	public void setValues(Collection<Value> values) {
		this.values = values;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getCapital() {
		return capital;
	}

	public void setCapital(double capital) {
		this.capital = capital;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Entreprise [id=" + id + ", values=" + values + ", name=" + name + ", capital=" + capital
				+ ", description=" + description + "]";
	}

}
