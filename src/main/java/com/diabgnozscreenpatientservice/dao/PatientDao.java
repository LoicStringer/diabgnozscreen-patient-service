package com.diabgnozscreenpatientservice.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	private PatientMapper patientMapper ;
	
	public List<Patient> getAllPatientsList(){
		return patientMapper.patientEntitiesListToPatientsList
				(patientRepository.findAll());
	}
	
	public Patient getOnePatient(Long patientId) throws PatientNotFoundException {
		PatientEntity patientEntityToGet = 
				patientRepository.findById(patientId).orElseThrow(()->new PatientNotFoundException("This patient is not registered."));
		return (patientMapper.patientEntityToPatient(patientEntityToGet));
	}
	
	public List<Patient> getPatientsByNameList (String patientLastName){
		return patientMapper.patientEntitiesListToPatientsList
				(patientRepository.findByPatientLastName(patientLastName));
	}
	
	public List<Patient> getPatientsByBirthDateList (LocalDate patientBirthDate){
		return patientMapper.patientEntitiesListToPatientsList
				(patientRepository.findByPatientBirthDate(patientBirthDate));
	}
}