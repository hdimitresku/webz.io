package com.webzio.springboot.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO for representing the response after a user registers successfully.
 * This class contains a message that confirms the result of the registration process.
 */
public class RegistrationResponse {

	private String message;

	public RegistrationResponse(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
