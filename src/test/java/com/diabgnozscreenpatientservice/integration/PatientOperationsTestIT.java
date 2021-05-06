package com.diabgnozscreenpatientservice.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.diabgnozscreenpatientservice.controller.PatientController;
import com.diabgnozscreenpatientservice.dto.PatientDto;
import com.diabgnozscreenpatientservice.exception.PatientIdCoherenceException;
import com.diabgnozscreenpatientservice.exception.PatientNotFoundException;
import com.diabgnozscreenpatientservice.utility.PatientGenderEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PatientOperationsTestIT {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private PatientController patientController;
	
	@Test
	void getAllPatientsPageTest() throws Exception {
		mockMvc.perform(get("/diabgnoz/patients"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content.length()").value(5))
				.andExpect(jsonPath("$.content.[0].patientLastName").value("Jordan"));	
	}
	
	@Test
	void getOnePatientTest() throws Exception {
		mockMvc.perform(get("/diabgnoz/patients/2"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.patientLastName").value("Tyson"));
	}
	
	@Test
	void addPatientTest() throws JsonProcessingException, Exception {
		LocalDate birthDate = LocalDate.of(1981,8,8);
		PatientDto patientToAdd = new PatientDto();
		patientToAdd.setPatientId(6L);
		patientToAdd.setPatientLastName("Federer");
		patientToAdd.setPatientFirstName("Roger");
		patientToAdd.setPatientBirthDate(birthDate);
		patientToAdd.setpatientGender(PatientGenderEnum.M);
		
		mockMvc.perform(post("/diabgnoz/patients")
				.content(objectMapper.writeValueAsString(patientToAdd))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.patientLastName").value("Federer"));
	}
	
	
	@Test
	void updatePatientTest() throws JsonProcessingException, Exception {
		
		PatientDto updatedPatient = patientController.getOnePatient(5L).getBody();
		updatedPatient.setPatientFirstName("Robert");
		
		mockMvc.perform(put("/diabgnoz/patients/5")
				.content(objectMapper.writeValueAsString(updatedPatient))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.patientFirstName").value("Robert"));
		
		assertEquals("Robert", patientController.getOnePatient(5L).getBody().getPatientFirstName());
	}
	
	
	@Test
	void getPatientsByNameListTest() throws Exception {
		mockMvc.perform(get("/diabgnoz/patients/names/lewis"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.[0].patientFirstName").value("Carl"));
	}
	
	@Test
	void isExpectedExceptionAndStatusThrownWhenPatientNotFoundTest() throws Exception {
		mockMvc.perform(get("/diabgnoz/patients/10"))
		.andExpect(status().isNotFound())
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof PatientNotFoundException));
	}
		
}
