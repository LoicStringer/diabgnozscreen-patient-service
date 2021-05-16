package com.diabgnozscreenpatientservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;

import com.diabgnozscreenpatientservice.entity.PatientEntity;

public interface PatientRepository extends JpaRepository<PatientEntity, Long>, JpaSpecificationExecutor<PatientEntity>{

	boolean existsByPatientLastName(String patientLastName);
	Page<PatientEntity> findAll(@Nullable Specification<PatientEntity> spec, Pageable pageable);
		
	
	
 }
