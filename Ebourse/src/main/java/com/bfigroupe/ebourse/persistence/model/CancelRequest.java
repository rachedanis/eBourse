package com.bfigroupe.ebourse.persistence.model;

import java.sql.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CANCEL")
public class CancelRequest extends Request {
	private static final long serialVersionUID = 1L;

	public CancelRequest() {
		super();
	}

	public CancelRequest(Order oldOrder, Date date) {
		super(oldOrder, date);
	}

	public CancelRequest(Order oldOrder) {
		super(oldOrder);
	}

}
