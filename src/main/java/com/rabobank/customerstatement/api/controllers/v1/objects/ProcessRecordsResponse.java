package com.rabobank.customerstatement.api.controllers.v1.objects;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Process Records Response")
public class ProcessRecordsResponse {

	@JsonProperty(value="failed_records")
	@JsonInclude(value = Include.NON_NULL)
	private List<FailedRecord> failedRecords;

	@ApiModelProperty(allowableValues = "1001")
	@JsonProperty(value = "status_code")
	private String responseCode;
	
	@ApiModelProperty(allowableValues = "success")
	@JsonProperty(value = "status_desc")
	private String responseDesc;

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseDesc() {
		return responseDesc;
	}

	public void setResponseDesc(String responseDesc) {
		this.responseDesc = responseDesc;
	}

	public List<FailedRecord> getFailedRecords() {
		return failedRecords;
	}

	public void setFailedRecords(List<FailedRecord> failedRecords) {
		this.failedRecords = failedRecords;
	}

}
