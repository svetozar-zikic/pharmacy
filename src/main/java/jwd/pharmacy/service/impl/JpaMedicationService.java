package jwd.pharmacy.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jwd.pharmacy.model.Medication;
import jwd.pharmacy.repository.MedicationRepository;
import jwd.pharmacy.service.MedicationService;

@Service
@Transactional
public class JpaMedicationService implements MedicationService {

	@Autowired
	private MedicationRepository medicationRepo;
	
	@Override
	public List<Medication> findAll() {
		return medicationRepo.findAll();
	}

	@Override
	public Medication findOne(Long id) {
		return medicationRepo.findOne(id);
	}

	@Override
	public Medication save(Medication medication) {
		return medicationRepo.save(medication);
	}

	@Override
	public Medication delete(Long id) {
		Medication deleted = medicationRepo.findOne(id);
		medicationRepo.delete(id);
		return deleted;
	}

}
