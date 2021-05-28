package com.diabgnozscreenpatientservice.model;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.diabgnozscreenpatientservice.utility.PatientGenderEnum;

@Component
public class Patient {

	private Long patientId;
	private String patientLastName;
	private String patientFirstName;
	private LocalDate patientBirthDate;
	private PatientGenderEnum patientGender;
	private String patientAddress;
	private String patientPhoneNumber;
	private String patientEmail;
	
	public Patient() {
	}

	public Patient(Long patientId, String patientLastName, String patientFirstName, LocalDate patientBirthDate,
			PatientGenderEnum patientGender, String patientAddress, String patientPhoneNumber, String patientEmail) {
		super();
		this.patientId = patientId;
		this.patientLastName = patientLastName;
		this.patientFirstName = patientFirstName;
		this.patientBirthDate = patientBirthDate;
		this.patientGender = patientGender;
		this.patientAddress = patientAddress;
		this.patientPhoneNumber = patientPhoneNumber;
		this.patientEmail = patientEmail;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public String getPatientLastName() {
		return patientLastName;
	}

	public void setPatientLastName(String patientLastName) {
		this.patientLastName = patientLastName;
	}

	public String getPatientFirstName() {
		return patientFirstName;
	}

	public void setPatientFirstName(String patientFirstName) {
		this.patientFirstName = patientFirstName;
	}

	public LocalDate getPatientBirthDate() {
		return patientBirthDate;
	}

	public void setPatientBirthDate(LocalDate patientBirthDate) {
		this.patientBirthDate = patientBirthDate;
	}

	public PatientGenderEnum getpatientGender() {
		return patientGender;
	}

	public void setpatientGender(PatientGenderEnum patientGender) {
		this.patientGender = patientGender;
	}

	public String getPatientAddress() {
		return patientAddress;
	}

	public void setPatientAddress(String patientAddress) {
		this.patientAddress = patientAddress;
	}

	public String getPatientPhoneNumber() {
		return patientPhoneNumber;
	}

	public void setPatientPhoneNumber(String patientPhoneNumber) {
		this.patientPhoneNumber = patientPhoneNumber;
	}

	public String getPatientEmail() {
		return patientEmail;
	}

	public void setPatientEmail(String patientEmail) {
		this.patientEmail = patientEmail;
	}

	

}