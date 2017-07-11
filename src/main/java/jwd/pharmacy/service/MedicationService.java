package jwd.pharmacy.service;

import java.util.List;

import jwd.pharmacy.model.Medication;

public interface MedicationService {
	
	List<Medication> findAll();
	
	Medication findOne(Long id);
	
	Medication save(Medication medication);
	
	Medication delete(Long id);
	
	

}
