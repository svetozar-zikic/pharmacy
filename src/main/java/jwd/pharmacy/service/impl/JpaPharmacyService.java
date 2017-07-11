package jwd.pharmacy.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jwd.pharmacy.model.Pharmacy;
import jwd.pharmacy.repository.PharmacyRepository;
import jwd.pharmacy.service.PharmacyService;

@Service
@Transactional
public class JpaPharmacyService implements PharmacyService {

	@Autowired
	private PharmacyRepository pharmacyRepo;
	
	@Override
	public List<Pharmacy> findAll() {
		return pharmacyRepo.findAll();
	}

	@Override
	public Pharmacy findOne(Long id) {
		return pharmacyRepo.findOne(id);
	}

	@Override
	public Pharmacy save(Pharmacy pharmacy) {
		return pharmacyRepo.save(pharmacy);
	}

	@Override
	public Pharmacy delete(Long id) {
		Pharmacy deleted = pharmacyRepo.findOne(id);
		pharmacyRepo.delete(id);
		return deleted;
	}

}
