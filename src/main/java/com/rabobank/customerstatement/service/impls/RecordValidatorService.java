package com.rabobank.customerstatement.service.impls;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.rabobank.customerstatement.service.Validator;
import com.rabobank.customerstatement.service.objects.Record;

@Service
public class RecordValidatorService implements Validator {
	public List<Record> getDuplicateRecords(List<Record> customerRecords){
		return customerRecords.stream()
				  .collect(Collectors.groupingBy(Record::getTransactionRef))
				  .entrySet().stream()
				  .filter(e->e.getValue().size() > 1)
				  .flatMap(e->e.getValue().stream())
				  .collect(Collectors.toList());
	}
	
	public List<Record> getUniqueRecords(List<Record> customerRecords){
		
		return customerRecords.stream()
				  .collect(Collectors.groupingBy(Record::getTransactionRef))
				  .entrySet().stream()
				  .filter(e->e.getValue().size() == 1)
				  .flatMap(e->e.getValue().stream())
				  .collect(Collectors.toList());
		
	}
	
	public List<Record> validateBalance(List<Record> customerRecords){
		List<Record> failedRecords= customerRecords.stream().filter(c->! isValidBal(c.getStartBalance(), c.getMutation(), c.getEndBalance())).collect(Collectors.toList());
		return failedRecords;
	}

	public boolean isValidBal(BigDecimal startBal,BigDecimal mutationValue,BigDecimal endBal){
		DecimalFormat df=new DecimalFormat("0.00");
		return  new BigDecimal(df.format(startBal)).add(new BigDecimal(df.format(mutationValue))).subtract(new BigDecimal(df.format(endBal))).equals(new BigDecimal("0.00"));
	}
	
}
