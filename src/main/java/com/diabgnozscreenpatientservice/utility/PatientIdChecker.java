package com.diabgnozscreenpatientservice.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.diabgnozscreenpatientservice.exception.PatientIdCoherenceException;
import com.diabgnozscreenpatientservice.exception.PatientNotFoundException;
import com.diabgnozscreenpatientservice.repository.PatientRepository;

@Component
public class PatientIdChecker {

	@Autowired
	private PatientRepository patientRepository;;
	
	public void checkPatientId(Long patientId) throws PatientNotFoundException{
		if(!patientRepository.existsById(patientId)) {
			throw new PatientNotFoundException("Patient not registered");
		}
	}
	
	public void checkPatientIdCoherence(Long targetPatientId, Long treatedPatientId) throws PatientIdCoherenceException {
		if(!targetPatientId.equals(treatedPatientId)) {
			throw new PatientIdCoherenceException("Patient to update is different from updated patient");
		}
	}
}
