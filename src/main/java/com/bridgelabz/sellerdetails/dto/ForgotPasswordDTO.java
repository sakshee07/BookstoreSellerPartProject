package com.bridgelabz.sellerdetails.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ForgotPasswordDTO {
    private String emailAddress;
    private String newPassword;
	public String getEmailAddress() {
		return emailAddress;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
