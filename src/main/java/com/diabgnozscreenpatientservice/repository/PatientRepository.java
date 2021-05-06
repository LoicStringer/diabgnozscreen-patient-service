package com.diabgnozscreenpatientservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.diabgnozscreenpatientservice.entity.PatientEntity;

public interface PatientRepository extends JpaRepository<PatientEntity, Long>, JpaSpecificationExecutor<PatientEntity>{

	boolean existsByPatientLastName(String patientLastName);
	
 }
