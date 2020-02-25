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

import com.rabobank.customerstatement.service.impls.RecordValidatorService;
import com.rabobank.customerstatement.service.objects.Record;

@RunWith(MockitoJUnitRunner.class)
public class RecordValidatorServiceTest {
	
	@InjectMocks
	RecordValidatorService classUnderTest;
	
	List<Record> customerList =new ArrayList<>();
	
	List<Record> expectedDuplicateList=new ArrayList<>();
	List<Record> expectedUniqueList=new ArrayList<>();
	
	List<Record> expectedInvalidBalanceRecordList =new ArrayList<>();
	
	Record record1,record2,record3,record4,record5;
	
	@Before
	public void setUp(){
		
		record1 =new Record();
		record1.setTransactionRef(111);
		record1.setAccountNumber("accountNumber1");
		record1.setDescription("description1");
		record1.setEndBalance(new BigDecimal(5));
		record1.setMutation(new BigDecimal(-5));
		record1.setStartBalance(new BigDecimal(10));
		customerList.add(record1);
		
		record2 =new Record();
		record2.setTransactionRef(123);
		record2.setAccountNumber("accountNumber2");
		record2.setDescription("description2");
		record2.setEndBalance(new BigDecimal(9.8));
		record2.setMutation(new BigDecimal(-0.3));
		record2.setStartBalance(new BigDecimal(10.1));
		customerList.add(record2);
		
		record3 =new Record();
		record3.setTransactionRef(111);
		record3.setAccountNumber("accountNumber3");
		record3.setDescription("description3");
		record3.setEndBalance(new BigDecimal(10.5));
		record3.setMutation(new BigDecimal(-5.5));
		record3.setStartBalance(new BigDecimal(15.5));
		customerList.add(record3);
		
		record4 =new Record();
		record4.setTransactionRef(111);
		record4.setAccountNumber("accountNumber4");
		record4.setDescription("description4");
		record4.setEndBalance(new BigDecimal(10.70));
		record4.setMutation(new BigDecimal(0.3));
		record4.setStartBalance(new BigDecimal(10.1));
		customerList.add(record4);
		
		record5 =new Record();
		record5.setTransactionRef(222);
		record5.setAccountNumber("accountNumber4");
		record5.setDescription("description4");
		record5.setEndBalance(new BigDecimal(15.70));
		record5.setMutation(new BigDecimal(0.3));
		record5.setStartBalance(new BigDecimal(15.1));
		customerList.add(record5);
	}
	
	
	@Test
	public void testGetDuplicateRecordsForSucess() {
		
		expectedDuplicateList.add(record1);
		expectedDuplicateList.add(record3);
		expectedDuplicateList.add(record4);
		
		
		List<Record> actual=classUnderTest.getDuplicateRecords(customerList);
		
		assertEquals(expectedDuplicateList, actual);
		
	}
	
	@Test
	public void testGetUniqueRecordsForSuccess() {
		
		expectedUniqueList.add(record2);
		expectedUniqueList.add(record5);
		
		List<Record> actual=classUnderTest.getUniqueRecords(customerList);
		
		assertEquals(expectedUniqueList, actual);
	}
	
	@Test
	public void testValidateBalanceForSucces() {
		
		expectedInvalidBalanceRecordList.add(record3);
		expectedInvalidBalanceRecordList.add(record4);
		expectedInvalidBalanceRecordList.add(record5);
		
		List<Record> actual=classUnderTest.validateBalance(customerList);
		
		assertEquals(expectedInvalidBalanceRecordList, actual);
	}
	

}
