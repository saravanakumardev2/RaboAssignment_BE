package com.rabobank.customerstatement.constants;

public enum ExtentionTypes 
{
	XML("xml"),
	CSV("csv");
	
	private String extention;
	
	ExtentionTypes(String extention){
		this.extention=extention;
	}
	
	public String getExtention() {
		return extention;
	}

}
