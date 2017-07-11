package jwd.pharmacy.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table
public class Pharmacy {

	@Id
	@GeneratedValue
	@Column
	private Long id;

	@Column
	private String name;

	@Column
	private String city;

	@ManyToMany
	private List<Medication> medications = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public List<Medication> getMedications() {
		return medications;
	}

	public void setMedications(List<Medication> medications) {
		this.medications = medications;
	}

	public void addMedication(Medication medication) {
		this.medications.add(medication);
	}

	public void removeMedication(Medication medication) {
		for (int i = 0; i < this.medications.size(); i++) {
			if (this.medications.get(i).equals(medication)) {
				this.medications.remove(i);
			}
		}
	}

}
