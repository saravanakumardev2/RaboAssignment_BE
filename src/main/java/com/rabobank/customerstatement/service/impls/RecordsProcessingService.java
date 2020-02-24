package com.rabobank.customerstatement.service.impls;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.io.Files;
import com.rabobank.customerstatement.constants.ResponseCodeDescription;
import com.rabobank.customerstatement.service.Extractor;
import com.rabobank.customerstatement.service.Validator;
import com.rabobank.customerstatement.service.exceptions.FileExtractorServiceException;
import com.rabobank.customerstatement.service.exceptions.RecordsProcessingServiceException;
import com.rabobank.customerstatement.service.objects.Record;
import com.rabobank.customerstatement.service.objects.StatmentServiceResponse;

@Service
public class RecordsProcessingService {
	
	private static final Logger LOG = LoggerFactory.getLogger(RecordValidatorService.class);

	@Autowired
	private ExtractorFactory extractorFactory;

	@Autowired
	private Validator recordValidatorService;

	public StatmentServiceResponse process(MultipartFile multipartFile) throws RecordsProcessingServiceException {

		List<Record> failedRecords;
		StatmentServiceResponse statmentServiceResponse=new StatmentServiceResponse();

		try {

			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			LOG.debug("recieved file name: ->{}", fileName);

			String extension = Files.getFileExtension(fileName);
			LOG.debug("received file extenson: ->{}", extension);

			LOG.debug("transferring multipartfile to file");
			File file = (new ClassPathResource(fileName).getFile());
			multipartFile.transferTo(file);

			LOG.info("calling factory pattern logic");
			Extractor extractor = extractorFactory.getExtractorType(extension);
			
			if(extractor==null) 
			{
				LOG.debug("received file is not compatable");
				statmentServiceResponse.setServiceResponse(ResponseCodeDescription.VALIDATION_ERROR);
				return statmentServiceResponse;
			}
			
			List<Record> customerList =extractor.extractStatements(file);

			LOG.info("calling validator service to get duplicate records");
			List<Record> duplicateRecords = recordValidatorService.getDuplicateRecords(customerList);
			
			LOG.info("calling validator service to get unique records");
			List<Record> uniqueRecords = recordValidatorService.getUniqueRecords(customerList);


			LOG.info("calling validator service to get invalid balance records");
			LOG.info("sending unique records alone..");
			List<Record> invalidBalanceRecordList = recordValidatorService.validateBalance(uniqueRecords);

			LOG.info("adding duplicate and invalid balance records into failed record list");
			failedRecords = new ArrayList<>();
			failedRecords.addAll(duplicateRecords);
			failedRecords.addAll(invalidBalanceRecordList);

		} catch (IOException | FileExtractorServiceException exception) {

			LOG.error("error in processing records",exception);
			throw new RecordsProcessingServiceException();

		}
		
		statmentServiceResponse.setRecords(failedRecords);
		statmentServiceResponse.setServiceResponse(ResponseCodeDescription.SUCCESS);
		return statmentServiceResponse;
	}
}
