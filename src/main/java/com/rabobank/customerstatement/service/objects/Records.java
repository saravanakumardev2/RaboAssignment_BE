package com.rabobank.customerstatement.service.objects;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Records {
	public Records() {
	}

	public Records(List<Record> record) {
		this.record = record;
	}

	private List<Record> record;

	public List<Record> getRecord() {
		return record;
	}

	public void setRecord(List<Record> record) {
		this.record = record;
	}

}
