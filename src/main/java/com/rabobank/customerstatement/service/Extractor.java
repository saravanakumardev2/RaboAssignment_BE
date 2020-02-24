package com.rabobank.customerstatement.service;

import java.io.File;
import java.util.List;

import com.rabobank.customerstatement.constants.ExtentionTypes;
import com.rabobank.customerstatement.service.exceptions.FileExtractorServiceException;
import com.rabobank.customerstatement.service.objects.Record;

 public interface Extractor {
	 List<Record> extractStatements(File file) throws FileExtractorServiceException;
	 ExtentionTypes getExtensionType();
}
