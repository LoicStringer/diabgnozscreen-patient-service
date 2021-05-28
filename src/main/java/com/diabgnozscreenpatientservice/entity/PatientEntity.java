package com.diabgnozscreenpatientservice.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.diabgnozscreenpatientservice.utility.PatientGenderEnum;

@Entity
@Table(name="patient")
public class PatientEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="patient_id")
	private Long patientId;
	
	@NotBlank
	@Column(name="patient_lastname",length=50,nullable=false)
	private String patientLastName;
	
	@NotBlank
	@Column(name="patient_firstname",length=50,nullable=false)
	private String patientFirstName;
	
	@NotNull
	@Column(name="patient_birthdate",nullable=false)
	private LocalDate patientBirthDate;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name="patient_gender",length=1,nullable=false)
	private PatientGenderEnum patientGender;
	
	@Column(name="patient_address")
	private String patientAddress;
	
	@Column(name="patient_phoneNumber")
	private String patientPhoneNumber;
	
	@Column(name="patient_email")
	private String patientEmail;

	public PatientEntity() {
	}

	public PatientEntity(Long patientId, @NotBlank String patientLastName, @NotBlank String patientFirstName,
			@NotBlank LocalDate patientBirthDate, @NotBlank PatientGenderEnum patientGender, String patientAddress,
			String patientPhoneNumber, String patientEmail) {
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