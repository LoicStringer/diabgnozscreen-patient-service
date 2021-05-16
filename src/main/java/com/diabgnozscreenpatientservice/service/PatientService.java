package com.diabgnozscreenpatientservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.diabgnozscreenpatientservice.dao.PatientDao;
import com.diabgnozscreenpatientservice.exception.PatientIdMismatchException;
import com.diabgnozscreenpatientservice.exception.PatientIdSettingNotAllowedException;
import com.diabgnozscreenpatientservice.exception.PatientNotFoundException;
import com.diabgnozscreenpatientservice.model.Patient;

@Service
public class PatientService {
	
	@Autowired
	private PatientDao patientDao;
	
	public Page<Patient> getAllPatientsList(@Nullable String patientLastName,Pageable pageable) throws PatientNotFoundException{
		return patientDao.getAllPatientsList(patientLastName,pageable);
	}

	public Patient getOnePatient(Long patientId) throws PatientNotFoundException {
		return patientDao.getOnePatient(patientId);
	}
	
	public Patient addPatient(Patient patientToAdd) throws PatientIdSettingNotAllowedException {
		return patientDao.addPatient(patientToAdd);
		
	}
	
	public Patient updatePatient(Long patientId, Patient updatedPatient) throws PatientNotFoundException, PatientIdMismatchException {
		return patientDao.updatePatient(patientId,updatedPatient);
	}

	
}
