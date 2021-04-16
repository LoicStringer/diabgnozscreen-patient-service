package com.diabgnozscreenpatientservice.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.diabgnozscreenpatientservice.exception.PatientNotFoundException;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PatientControllerTestIT {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void getAllPatientsPageTest() throws Exception {
		mockMvc.perform(get("/diabgnoz/patients"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content.length()").value(4))
				.andExpect(jsonPath("$.content.[0].patientLastName").value("Jordan"));	
	}
	
	@Test
	void getOnePatientTest() throws Exception {
		mockMvc.perform(get("/diabgnoz/patients/2"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.patientLastName").value("Tyson"));
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
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof PatientNotFoundException))
		.andExpect(result -> assertEquals("This patient is not registered.", result.getResolvedException().getMessage()));
	}
	
	
}
