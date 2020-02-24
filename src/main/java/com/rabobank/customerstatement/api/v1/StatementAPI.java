package com.rabobank.customerstatement.api.v1;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.rabobank.customerstatement.api.controllers.v1.objects.ProcessRecordsResponse;
import com.rabobank.customerstatement.constants.Documentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RequestMapping(value = "/api/rest/v1/statement/")
@Api(tags = { "Statement Service Opearations" })
public interface StatementAPI {
	@PostMapping(value = "process", produces = MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	@ApiOperation(notes=Documentation.STATMENT_PROCESS_CSV_AND_XML_NOTES,consumes=MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, value = "Statement File Processor")
	ResponseEntity<ProcessRecordsResponse> process(MultipartFile uploadfile);
}
