package jwd.pharmacy.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jwd.pharmacy.model.Medication;
import jwd.pharmacy.service.MedicationService;
import jwd.pharmacy.support.MedicationDTOToMedication;
import jwd.pharmacy.support.MedicationToMedicationDTO;
import jwd.pharmacy.web.dto.MedicationDTO;

@RestController
@RequestMapping(value = "/api/medications")
public class ApiMedicationController {

	@Autowired
	private MedicationService medicationSvc;
	
	@Autowired
	private MedicationToMedicationDTO toDTO;
	
	@Autowired
	private MedicationDTOToMedication toMedication;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<MedicationDTO>> getAll(){
		
		List<Medication> retVal = medicationSvc.findAll();
		
		if (retVal == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		if (retVal.isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
		return new ResponseEntity<>(toDTO.convert(retVal), HttpStatus.OK);
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<MedicationDTO> getOne(@PathVariable Long id){
		
		Medication retVal = medicationSvc.findOne(id);
		
		if (retVal == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(toDTO.convert(retVal), HttpStatus.OK);
		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<MedicationDTO> add(@RequestBody MedicationDTO dto){
		
		Medication medication = toMedication.convert(dto);
		medicationSvc.save(medication);
		
		return new ResponseEntity<>(toDTO.convert(medication), HttpStatus.CREATED);
		
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<MedicationDTO> edit(
			@PathVariable Long id,
			@RequestBody MedicationDTO dto
			){
		
		if (!id.equals(dto.getId()))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		Medication medication = toMedication.convert(dto);
		Medication persisted = medicationSvc.save(medication);
		
		return new ResponseEntity<>(toDTO.convert(persisted), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<MedicationDTO> delete(@PathVariable Long id){
		
		Medication medication = medicationSvc.findOne(id);
		
		if (medication == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		Medication deleted = medicationSvc.delete(id);
		
		return new ResponseEntity<>(toDTO.convert(deleted), HttpStatus.OK);
		
	}
	
	
	
}
