package com.rabobank.customerstatement.api.controllers.v1.objects;

public class FailedRecord {
	
	private int transactionReference;
	private String description;
	
	public int getTransactionReference() {
		return transactionReference;
	}
	public void setTransactionReference(int transactionReference) {
		this.transactionReference = transactionReference;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
