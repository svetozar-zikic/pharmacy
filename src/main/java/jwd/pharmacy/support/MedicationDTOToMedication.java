package jwd.pharmacy.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import jwd.pharmacy.model.Medication;
import jwd.pharmacy.service.MedicationService;
import jwd.pharmacy.web.dto.MedicationDTO;

@Component
public class MedicationDTOToMedication implements Converter<MedicationDTO, Medication> {

	@Autowired
	private MedicationService medicationSvc;
	
	@Override
	public Medication convert(MedicationDTO dto) {

		Medication medication;
		
		if (dto.getId() == null){
			medication = new Medication();
		} else {
			medication = medicationSvc.findOne(dto.getId());
		}
	
		medication.setPrice(dto.getPrice());
		medication.setName(dto.getName());
		
		return medication;
	
	}

}
