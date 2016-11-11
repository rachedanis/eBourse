package com.bfigroupe.ebourse.persistence.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bfigroupe.ebourse.web.error.RequestException;

@Entity
@Table(name = "Requests")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "request_type", discriminatorType = DiscriminatorType.STRING)
public class Request implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "request_type", insertable = false, updatable = false)
	private String type;

	@ManyToOne(targetEntity = Order.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "oldOrder_id", referencedColumnName = "id")
	private Order oldOrder;

	private Date date;

	private String status;

	public Request() {
		super();
		status = RequestState.NOT_SENT.getState();
	}

	public Request(Order oldOrder) {
		super();
		this.oldOrder = oldOrder;
		this.date = new Date(new java.util.Date().getTime());
		status = RequestState.NOT_SENT.getState();
	}

	public Request(Order oldOrder, Date date) {
		super();
		this.oldOrder = oldOrder;
		this.date = date;
		status = RequestState.NOT_SENT.getState();
	}

	public boolean updateState(RequestState state) {
		if (this.getStatus().equals(RequestState.ACCEPTED.getState()))
			throw new RequestException("error.request.accepted");
		else if (this.getStatus().equals(RequestState.REFUSED.getState()))
			throw new RequestException("error.request.refused");
		else if (this.getStatus().equals(RequestState.SENT.getState()))
			switch (state) {
			case ACCEPTED:
			case REFUSED: {
				this.setStatus(state.getState());
				return true;
			}
			case NOT_SENT:
				throw new RequestException("error.request.notSent");
			case SENT:
				throw new RequestException("error.request.sent");
			default:
				throw new RequestException("error.request.illegalState");
			}
		else if (this.getStatus().equals(RequestState.NOT_SENT.getState())) {
			switch (state) {
			case ACCEPTED:
			case REFUSED: {
				throw new RequestException("error.request.notSent");
			}
			case NOT_SENT:
				throw new RequestException("error.request.notSent");
			case SENT:
				this.setStatus(state.getState());
				return true;
			default:
				throw new RequestException("error.request.illegalState");
			}
		} else
			throw new RequestException("error.request.illegalState");
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Order getOldOrder() {
		return oldOrder;
	}

	public void setOldOrder(Order oldOrder) {
		this.oldOrder = oldOrder;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	@Override
	public String toString() {
		return "Request [id=" + id + ", type=" + type + ", oldOrder=" + oldOrder + ", date=" + date + ", status="
				+ status + "]";
	}

}
