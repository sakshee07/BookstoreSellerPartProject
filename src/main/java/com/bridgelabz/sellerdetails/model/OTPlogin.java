package com.bridgelabz.sellerdetails.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Data
@Table(name = "otp")
public class OTPlogin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
     Long id;
     String emailAddress;
     Long Otp;
     LocalDateTime otpTime;
     LocalDateTime otpExpiredTime;
     
     
	public OTPlogin() {
		super();
	}
	public Long getId() {
		return id;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public Long getOtp() {
		return Otp;
	}
	public LocalDateTime getOtpTime() {
		return otpTime;
	}
	public LocalDateTime getOtpExpiredTime() {
		return otpExpiredTime;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public void setOtp(Long otp) {
		Otp = otp;
	}
	public void setOtpTime(LocalDateTime otpTime) {
		this.otpTime = otpTime;
	}
	public void setOtpExpiredTime(LocalDateTime otpExpiredTime) {
		this.otpExpiredTime = otpExpiredTime;
	}
    
    
}
