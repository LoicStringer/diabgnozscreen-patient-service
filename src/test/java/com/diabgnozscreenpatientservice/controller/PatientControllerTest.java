package com.diabgnozscreenpatientservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.diabgnozscreenpatientservice.dto.PatientDto;
import com.diabgnozscreenpatientservice.exception.PatientIdCoherenceException;
import com.diabgnozscreenpatientservice.exception.PatientNotFoundException;
import com.diabgnozscreenpatientservice.mapper.PatientMapper;
import com.diabgnozscreenpatientservice.model.Patient;
import com.diabgnozscreenpatientservice.service.PatientService;

@DisplayName("Patient controller unit tests")
@ExtendWith(MockitoExtension.class)
public class PatientControllerTest {
	
	@Mock
	private PatientService patientService;
	
	@Mock
	private PatientMapper patientMapper;
	
	@InjectMocks
	private PatientController patientController;
	
	private static PatientDto testedPatientDto;
	private static PatientDto testedPatientDtoTwo;
	private static PatientDto testedPatientDtoThree;
	private static List<PatientDto> testedPatientDtosList;
	private static Page<PatientDto> testedPatientDtosPage;
	private static List<PatientDto> homonymousPatientsListDto;
	
	private static Patient testedPatient;
	private static Patient testedPatientTwo;
	private static Patient testedPatientThree;
	private static List<Patient> testedPatientsList;
	private static Page<Patient> testedPatientsPage;
	private static List<Patient> homonymousPatientsList;

	private static Pageable testedPageable;

	@BeforeAll
	static void setUpForTests() {
		initTestDtoBeans();
		setUpTestDtoBeans();
		initTestBeans();
		setUpTestBeans();
		setUpTestPatientDtosLists() ;
		setUpTestPatientsLists();
		testedPatientsPage = new PageImpl<>(testedPatientsList);
		testedPatientDtosPage = new PageImpl<>(testedPatientDtosList);
		testedPageable = PageRequest.of(0, 1);
	}
	
	@Nested
	@Tag("NominalCasesTests")
	@DisplayName("Nominal cases checking")
	class NominalCasesTests {
		
		@Test
		void getAllPatientsPageTest() {
			when(patientService.getAllPatientsList(testedPageable)).thenReturn(testedPatientsPage);
			when(patientMapper.patientToPatientDto(testedPatient)).thenReturn(testedPatientDto);
			when(patientMapper.patientToPatientDto(testedPatientTwo)).thenReturn(testedPatientDtoTwo);
			when(patientMapper.patientToPatientDto(testedPatientThree)).thenReturn(testedPatientDtoThree);
			assertEquals(ResponseEntity.ok(testedPatientDtosPage), patientController.getAllPatientsList(testedPageable));
		}
		
		@Test
		void getOnePatientTest() throws PatientNotFoundException {
			when(patientService.getOnePatient(any(Long.class))).thenReturn(testedPatient);
			when(patientMapper.patientToPatientDto(testedPatient)).thenReturn(testedPatientDto);
			assertEquals(ResponseEntity.ok(testedPatientDto),patientController.getOnePatient(1L));
		}
		
		@Test
		void addPatientTest() {
			when(patientService.addPatient(testedPatient)).thenReturn(testedPatient);
			when(patientMapper.patientDtoToPatient(testedPatientDto)).thenReturn(testedPatient);
			assertEquals(ResponseEntity.ok(testedPatientDto),patientController.addPatient(testedPatientDto));
		}
		
		@Test 
		void updatePatient() throws PatientNotFoundException, PatientIdCoherenceException {
			when(patientService.updatePatient(any(Long.class), any(Patient.class))).thenReturn(testedPatient);
			when(patientMapper.patientDtoToPatient(testedPatientDto)).thenReturn(testedPatient);
			assertEquals(ResponseEntity.ok(testedPatientDto),patientController.updatePatient(1L, testedPatientDto));
		}
		
		@Test
		void getPatientsByNameListTest() {
			when(patientService.getPatientsByNameList(any(String.class))).thenReturn(homonymousPatientsList);
			when(patientMapper.patientsListToPatientDtosList(homonymousPatientsList)).thenReturn(homonymousPatientsListDto);
			assertEquals(ResponseEntity.ok(homonymousPatientsListDto),patientController.getPatientsByName("Johnson"));
		}
	}
	
	@Nested
	@Tag("ExceptionsTests")
	@DisplayName("Exceptions Checking")
	class ExceptionsTests {
		
		@Test
		void isExpectedExceptionThrownWhenPatientNotFoundTest() throws PatientNotFoundException {
			when(patientService.getOnePatient(any(Long.class))).thenThrow(PatientNotFoundException.class);
			assertThrows(PatientNotFoundException.class, ()->patientController.getOnePatient(1L));
		}
		
		@Test
		void isExpectedExceptionThrownWHenPatientIdIsNotCoherentTest() throws PatientNotFoundException, PatientIdCoherenceException {
			when(patientService.updatePatient(2L, testedPatient)).thenThrow(PatientIdCoherenceException.class);
			when(patientMapper.patientDtoToPatient(testedPatientDto)).thenReturn(testedPatient);
			assertThrows(PatientIdCoherenceException.class, ()->patientController.updatePatient(2L, testedPatientDto));
		}
	}

	private static void initTestDtoBeans() {
		testedPatientDto = new PatientDto();
		testedPatientDtoTwo = new PatientDto();
		testedPatientDtoThree = new PatientDto();
		testedPatientDtosList = new ArrayList<PatientDto>();
		homonymousPatientsListDto = new ArrayList<PatientDto>();
	}

	private static void setUpTestDtoBeans() {
		testedPatientDto.setPatientId(1L);
		testedPatientDto.setPatientLastName("Smith");
		testedPatientDtoTwo.setPatientId(2L);
		testedPatientDtoTwo.setPatientLastName("Johnson");
		testedPatientDtoThree.setPatientId(3L);
		testedPatientDtoThree.setPatientLastName("Johnson");
	}
	
	private static void setUpTestPatientDtosLists() {
		testedPatientDtosList.add(testedPatientDto);
		testedPatientDtosList.add(testedPatientDtoTwo);
		testedPatientDtosList.add(testedPatientDtoThree);
		homonymousPatientsListDto.add(testedPatientDtoTwo);
		homonymousPatientsListDto.add(testedPatientDtoThree);
	}
	
	private static void initTestBeans() {
		testedPatient = new Patient();
		testedPatientTwo = new Patient();
		testedPatientThree = new Patient();
		testedPatientsList = new ArrayList<Patient>();
		homonymousPatientsList = new ArrayList<Patient>();
	}

	private static void setUpTestBeans() {
		testedPatient.setPatientId(1L);
		testedPatient.setPatientLastName("Smith");
		testedPatientTwo.setPatientId(2L);
		testedPatientTwo.setPatientLastName("Johnson");
		testedPatientThree.setPatientId(3L);
		testedPatientThree.setPatientLastName("Johnson");
	}
	
	private static void setUpTestPatientsLists() {
		testedPatientsList.add(testedPatient);
		testedPatientsList.add(testedPatientTwo);
		testedPatientsList.add(testedPatientThree);
		homonymousPatientsList.add(testedPatientTwo);
		homonymousPatientsList.add(testedPatientThree);
	}
}
