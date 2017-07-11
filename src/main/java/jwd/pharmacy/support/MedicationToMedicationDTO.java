package jwd.pharmacy.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import jwd.pharmacy.model.Medication;
import jwd.pharmacy.web.dto.MedicationDTO;

@Component
public class MedicationToMedicationDTO implements Converter<Medication, MedicationDTO> {

	@Override
	public MedicationDTO convert(Medication medication) {
		
		MedicationDTO dto = new MedicationDTO();
		dto.setPrice(medication.getPrice());
		dto.setName(medication.getName());
		dto.setId(medication.getId());
		
		return dto; 
	}
	
	public List<MedicationDTO> convert(List<Medication> medications) {
		List<MedicationDTO> dtos = new ArrayList<>();
		for (Medication medication : medications) {
			dtos.add(convert(medication));
		}
		
		return dtos;
	}

}
