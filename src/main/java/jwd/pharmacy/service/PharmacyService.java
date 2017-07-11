package jwd.pharmacy.service;

import java.util.List;

import jwd.pharmacy.model.Pharmacy;

public interface PharmacyService {
	
	List<Pharmacy> findAll();
	
	Pharmacy findOne(Long id);
	
	Pharmacy save(Pharmacy pharmacy);
	
	Pharmacy delete(Long id);

}
