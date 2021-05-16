package com.diabgnozscreenpatientservice.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.diabgnozscreenpatientservice.controller.PatientController;
import com.diabgnozscreenpatientservice.dto.PatientDto;
import com.diabgnozscreenpatientservice.exception.PatientIdMismatchException;
import com.diabgnozscreenpatientservice.exception.PatientIdSettingNotAllowedException;
import com.diabgnozscreenpatientservice.exception.PatientNotFoundException;
import com.diabgnozscreenpatientservice.utility.PatientGenderEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ActiveProfiles("test")
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

	@Nested
	@Tag("NominalCasesTests")
	@DisplayName("Nominal cases checking")
	class NominalCasesTests {

		@Test
		void getAllPatientsPageTest() throws Exception {
			mockMvc.perform(get("/diabgnoz/patients?patientLastName=")).andExpect(status().isOk())
					.andExpect(jsonPath("$.content.length()").value(4))
					.andExpect(jsonPath("$.content.[0].patientLastName").value("TestNone"));
		}

		@Test
		void getOnePatientTest() throws Exception {
			mockMvc.perform(get("/diabgnoz/patients/2")).andExpect(status().isOk())
					.andExpect(jsonPath("$.patientLastName").value("TestBorderline"));
		}

		@Test
		void addPatientTest() throws JsonProcessingException, Exception {
			LocalDate birthDate = LocalDate.of(1963, 2, 17);
			PatientDto patientToAdd = new PatientDto();
			patientToAdd.setPatientLastName("Jordan");
			patientToAdd.setPatientFirstName("Michael");
			patientToAdd.setPatientBirthDate(birthDate);
			patientToAdd.setpatientGender(PatientGenderEnum.M);

			mockMvc.perform(post("/diabgnoz/patients").content(objectMapper.writeValueAsString(patientToAdd))
					.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
					.andExpect(jsonPath("$.patientLastName").value("Jordan"));

			assertEquals("Jordan", 
					patientController.getAllPatientsList("Jordan", 
							PageRequest.of(0, 1)).getBody().getContent().get(0).getPatientLastName());
		}

		@Test
		void updatePatientTest() throws JsonProcessingException, Exception {

			PatientDto updatedPatient = patientController.getOnePatient(4L).getBody();
			updatedPatient.setPatientFirstName("Allyson");

			mockMvc.perform(put("/diabgnoz/patients/4").content(objectMapper.writeValueAsString(updatedPatient))
					.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
					.andExpect(jsonPath("$.patientFirstName").value("Allyson"));

			assertEquals("Allyson", patientController.getOnePatient(4L).getBody().getPatientFirstName());
		}

	}

	@Nested
	@Tag("ExceptionsTests")
	@DisplayName("Exceptions Checking")
	class ExceptionsTests {

		@Test
		void isExpectedExceptionAndStatusThrownWhenPatientNotFoundTest() throws Exception {
			mockMvc.perform(get("/diabgnoz/patients/10")).andExpect(status().isNotFound())
					.andExpect(result -> assertTrue(result.getResolvedException() instanceof PatientNotFoundException));
		}

		@Test
		void isExpectedExceptionThrownWhenPatientIdMismatchTest() throws Exception {
			PatientDto updatedPatient = patientController.getOnePatient(4L).getBody();
			updatedPatient.setPatientFirstName("Allyson");

			mockMvc.perform(put("/diabgnoz/patients/3").content(objectMapper.writeValueAsString(updatedPatient))
					.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isConflict()).andExpect(
							result -> assertTrue(result.getResolvedException() instanceof PatientIdMismatchException));
		}
		
		@Test
		void isExpectedExceptionThrownWhenPatientIdSetBeforeCreateTest() throws JsonProcessingException, Exception {
			PatientDto patientToAdd = new PatientDto();
			patientToAdd.setPatientId(5L);

			mockMvc.perform(post("/diabgnoz/patients")
					.content(objectMapper.writeValueAsString(patientToAdd))
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isForbidden())
					.andExpect(result -> assertTrue(result.getResolvedException() instanceof PatientIdSettingNotAllowedException));
		}

	}
}
