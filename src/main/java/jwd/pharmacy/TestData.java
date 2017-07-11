package jwd.pharmacy;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jwd.pharmacy.model.Pharmacy;
import jwd.pharmacy.model.Medication;
import jwd.pharmacy.service.PharmacyService;
import jwd.pharmacy.service.MedicationService;

@Component
public class TestData {
	
	@Autowired
	MedicationService medicationSvc;
	
	@Autowired
	PharmacyService pharmacySvc;
	
//	@PostConstruct
	public void init(){

		Medication medication1 = new Medication();
		medication1.setPrice(200);
		medication1.setName("Brufen 400mg");
		medicationSvc.save(medication1);
		
		Medication medication2 = new Medication();
		medication2.setPrice(300);
		medication2.setName("Aspirin 500mg");
		medicationSvc.save(medication2);
		
		Pharmacy a1 = new Pharmacy();
		a1.setCity("Bečej");
		a1.setName("Državna apoteka");
		a1.addMedication(medication1);
		a1.addMedication(medication2);
		pharmacySvc.save(a1);
		
		Pharmacy a2 = new Pharmacy();
		a2.setCity("Novi Sad");
		a2.setName("Privatna apoteka");
		a2.addMedication(medication2);
		a2.addMedication(medication1);
		pharmacySvc.save(a2);
		
		
	}
	

}
