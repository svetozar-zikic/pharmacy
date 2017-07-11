package jwd.pharmacy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jwd.pharmacy.model.Medication;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {

}
