package com.rabobank.customerstatement.service.impls;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.rabobank.customerstatement.service.Extractor;

@Component
public class ExtractorFactory {
	@Autowired
	private List<Extractor> extractorTypes;	
	public Extractor getExtractorType(String extensionType) {
		 return extractorTypes.stream().filter(x->x.getExtensionType().getExtention().equalsIgnoreCase(extensionType)).findFirst().orElse(null);
	}
}
