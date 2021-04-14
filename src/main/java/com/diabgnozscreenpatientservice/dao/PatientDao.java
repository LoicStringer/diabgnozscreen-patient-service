package com.diabgnozscreenpatientservice.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.diabgnozscreenpatientservice.mapper.PatientMapper;
import com.diabgnozscreenpatientservice.model.Patient;
import com.diabgnozscreenpatientservice.repository.PatientRepository;

public class PatientDao {

	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private PatientMapper patientMapper ;
	
	public List<Patient> getAllPatientsList(){
		return patientMapper.patientEntitiesListToPatientsList(patientRepository.findAll());
	}
	
	
}
