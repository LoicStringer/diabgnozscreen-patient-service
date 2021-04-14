package com.diabgnozscreenpatientservice.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diabgnozscreenpatientservice.entity.PatientEntity;

public interface PatientRepository extends JpaRepository<PatientEntity, Long> {

	List<PatientEntity> findByPatientLastName(String patientLastName);
	List<PatientEntity> findByPatientBirthDate(LocalDate patientBirthDate);
 }
