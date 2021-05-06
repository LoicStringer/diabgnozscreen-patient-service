package com.diabgnozscreenpatientservice.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.diabgnozscreenpatientservice.dto.PatientDto;
import com.diabgnozscreenpatientservice.entity.PatientEntity;
import com.diabgnozscreenpatientservice.model.Patient;

@Mapper(componentModel = "spring")
public interface PatientMapper {

	Patient patientEntityToPatient (PatientEntity patientEntity);
	PatientEntity patientToPatientEntity(Patient patient);
	
	PatientDto patientToPatientDto (Patient patient);
	Patient patientDtoToPatient (PatientDto patientDto);
	
	List<Patient> patientEntitiesListToPatientsList (List<PatientEntity> patientEntitiesList);
	List<PatientDto> patientsListToPatientDtosList (List<Patient> patientsList);
}