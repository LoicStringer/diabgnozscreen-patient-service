package com.diabgnozscreenpatientservice.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.diabgnozscreenpatientservice.entity.PatientEntity;
import com.diabgnozscreenpatientservice.exception.PatientNotFoundException;
import com.diabgnozscreenpatientservice.mapper.PatientMapper;
import com.diabgnozscreenpatientservice.model.Patient;
import com.diabgnozscreenpatientservice.repository.PatientRepository;


@Repository
public class PatientDao {

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private PatientMapper patientMapper;
	
	
	public Page<Patient> getAllPatientsList(Pageable pageable) {
		Page<PatientEntity> allPatientEntitiesPage = patientRepository.findAll(pageable);
		Page<Patient> allPatientsPage = allPatientEntitiesPage.map(p -> patientMapper.patientEntityToPatient(p));
		return allPatientsPage;
	}

	public Patient getOnePatient(Long patientId) throws PatientNotFoundException {
		checkPatientId(patientId);
		PatientEntity patientEntityToGet = patientRepository.findById(patientId).get();
		return (patientMapper.patientEntityToPatient(patientEntityToGet));
	}

	public Patient addPatient(Patient patientToAdd) {
		patientRepository.save(patientMapper.patientToPatientEntity(patientToAdd));
		return patientToAdd;
	}
	
	public Patient updatePatient(Long patientId, Patient updatedPatient) throws PatientNotFoundException {
		checkPatientId(patientId);
		patientRepository.save(patientMapper.patientToPatientEntity(updatedPatient));
		return updatedPatient;
	}

	private void checkPatientId(Long patientId) throws PatientNotFoundException{
		if(!patientRepository.existsById(patientId)) 
			throw new PatientNotFoundException("Patient not registered");
	}
	
	private void checkPatientLastName(String patientLastName) throws PatientNotFoundException {
		if(!patientRepository.existsByPatientLastName(patientLastName))
			throw new PatientNotFoundException("Patient not registered");
	}

}
