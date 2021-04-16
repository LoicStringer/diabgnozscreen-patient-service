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

import com.diabgnozscreenpatientservice.utility.PatientGender;

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
	
	@NotBlank
	@Column(name="patient_birthdate",nullable=false)
	private LocalDate patientBirthDate;
	
	@NotBlank
	@Enumerated(EnumType.STRING)
	@Column(name="patient_gender",nullable=false)
	private PatientGender patientGender;
	
	@Column(name="patient_address")
	private String patientAddress;
	
	@Column(name="patient_phoneNumber")
	private String patientPhoneNumber;
	
	@Column(name="patient_email")
	private String patientEmail;

	public PatientEntity() {
	}

	public PatientEntity(Long patientId, @NotBlank String patientLastName, @NotBlank String patientFirstName,
			@NotBlank LocalDate patientBirthDate, @NotBlank PatientGender patientGender, String patientAddress,
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

	public PatientGender getpatientGender() {
		return patientGender;
	}

	public void setpatientGender(PatientGender patientGender) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((patientAddress == null) ? 0 : patientAddress.hashCode());
		result = prime * result + ((patientBirthDate == null) ? 0 : patientBirthDate.hashCode());
		result = prime * result + ((patientEmail == null) ? 0 : patientEmail.hashCode());
		result = prime * result + ((patientFirstName == null) ? 0 : patientFirstName.hashCode());
		result = prime * result + ((patientId == null) ? 0 : patientId.hashCode());
		result = prime * result + ((patientLastName == null) ? 0 : patientLastName.hashCode());
		result = prime * result + ((patientPhoneNumber == null) ? 0 : patientPhoneNumber.hashCode());
		result = prime * result + ((patientGender == null) ? 0 : patientGender.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PatientEntity other = (PatientEntity) obj;
		if (patientAddress == null) {
			if (other.patientAddress != null)
				return false;
		} else if (!patientAddress.equals(other.patientAddress))
			return false;
		if (patientBirthDate == null) {
			if (other.patientBirthDate != null)
				return false;
		} else if (!patientBirthDate.equals(other.patientBirthDate))
			return false;
		if (patientEmail == null) {
			if (other.patientEmail != null)
				return false;
		} else if (!patientEmail.equals(other.patientEmail))
			return false;
		if (patientFirstName == null) {
			if (other.patientFirstName != null)
				return false;
		} else if (!patientFirstName.equals(other.patientFirstName))
			return false;
		if (patientId == null) {
			if (other.patientId != null)
				return false;
		} else if (!patientId.equals(other.patientId))
			return false;
		if (patientLastName == null) {
			if (other.patientLastName != null)
				return false;
		} else if (!patientLastName.equals(other.patientLastName))
			return false;
		if (patientPhoneNumber == null) {
			if (other.patientPhoneNumber != null)
				return false;
		} else if (!patientPhoneNumber.equals(other.patientPhoneNumber))
			return false;
		if (patientGender != other.patientGender)
			return false;
		return true;
	}
	
}