package com.diabgnozscreenpatientservice.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diabgnozscreenpatientservice.dto.PatientDto;
import com.diabgnozscreenpatientservice.exception.PatientIdCoherenceException;
import com.diabgnozscreenpatientservice.exception.PatientNotFoundException;
import com.diabgnozscreenpatientservice.mapper.PatientMapper;
import com.diabgnozscreenpatientservice.model.Patient;
import com.diabgnozscreenpatientservice.service.PatientService;

@RestController
@RequestMapping("/diabgnoz/patients")
@CrossOrigin(origins = "http://localhost:4200")
public class PatientController {
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private PatientMapper patientMapper;
	
	@GetMapping("")
	public ResponseEntity<Page<PatientDto>> getAllPatientsList (Pageable pageable){
		Page<PatientDto> allPatientDtosPage = 
				patientService.getAllPatientsList(pageable).map(p->patientMapper.patientToPatientDto(p));
		return ResponseEntity.ok(allPatientDtosPage);
	}

	@GetMapping("/{patientId}")
	public ResponseEntity<PatientDto> getOnePatient(@PathVariable Long patientId) throws PatientNotFoundException{
		PatientDto patientToFetch = 
				patientMapper.patientToPatientDto(patientService.getOnePatient(patientId));
		return ResponseEntity.ok(patientToFetch);
	}
	
	@PostMapping("")
	public ResponseEntity<PatientDto> addPatient(@RequestBody PatientDto patientToAdd){
		Patient patientToSave = patientMapper.patientDtoToPatient(patientToAdd);
		patientService.addPatient(patientToSave);
		return ResponseEntity.ok(patientToAdd);
	}
	
	@PutMapping("/{patientId}")
	public ResponseEntity<PatientDto> updatePatient(@PathVariable Long patientId, @Valid @RequestBody PatientDto updatedPatient) throws PatientNotFoundException, PatientIdCoherenceException{
		Patient patientToUpdate = patientMapper.patientDtoToPatient(updatedPatient);
		patientService.updatePatient(patientId, patientToUpdate);
		return ResponseEntity.ok(updatedPatient);
	}
	
	@GetMapping("/names/{patientLastName}")
	public ResponseEntity<List<PatientDto>> getPatientsByName(@PathVariable String patientLastName){
		List<PatientDto> patientDtosByNameList = 
				patientMapper.patientsListToPatientDtosList(patientService.getPatientsByNameList(patientLastName));
		return ResponseEntity.ok(patientDtosByNameList);
	}
	
}


