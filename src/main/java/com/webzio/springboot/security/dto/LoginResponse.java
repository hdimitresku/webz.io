package com.webzio.springboot.security.dto;


/**
 * DTO for representing the login response.
 * This class contains the JWT token returned after a successful login.
 * The token is used for user authentication in subsequent requests.
 */
public class LoginResponse {

	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LoginResponse(String token) {
		this.token = token;
	}
}
