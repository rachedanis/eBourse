package com.bfigroupe.ebourse.persistence.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Entreprises")
public class Entreprise implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToMany(mappedBy = "entreprise")
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
		return "Entreprise [id=" + id + ", name=" + name + ", capital=" + capital
				+ ", description=" + description + "]";
	}
}
