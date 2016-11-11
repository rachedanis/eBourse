package com.bfigroupe.ebourse.web.dto;

import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import com.bfigroupe.ebourse.persistence.model.RequestState;

public class EvalRequestDto {

	@NotNull
	Long requestId;
	
	@NotNull
	@Enumerated
	RequestState status;

	public Long getRequestId() {
		return requestId;
	}

	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}

	public RequestState getStatus() {
		return status;
	}

	public void setStatus(RequestState status) {
		this.status = status;
	}

}
