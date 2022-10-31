package com.bridgelabz.sellerdetails.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OtpDTO {
    private String emailAddress;
    private Long otp;
    
	public String getEmailAddress() {
		return emailAddress;
	}
	public Long getOtp() {
		return otp;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public void setOtp(Long otp) {
		this.otp = otp;
	}
    
}
