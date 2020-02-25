package com.rabobank.customerstatement.service.objects;

import java.util.List;
import com.rabobank.customerstatement.constants.ResponseCode;

public class StatmentServiceResponse implements ServiceResponse {

	private ResponseCode serviceResponse;
	private List<Record> records;

	@Override
	public ResponseCode getServiceResponse() {
		return serviceResponse;
	}

	public void setServiceResponse(ResponseCode serviceResponse) {
		this.serviceResponse = serviceResponse;
	}

	public List<Record> getRecords() {
		return records;
	}

	public void setRecords(List<Record> records) {
		this.records = records;
	}

	

}
