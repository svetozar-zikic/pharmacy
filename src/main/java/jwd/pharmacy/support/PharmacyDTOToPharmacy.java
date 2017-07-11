package jwd.pharmacy.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import jwd.pharmacy.model.Pharmacy;
import jwd.pharmacy.service.PharmacyService;
import jwd.pharmacy.web.dto.PharmacyDTO;

@Component
public class PharmacyDTOToPharmacy implements Converter<PharmacyDTO, Pharmacy> {

	@Autowired
	PharmacyService pharmacySvc;
	
	@Override
	public Pharmacy convert(PharmacyDTO dto) {
		
		Pharmacy pharmacy;
		
		if (dto.getId() != null){
			pharmacy = pharmacySvc.findOne(dto.getId());
		} else {
			pharmacy = new Pharmacy();
		}
		
		pharmacy.setName(dto.getName());
		pharmacy.setCity(dto.getCity());
		
		return pharmacy;
	}
	
	public List<Pharmacy> convert(List<PharmacyDTO> dtos){
		
		List<Pharmacy> retVal = new ArrayList<>();
		
		for (PharmacyDTO dto : dtos) {
			retVal.add(convert(dto));
		}
		
		return retVal;
	}

}
