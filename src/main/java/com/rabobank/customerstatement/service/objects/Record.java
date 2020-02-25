package com.rabobank.customerstatement.service.objects;


import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Record {

	private int transactionRef;
	
	private String accountNumber;
	
	private BigDecimal startBalance;
	
	private BigDecimal mutation;
	
	private String description;

	private BigDecimal endBalance;

	public Record() {
	}

	public Record(int transactionRef, String accountNumber, BigDecimal startBalance, BigDecimal mutation, String description,
			BigDecimal endBalance) {
		this.transactionRef = transactionRef;
		this.accountNumber = accountNumber;
		this.startBalance = startBalance;
		this.mutation = mutation;
		this.description = description;
		this.endBalance = endBalance;
	}

	@XmlAttribute(name="reference")
	public int getTransactionRef() {
		return transactionRef;
	}

	public void setTransactionRef(int transactionRef) {
		this.transactionRef = transactionRef;
	}

	@XmlElement(name="accountNumber")
	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	@XmlElement(name="description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@XmlElement(name="startBalance")
	public BigDecimal getStartBalance() {
		return startBalance;
	}

	public void setStartBalance(BigDecimal startBalance) {
		this.startBalance = startBalance;
	}

	@XmlElement(name="mutation")
	public BigDecimal getMutation() {
		return mutation;
	}

	public void setMutation(BigDecimal mutation) {
		this.mutation = mutation;
	}

	@XmlElement(name="endBalance")
	public BigDecimal getEndBalance() {
		return endBalance;
	}

	public void setEndBalance(BigDecimal endBalance) {
		this.endBalance = endBalance;
	}
}
