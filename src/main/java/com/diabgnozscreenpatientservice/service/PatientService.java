package com.diabgnozscreenpatientservice.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.diabgnozscreenpatientservice.dao.PatientDao;
import com.diabgnozscreenpatientservice.exception.PatientIdCoherenceException;
import com.diabgnozscreenpatientservice.exception.PatientNotFoundException;
import com.diabgnozscreenpatientservice.model.Patient;

@Service
public class PatientService {
	
	@Autowired
	private PatientDao patientDao;
	
	public Page<Patient> getAllPatientsList(Pageable pageable){
		return patientDao.getAllPatientsList(pageable);
	}

	public Patient getOnePatient(Long patientId) throws PatientNotFoundException {
		return patientDao.getOnePatient(patientId);
	}
	
	public Patient addPatient(Patient patientToAdd) {
		return patientDao.addPatient(patientToAdd);
		
	}
	
	public Patient updatePatient(Long patientId, Patient updatedPatient) throws PatientNotFoundException, PatientIdCoherenceException {
		return patientDao.updatePatient(patientId,updatedPatient);
	}
	
	public List<Patient> getPatientsByNameList (String patientLastName){
		return patientDao.getPatientsByNameList(patientLastName);
	}
	
	public List<Patient> getPatientsByBirthDateList(LocalDate patientBirthDate){
		return patientDao.getPatientsByBirthDateList(patientBirthDate);
	}

	
}
