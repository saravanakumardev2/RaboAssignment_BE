package com.rabobank.customerstatement.constants;

public enum ResponseCodeDescription implements ResponseCode 
{
	INTERNAL_SERVER_ERROR("500", "Process error. Please contact service provider"), 
	VALIDATION_ERROR("400",	"xml or csv files are only allowed"),
	SUCCESS("1001","failed records retrieved");
	
	private final String code;
	private final String descrption;

	ResponseCodeDescription(String code, String descrption) {
		this.code = code;
		this.descrption = descrption;
	}

	public String getCode() {
		return code;
	}

	public String getDescrption() {
		return descrption;
	}

}
