package com.rabobank.statement.service.impls;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;

import com.rabobank.customerstatement.constants.ResponseCodeDescription;
import com.rabobank.customerstatement.service.exceptions.FileExtractorServiceException;
import com.rabobank.customerstatement.service.exceptions.RecordsProcessingServiceException;
import com.rabobank.customerstatement.service.impls.CsvFileExtractorService;
import com.rabobank.customerstatement.service.impls.ExtractorFactory;
import com.rabobank.customerstatement.service.impls.RecordValidatorService;
import com.rabobank.customerstatement.service.impls.RecordsProcessingService;
import com.rabobank.customerstatement.service.impls.XmlFileExtractorService;
import com.rabobank.customerstatement.service.objects.Record;
import com.rabobank.customerstatement.service.objects.StatmentServiceResponse;

@RunWith(MockitoJUnitRunner.class)
public class RecordsProcessingServiceTest {
	
	@Mock
	XmlFileExtractorService mockXmlFileExtractorService;
	
	@Mock
	CsvFileExtractorService mockCsvFileExtractorService;

	@Mock
	RecordValidatorService mockRecordValidatorService;
	
	@Mock
	ExtractorFactory mockExtractorFactory;
	
	@InjectMocks
	RecordsProcessingService classUnderTest;
	
	List<Record> customerList=new ArrayList<>();
	Record record1,record2,record3,record4;
	
	Map<String, List<Record>> recordsStatus=new HashMap<>();
	List<Record> duplicateRecords=new ArrayList<>();
	List<Record> uniqueRecords = new ArrayList<>();
	List<Record> invalidBalanceList=new ArrayList<>();
	List<Record> failedRecord=new ArrayList<>();
	StatmentServiceResponse expected;
	
	@Before
	public void setup() throws Exception{
		
		
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
		record4.setTransactionRef(222);
		record4.setAccountNumber("accountNumber4");
		record4.setDescription("description4");
		record4.setEndBalance(new BigDecimal(10.70));
		record4.setMutation(new BigDecimal(0.3));
		record4.setStartBalance(new BigDecimal(10.1));
		customerList.add(record4);
		
		duplicateRecords.add(record2);
		duplicateRecords.add(record3);
		duplicateRecords.add(record1);
		recordsStatus.put("duplicateRecords", duplicateRecords);
		uniqueRecords.add(record4);
		recordsStatus.put("uniqueRecords", uniqueRecords);
		
		Mockito.when(this.mockRecordValidatorService.getDuplicateRecords(any())).thenReturn(duplicateRecords);
		
		Mockito.when(this.mockRecordValidatorService.getUniqueRecords(any())).thenReturn(uniqueRecords);

		
		invalidBalanceList.add(record4);
		Mockito.when(this.mockRecordValidatorService.validateBalance(any())).thenReturn(invalidBalanceList);
		
		failedRecord.add(record2);
		failedRecord.add(record3);
		failedRecord.add(record1);
		failedRecord.add(record4);
		
	    expected=new StatmentServiceResponse();
		
	}

	@Test
	public void testProcessForCSVFileSuccess() throws FileExtractorServiceException, RecordsProcessingServiceException {
		
		MockMultipartFile multipartFile  = new MockMultipartFile("file", "servicetestrecords.csv",
                "multipart/form-data", "Spring Framework".getBytes());
		
		Mockito.when(this.mockExtractorFactory.getExtractorType("csv")).thenReturn(mockCsvFileExtractorService);
	    Mockito.when(this.mockCsvFileExtractorService.extractStatements(any())).thenReturn(customerList);

		
		StatmentServiceResponse actual=classUnderTest.process(multipartFile);
		
		expected.setServiceResponse(ResponseCodeDescription.SUCCESS);
		expected.setRecords(failedRecord);
		assertThat(expected).isEqualToComparingFieldByFieldRecursively(actual);

		
	}
	
	@Test
	public void testProcessForXMLFileSuccess() throws FileExtractorServiceException, RecordsProcessingServiceException {
		
		MockMultipartFile multipartFile  = new MockMultipartFile("file", "servicetestrecords.xml",
                "multipart/form-data", "Spring Framework".getBytes());
	   
		
		Mockito.when(this.mockExtractorFactory.getExtractorType("xml")).thenReturn(mockXmlFileExtractorService);
        Mockito.when(this.mockXmlFileExtractorService.extractStatements(any())).thenReturn(customerList);

		StatmentServiceResponse actual=classUnderTest.process(multipartFile);
		expected.setServiceResponse(ResponseCodeDescription.SUCCESS);
		expected.setRecords(failedRecord);
		
		assertThat(expected).isEqualToComparingFieldByFieldRecursively(actual);

	}
	
	
	@Test(expected=RecordsProcessingServiceException.class)
	public void testProcessForFileExtractorServiceException() throws Exception{
		
		MockMultipartFile multipartFile  = new MockMultipartFile("file", "servicetestrecords.csv",
                "multipart/form-data", "Spring Framework".getBytes());
		Mockito.when(this.mockExtractorFactory.getExtractorType("csv")).thenReturn(mockCsvFileExtractorService);

		doThrow(FileExtractorServiceException.class).when(this.mockCsvFileExtractorService).extractStatements(any());
		
		classUnderTest.process(multipartFile);
	}
}
