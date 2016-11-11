package com.bfigroupe.ebourse.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "MarketValues")
public class MarketValue implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "date", columnDefinition="TIMESTAMP(6)")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	private double cours;
	@ManyToOne(targetEntity = Value.class)
	@JoinColumn(nullable = false, name = "value_isin", referencedColumnName = "isin")
	private Value value;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
		if (this.date != mv.getDate()) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "MarketValue [id=" + id + ", value=" + value + ", date=" + date + ", cours=" + cours + "]";
	}

}
