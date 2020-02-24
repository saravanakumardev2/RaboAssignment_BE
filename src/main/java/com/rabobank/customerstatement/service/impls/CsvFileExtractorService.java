package com.rabobank.customerstatement.service.impls;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.rabobank.customerstatement.constants.ExtentionTypes;
import com.rabobank.customerstatement.service.Extractor;
import com.rabobank.customerstatement.service.exceptions.FileExtractorServiceException;
import com.rabobank.customerstatement.service.objects.Record;

@Service
public class CsvFileExtractorService implements Extractor {
	
	private static final String REFERAANCE_NO = "Reference";
	private static final String ACCOUNT_NUMBER = "AccountNumber";
	private static final String DESCR = "Description";
	private static final String START_BALANCE = "Start Balance";
	private static final String MUTATION = "Mutation";
	private static final String END_BALANCE = "End Balance";
	
	private static final String[] FILE_HEADER_MAPPING = { REFERAANCE_NO, ACCOUNT_NUMBER, DESCR, START_BALANCE, MUTATION, END_BALANCE };
	
	private static final Logger LOG = LoggerFactory.getLogger(CsvFileExtractorService.class);

	public List<Record> extractStatements(File file) throws FileExtractorServiceException {
		
		List<CSVRecord> records;

		try {
			Reader reader = new FileReader(file);
			LOG.debug("parsing csv file");
			records = parseFile(reader);
			LOG.debug("extracting records");
		} catch (IOException exception) {
			LOG.error("error in extraction files from csv file",exception);
			throw new FileExtractorServiceException();
		}

		return extractRecords(records);
	}

	private List<Record> extractRecords(List<CSVRecord> records) {
		return records.stream().map(record-> 
									new Record(
											Integer.valueOf(record.get(REFERAANCE_NO)),
											record.get(ACCOUNT_NUMBER),
											new BigDecimal(record.get(START_BALANCE)),
											new BigDecimal(record.get(MUTATION)),
											record.get(DESCR),
											new BigDecimal(record.get(END_BALANCE)))).
											collect(Collectors.toList());
	}

	@SuppressWarnings("resource")
	private List<CSVRecord> parseFile(Reader reader) throws IOException {
		CSVFormat csvFileFormat = CSVFormat.RFC4180.withHeader(FILE_HEADER_MAPPING).withSkipHeaderRecord();
		CSVParser csvParser = new CSVParser(reader, csvFileFormat);
   	    return csvParser.getRecords();
	}

	@Override
	public ExtentionTypes getExtensionType() {
		return ExtentionTypes.CSV;
	}
}
