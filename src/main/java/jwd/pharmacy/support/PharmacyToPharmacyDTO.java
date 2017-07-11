package jwd.pharmacy.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import jwd.pharmacy.model.Pharmacy;
import jwd.pharmacy.web.dto.PharmacyDTO;

@Component
public class PharmacyToPharmacyDTO implements Converter<Pharmacy, PharmacyDTO> {

	@Override
	public PharmacyDTO convert(Pharmacy pharmacy) {
		
		PharmacyDTO dto = new PharmacyDTO();
		
		dto.setId(pharmacy.getId());
		dto.setCity(pharmacy.getCity());
		dto.setName(pharmacy.getName());
		
		return dto;
	}
	
	public List<PharmacyDTO> convert(List<Pharmacy> pharmacies){
		List<PharmacyDTO> dtos = new ArrayList<>();
		for (Pharmacy pharmacy : pharmacies) {
			dtos.add(convert(pharmacy));
		}
		return dtos;
	}

}
