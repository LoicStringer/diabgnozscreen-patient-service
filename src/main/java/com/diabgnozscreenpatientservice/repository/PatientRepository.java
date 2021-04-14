package com.diabgnozscreenpatientservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diabgnozscreenpatientservice.entity.PatientEntity;

public interface PatientRepository extends JpaRepository<PatientEntity, Long> {


}
