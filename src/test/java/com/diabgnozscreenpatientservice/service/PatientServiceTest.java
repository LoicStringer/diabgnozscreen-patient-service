package com.diabgnozscreenpatientservice.service;

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

import com.diabgnozscreenpatientservice.dao.PatientDao;
import com.diabgnozscreenpatientservice.exception.PatientIdMismatchException;
import com.diabgnozscreenpatientservice.exception.PatientIdSettingNotAllowedException;
import com.diabgnozscreenpatientservice.exception.PatientNotFoundException;
import com.diabgnozscreenpatientservice.model.Patient;

@DisplayName("PatientService unit tests")
@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

	@InjectMocks
	private PatientService patientService;

	@Mock
	private PatientDao patientDao;

	private static Patient testedPatient;
	private static Patient testedPatientTwo;
	private static Patient testedPatientThree;
	private static List<Patient> testedPatientsList;
	private static Page<Patient> testedPatientsPage;
	private static Pageable testedPageable;
	private static List<Patient> homonymousPatientsList;

	@BeforeAll
	static void setUpForTests() {
		initTestBeans();
		setUpTestBeans();
		testedPatientsList.add(testedPatient);
		testedPatientsList.add(testedPatientTwo);
		homonymousPatientsList.add(testedPatientTwo);
		homonymousPatientsList.add(testedPatientThree);
		testedPatientsPage = new PageImpl<>(testedPatientsList);
		testedPageable = PageRequest.of(0, 1);
	}

	@Nested
	@Tag("NominalCasesTests")
	@DisplayName("Nominal cases checking")
	class NominalCasesTests {

		@Test
		void getAllPatientsPageTest() throws PatientNotFoundException {
			when(patientDao.getAllPatientsList(null,testedPageable)).thenReturn(testedPatientsPage);
			assertEquals(testedPatientsPage, patientService.getAllPatientsList(null,testedPageable));
		}

		@Test
		void getOnePatientTest() throws PatientNotFoundException {
			when(patientDao.getOnePatient(any(Long.class))).thenReturn(testedPatient);
			assertEquals("Smith", patientService.getOnePatient(1L).getPatientLastName());
		}

		@Test
		void addPatientTest() throws PatientIdSettingNotAllowedException {
			when(patientDao.addPatient(testedPatient)).thenReturn(testedPatient);
			assertEquals("Smith", patientService.addPatient(testedPatient).getPatientLastName());
		}
		
		@Test
		void updatePatientTest() throws PatientNotFoundException, PatientIdMismatchException{
			when(patientDao.updatePatient(any(Long.class), any(Patient.class))).thenReturn(testedPatient);
			assertEquals("Smith", patientService.updatePatient(1L, testedPatient).getPatientLastName());
		}

	}

	@Nested
	@Tag("ExceptionsTests")
	@DisplayName("Exceptions Checking")
	class ExceptionsTests {

		@Test
		void isExpectedExceptionThrownWhenPatientIsNotFoundTest() throws PatientNotFoundException {
			when(patientDao.getOnePatient(any(Long.class))).thenThrow(PatientNotFoundException.class);
			assertThrows(PatientNotFoundException.class, () -> patientService.getOnePatient(1L));
		}
		
		@Test
		void isExpectedExceptionThrownWhenPatientIdMismatchTest() throws PatientNotFoundException, PatientIdMismatchException {
			when(patientDao.updatePatient(any(Long.class), any(Patient.class))).thenThrow(PatientIdMismatchException.class);
			assertThrows(PatientIdMismatchException.class, ()->patientService.updatePatient(1L, testedPatient));
		}
		
		@Test
		void isExpectedExceptionThrownWhenPatientIdSetBeforeCreateTest() throws PatientIdSettingNotAllowedException {
			when(patientDao.addPatient(testedPatient)).thenThrow(PatientIdSettingNotAllowedException.class);
			assertThrows(PatientIdSettingNotAllowedException.class, ()-> patientService.addPatient(testedPatient));
		}
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
}
