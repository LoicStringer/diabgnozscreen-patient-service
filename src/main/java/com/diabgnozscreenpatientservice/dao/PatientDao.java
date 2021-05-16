package com.diabgnozscreenpatientservice.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import com.diabgnozscreenpatientservice.entity.PatientEntity;
import com.diabgnozscreenpatientservice.entity.PatientEntity_;
import com.diabgnozscreenpatientservice.exception.PatientIdMismatchException;
import com.diabgnozscreenpatientservice.exception.PatientIdSettingNotAllowedException;
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

	public Page<Patient> getAllPatientsList(@Nullable String patientLastName, Pageable pageable)
			throws PatientNotFoundException {
		checkPatientLastName(patientLastName);
		Page<PatientEntity> allPatientEntitiesPage = patientRepository.findAll(nameLike(patientLastName), pageable);
		Page<Patient> allPatientsPage = allPatientEntitiesPage.map(p -> patientMapper.patientEntityToPatient(p));
		return allPatientsPage;
	}

	public Patient getOnePatient(Long patientId) throws PatientNotFoundException {
		checkPatientId(patientId);
		PatientEntity patientEntityToGet = patientRepository.findById(patientId).get();
		return (patientMapper.patientEntityToPatient(patientEntityToGet));
	}

	public Patient addPatient(Patient patientToAdd) throws PatientIdSettingNotAllowedException {
		preventPatientIdBreach(patientToAdd);
		patientRepository.save(patientMapper.patientToPatientEntity(patientToAdd));
		return patientToAdd;
	}

	public Patient updatePatient(Long patientId, Patient updatedPatient) throws PatientNotFoundException, PatientIdMismatchException {
		checkPatientId(patientId);
		checkPatientIdCoherence(patientId,updatedPatient.getPatientId());
		patientRepository.save(patientMapper.patientToPatientEntity(updatedPatient));
		return updatedPatient;
	}

	private void checkPatientId(Long patientId) throws PatientNotFoundException {
		if (!patientRepository.existsById(patientId))
			throw new PatientNotFoundException();
	}

	private void checkPatientLastName(String patientLastName) throws PatientNotFoundException {
		if (!patientLastName.isEmpty() && !patientRepository.existsByPatientLastName(patientLastName))
			throw new PatientNotFoundException();
	}

	private void checkPatientIdCoherence(Long targetPatientId, Long treatedPatientId)
			throws PatientIdMismatchException {
		if (!targetPatientId.equals(treatedPatientId))
			throw new PatientIdMismatchException();
	}
	
	private void preventPatientIdBreach(Patient patientToAdd ) throws PatientIdSettingNotAllowedException {
		if(patientToAdd.getPatientId()!=null)
			throw new PatientIdSettingNotAllowedException();
	}

	private Specification<PatientEntity> nameLike(String patientLastName) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(PatientEntity_.PATIENT_LAST_NAME),
				"%" + patientLastName + "%");
	}

}
