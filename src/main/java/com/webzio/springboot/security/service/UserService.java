package com.webzio.springboot.security.service;

import com.webzio.springboot.model.User;
import com.webzio.springboot.security.dto.AuthenticatedUserDto;
import com.webzio.springboot.security.dto.RegistrationRequest;
import com.webzio.springboot.security.dto.RegistrationResponse;


public interface UserService {

	User findByUsername(String username);

	User findById(Long username);

	RegistrationResponse registration(RegistrationRequest registrationRequest);

	AuthenticatedUserDto findAuthenticatedUserByUsername(String username);

	String getAuthenticatedUsername();
}
