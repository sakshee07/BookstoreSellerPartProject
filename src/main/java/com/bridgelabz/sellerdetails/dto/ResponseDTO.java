package com.bridgelabz.sellerdetails.dto;

import com.bridgelabz.sellerdetails.model.SellerDetails;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
public class ResponseDTO {
    String message;
    Object response;
    public ResponseDTO(String message, String response) {
        this.message = message;
        this.response = response;
    }

    public ResponseDTO(String message, SellerDetails response) {
        this.message = message;
        this.response = response;
    }

    public ResponseDTO(String message, List<SellerDetails> response) {
        this.message = message;
        this.response = response;
    }

    public ResponseDTO(String message, Optional<SellerDetails> response) {
        this.message = message;
        this.response = response;
    }

    public ResponseDTO(String message, ResponseDTO response) {
        this.message = message;
        this.response = response;
    }

	public String getMessage() {
		return message;
	}

	public Object getResponse() {
		return response;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setResponse(Object response) {
		this.response = response;
	}
    
    
}
