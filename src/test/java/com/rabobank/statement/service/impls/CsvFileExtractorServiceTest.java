package com.rabobank.statement.service.impls;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;

import com.rabobank.customerstatement.service.impls.CsvFileExtractorService;
import com.rabobank.customerstatement.service.objects.Record;

@RunWith(MockitoJUnitRunner.class)
public class CsvFileExtractorServiceTest {
	
	@InjectMocks
	CsvFileExtractorService classUnderTest;
	
	List<Record> expected=new ArrayList<>();
	Record record1,record2,record3;
	
	@Before
	public void setup(){
		
		record1 =new Record();
		record1.setTransactionRef(111);
		record1.setAccountNumber("accountNumber1");
		record1.setDescription("description1");
		record1.setEndBalance(new BigDecimal(5));
		record1.setMutation(new BigDecimal(-5));
		record1.setStartBalance(new BigDecimal(10));
		expected.add(record1);
		
		record2 =new Record();
		record2.setTransactionRef(123);
		record2.setAccountNumber("accountNumber2");
		record2.setDescription("description2");
		record2.setEndBalance(new BigDecimal(9.8));
		record2.setMutation(new BigDecimal(-0.3));
		record2.setStartBalance(new BigDecimal(10.1));
		expected.add(record2);
		
		record3 =new Record();
		record3.setTransactionRef(111);
		record3.setAccountNumber("accountNumber3");
		record3.setDescription("description3");
		record3.setEndBalance(new BigDecimal(10.5));
		record3.setMutation(new BigDecimal(-5.5));
		record3.setStartBalance(new BigDecimal(15.5));
		expected.add(record3);
	}
	
	
	@Test
	public void testFileExtractorForCSV() throws Exception{
		
		
		List<Record> actual=classUnderTest.extractStatements(new ClassPathResource("testrecords.csv").getFile());
		assertEquals(expected.get(0).getTransactionRef(), actual.get(0).getTransactionRef());
		assertEquals(expected.get(1).getTransactionRef(), actual.get(1).getTransactionRef());
		assertEquals(expected.get(2).getTransactionRef(), actual.get(2).getTransactionRef());
		
		assertEquals(expected.size(), actual.size());
		
	}

}
