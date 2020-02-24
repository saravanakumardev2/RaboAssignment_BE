package com.rabobank.customerstatement.service.impls;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.rabobank.customerstatement.constants.ExtentionTypes;
import com.rabobank.customerstatement.service.Extractor;
import com.rabobank.customerstatement.service.exceptions.FileExtractorServiceException;
import com.rabobank.customerstatement.service.objects.Record;
import com.rabobank.customerstatement.service.objects.Records;

@Service
public class XmlFileExtractorService implements Extractor {
	
	private static final Logger LOG = LoggerFactory.getLogger(XmlFileExtractorService.class);

	@Override
	public List<Record> extractStatements(File file) throws FileExtractorServiceException{
		Records rootRecord;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Records.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			LOG.debug("unmarshalling records from xml file");
			rootRecord = (Records) jaxbUnmarshaller.unmarshal(file);
		} catch (JAXBException exception) {
			LOG.error("error in extracting xml records",exception);
			throw new FileExtractorServiceException();
		}
		return rootRecord.getRecord();
	}

	@Override
	public ExtentionTypes getExtensionType() {
		return ExtentionTypes.XML;
	}

	

}
