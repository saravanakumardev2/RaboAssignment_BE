package com.rabobank.customerstatement.api.controllers;

import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.rabobank.customerstatement.api.controllers.v1.objects.FailedRecord;
import com.rabobank.customerstatement.api.controllers.v1.objects.ProcessRecordsResponse;
import com.rabobank.customerstatement.constants.ResponseCodeDescription;
import com.rabobank.customerstatement.service.exceptions.RecordsProcessingServiceException;
import com.rabobank.customerstatement.service.impls.RecordsProcessingService;
import com.rabobank.customerstatement.service.objects.StatmentServiceResponse;

@RestController
public class StatementsController implements StatementAPI {

	@Autowired
	private RecordsProcessingService recordsProcessingService;

	public ResponseEntity<ProcessRecordsResponse> process(@RequestParam("file") MultipartFile multipartFile) {
		ProcessRecordsResponse res = new ProcessRecordsResponse();
		try {
			StatmentServiceResponse serviceResponse = recordsProcessingService.process(multipartFile);
			if ("SUCCESS".equals(serviceResponse.getServiceResponse().toString())) {
				res.setFailedRecords(serviceResponse.getRecords().stream().map(obj -> {
					FailedRecord fr = new FailedRecord();
					fr.setDescription(obj.getDescription());
					fr.setTransactionReference(obj.getTransactionRef());
					return fr;
				}).collect(Collectors.toList()));
			}
			res.setResponseCode(serviceResponse.getServiceResponse().getCode());
			res.setResponseDesc(serviceResponse.getServiceResponse().getDescrption());
		} catch (RecordsProcessingServiceException exception) {
			res.setResponseCode(ResponseCodeDescription.INTERNAL_SERVER_ERROR.getCode());
			res.setResponseDesc(ResponseCodeDescription.INTERNAL_SERVER_ERROR.getDescrption());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
		}
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}
}
