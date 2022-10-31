package com.bridgelabz.sellerdetails.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChangePasswordDTO {
    private String emailAddress;
    private String oldPassword;
    private String password;
	public String getEmailAddress() {
		return emailAddress;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public String getPassword() {
		return password;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public void setPassword(String password) {
		this.password = password;
	}
    
    
}
