package com.webzio.springboot.service.impl;

import com.webzio.springboot.utils.ExceptionMessageAccessor;
import com.webzio.springboot.exceptions.RegistrationException;
import com.webzio.springboot.repository.UserRepository;
import com.webzio.springboot.security.dto.RegistrationRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Service that validates user registration details during the sign-up process.
 * Ensures that the provided email and username are unique before completing registration.
 */
@Service
public class UserValidationService {

	private static final String EMAIL_ALREADY_EXISTS = "email_already_exists";
	private static final Logger logger = LogManager.getLogger(UserValidationService.class);
	private static final String USERNAME_ALREADY_EXISTS = "username_already_exists";

	private final UserRepository userRepository;

	private final ExceptionMessageAccessor exceptionMessageAccessor;

	/**
	 * Constructor to initialize the UserValidationService with required dependencies.
	 *
	 * @param userRepository Repository for querying user data.
	 * @param exceptionMessageAccessor Accessor for getting exception messages.
	 */
    public UserValidationService(UserRepository userRepository, ExceptionMessageAccessor exceptionMessageAccessor) {
        this.userRepository = userRepository;
        this.exceptionMessageAccessor = exceptionMessageAccessor;
    }

	/**
	 * Validates the registration request by checking if the email and username are unique.
	 *
	 * @param registrationRequest The registration request containing user details.
	 * @throws RegistrationException if the email or username already exists in the system.
	 */
    public void validateUser(RegistrationRequest registrationRequest) {

		final String email = registrationRequest.getEmail();
		final String username = registrationRequest.getUsername();

		checkEmail(email);
		checkUsername(username);
	}

	private void checkUsername(String username) {

		final boolean existsByUsername = userRepository.existsByEmail(username);

		if (existsByUsername) {

			logger.warn("{} is already being used!", username);

			final String existsUsername = exceptionMessageAccessor.getMessage(null, USERNAME_ALREADY_EXISTS);
			throw new RegistrationException(existsUsername);
		}

	}

	private void checkEmail(String email) {

		final boolean existsByEmail = userRepository.existsByEmail(email);

		if (existsByEmail) {

			logger.warn("{} is already being used!", email);

			final String existsEmail = exceptionMessageAccessor.getMessage(null, EMAIL_ALREADY_EXISTS);
			throw new RegistrationException(existsEmail);
		}
	}

}
