package com.bridgelabz.sellerdetails.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class SellerDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SellerId", nullable = false)
    private Long id;
    private String businessName;
    private String sellerName;
    private String gstn;
    private String sellerWebsite;
    private String emailAddress;
    private String password;
    private String contactNumber;
    private boolean verify;
    private LocalDateTime createdTimeStamp;
    private LocalDateTime updatedTimeStamp;


    public SellerDetails(String businessName, String sellerName, String gstn, String sellerWebsite, String emailAddress, String password, String contactNumber, boolean verify, LocalDateTime createdTime, LocalDateTime updatedTime){
        this.businessName = businessName;
        this.sellerName = sellerName;
        this.gstn = gstn;
        this.sellerWebsite = sellerWebsite;
        this.emailAddress = emailAddress;
        this.password = password;
        this.contactNumber = contactNumber;
        this.verify = verify;
        this.createdTimeStamp = createdTime;
        this.updatedTimeStamp = updatedTime;
    }

    
	public SellerDetails() {
		super();
	}


	public Long getId() {
		return id;
	}


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


	public String getPassword() {
		return password;
	}


	public String getContactNumber() {
		return contactNumber;
	}


	public boolean isVerify() {
		return verify;
	}


	public LocalDateTime getCreatedTimeStamp() {
		return createdTimeStamp;
	}


	public LocalDateTime getUpdatedTimeStamp() {
		return updatedTimeStamp;
	}


	public void setId(Long id) {
		this.id = id;
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


	public void setPassword(String password) {
		this.password = password;
	}


	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}


	public void setVerify(boolean verify) {
		this.verify = verify;
	}


	public void setCreatedTimeStamp(LocalDateTime createdTimeStamp) {
		this.createdTimeStamp = createdTimeStamp;
	}


	public void setUpdatedTimeStamp(LocalDateTime updatedTimeStamp) {
		this.updatedTimeStamp = updatedTimeStamp;
	}
    
}
