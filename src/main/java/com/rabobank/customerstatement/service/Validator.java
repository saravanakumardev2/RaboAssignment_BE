package com.rabobank.customerstatement.service;

import java.util.List;
import com.rabobank.customerstatement.service.objects.Record;

public interface Validator 
{
	 List<Record> getDuplicateRecords(List<Record> customerRecords);
	 
	 List<Record> getUniqueRecords(List<Record> customerRecords);
	 
	 List<Record> validateBalance(List<Record> customerRecords);
}
