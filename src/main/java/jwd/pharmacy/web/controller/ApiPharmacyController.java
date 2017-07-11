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

import jwd.pharmacy.model.Pharmacy;
import jwd.pharmacy.model.Medication;
import jwd.pharmacy.service.PharmacyService;
import jwd.pharmacy.service.MedicationService;
import jwd.pharmacy.support.PharmacyDTOToPharmacy;
import jwd.pharmacy.support.PharmacyToPharmacyDTO;
import jwd.pharmacy.support.MedicationDTOToMedication;
import jwd.pharmacy.support.MedicationToMedicationDTO;
import jwd.pharmacy.web.dto.PharmacyDTO;
import jwd.pharmacy.web.dto.MedicationDTO;

@RestController
@RequestMapping(value = "/api/pharmacies")
public class ApiPharmacyController {
	
	@Autowired
	private PharmacyService pharmacySvc;
	
	@Autowired
	private PharmacyDTOToPharmacy toPharmacy;
	
	@Autowired
	private PharmacyToPharmacyDTO toDTO;
	
	@Autowired
	private MedicationToMedicationDTO toMedicationDTO;
	
	@Autowired
	private MedicationDTOToMedication toMedication;
	
	@Autowired
	private MedicationService medicationSvc;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<PharmacyDTO>> getAll(){
		
		List<Pharmacy> retVal = pharmacySvc.findAll();
		
		if (retVal == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		if (retVal.isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
		return new ResponseEntity<>(toDTO.convert(retVal), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}/medications")
	public ResponseEntity<List<MedicationDTO>> getMedicationsForOnePharmacy(
			@PathVariable Long id){
		
		Pharmacy pharmacy = pharmacySvc.findOne(id);
		List<Medication> retVal = pharmacy.getMedications();

		if (retVal == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		if (retVal.isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
		return new ResponseEntity<>(toMedicationDTO.convert(retVal), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/{id}/medications")
	public ResponseEntity<PharmacyDTO> addMedicationToPharmacy(
			@PathVariable Long id,
			@RequestBody MedicationDTO dto
			){
		
		Pharmacy pharmacy = pharmacySvc.findOne(id);
		if (pharmacy == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		Medication medication = toMedication.convert(dto);
		if (medication == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		if (pharmacy.getMedications().contains(medication)){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		pharmacy.addMedication(medication);
		
		return new ResponseEntity<>(toDTO.convert(pharmacySvc.save(pharmacy)), HttpStatus.OK);

	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}/medications/{idMedication}")
	public ResponseEntity<PharmacyDTO> removeMedicationFromPharmacy(
			@PathVariable Long id,
			@PathVariable Long idMedication
			){
		
		Pharmacy pharmacy = pharmacySvc.findOne(id);
		if (pharmacy == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		Medication medication = medicationSvc.findOne(idMedication);
		if (medication == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		pharmacy.removeMedication(medication);
		pharmacySvc.save(pharmacy);
		
		return new ResponseEntity<>(toDTO.convert(pharmacy), HttpStatus.OK);
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<PharmacyDTO> getOne(@PathVariable Long id){
		
		Pharmacy pharmacy = pharmacySvc.findOne(id);
		
		if (pharmacy == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(toDTO.convert(pharmacy), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<PharmacyDTO> add(@RequestBody PharmacyDTO dto){
		
		Pharmacy pharmacy = toPharmacy.convert(dto);
		Pharmacy persisted = pharmacySvc.save(pharmacy);
		
		return new ResponseEntity<>(toDTO.convert(persisted), HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<PharmacyDTO> edit(
			@PathVariable Long id,
			@RequestBody PharmacyDTO dto
			){
		
		if (!id.equals(dto.getId()))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		Pharmacy pharmacy = toPharmacy.convert(dto);
		Pharmacy persisted = pharmacySvc.save(pharmacy);
		
		return new ResponseEntity<>(toDTO.convert(persisted), HttpStatus.OK);
		
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<PharmacyDTO> delete(@PathVariable Long id){
		
		Pharmacy pharmacy = pharmacySvc.findOne(id);
		if (pharmacy == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		Pharmacy deleted = pharmacySvc.delete(id);
		
		
		return new ResponseEntity<>(toDTO.convert(deleted), HttpStatus.OK);
	}

}
