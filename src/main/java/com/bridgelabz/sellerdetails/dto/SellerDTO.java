package com.bridgelabz.sellerdetails.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
public class SellerDTO {
    @Pattern(regexp = "^[A-Z]{1}[a-zA-Z\\s]{2,}$", message="Invalid Business Name(First Letter Should be in Upper Case and min 3 Characters.)")
    String businessName;
    @Pattern(regexp = "^[A-Z]{1}[a-zA-Z\\s]{2,}$", message="Invalid Seller Name(First Letter Should be in Upper Case and min 3 Characters.)")
    String sellerName;
    @NotNull(message = "GSTN cannot be Null")
    String gstn;
    @NotNull(message = "Website cannot be Null")
    String sellerWebsite;
    @NotNull(message = "Email Address cannot be Null")
    String emailAddress;
    /*
     * (?=.*[A-Z]) represents an upper case character that must occur at least once.
     * (?=.*[0-9]) represents a digit must occur at least once.
     * (?+.*[@#$%^&*()] represent the special symbol at least once.
     * (?=.*[a-zA-z0-9]) represents a lower case character or number must occur at least once.
     * {8,} represents at least 8 or more characters.
     */
    @Pattern(regexp = "^[a-zA-Z\\s]{3,}$", message="Invalid User Name (Minimum 3 Characters and no numerical.)")
    String userName;
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&*()-+=])([a-zA-Z0-9@._-]).{8,}$", message="Invalid Password\n(1. Upper case character that must occur at least once.\n" +
            "2. A digit must occur at least once.\n3. Special symbol at least once.\n4. lower case character or number must occur at least once.\n5. Represents at least 8 or more characters.)")
    String password;
    @Pattern(regexp = "^[1-9]{2}[0-9]{10}$", message="Invalid Contact Number(Should have Country Code and must be 10 digit number) example: 919234567890")
    String contactNumber;
	public String getBusinessName() {
		return businessName;
	}
	public String getSellerName() {
		return sellerName;
	}
	public String getGstn() {
		return gstn;
	}
	public String getSellerWebsite() {
		return sellerWebsite;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public String getUserName() {
		return userName;
	}
	public String getPassword() {
		return password;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public void setGstn(String gstn) {
		this.gstn = gstn;
	}
	public void setSellerWebsite(String sellerWebsite) {
		this.sellerWebsite = sellerWebsite;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
    
    
}
