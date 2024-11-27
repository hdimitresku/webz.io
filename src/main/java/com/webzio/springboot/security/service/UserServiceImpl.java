package com.webzio.springboot.security.service;

import com.webzio.springboot.exceptions.ValidationAdvice;
import com.webzio.springboot.service.impl.UserValidationService;
import com.webzio.springboot.model.User;
import com.webzio.springboot.model.enumerators.UserRole;
import com.webzio.springboot.security.dto.AuthenticatedUserDto;
import com.webzio.springboot.security.dto.RegistrationRequest;
import com.webzio.springboot.security.dto.RegistrationResponse;
import com.webzio.springboot.security.mapper.UserMapper;
import com.webzio.springboot.utils.GeneralMessageAccessor;
import com.webzio.springboot.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

	private static final String REGISTRATION_SUCCESSFUL = "registration_successful";
	private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final UserValidationService userValidationService;
	private final GeneralMessageAccessor generalMessageAccessor;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, UserValidationService userValidationService, GeneralMessageAccessor generalMessageAccessor) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userValidationService = userValidationService;
        this.generalMessageAccessor = generalMessageAccessor;
    }

    @Override
	public User findByUsername(String email) {

		return userRepository.findByUsername(email);
	}

	@Override
	public User findById(Long id) {
		return userRepository.findById(id).orElseThrow();
	}

	@Override
	public RegistrationResponse registration(RegistrationRequest registrationRequest) {

		userValidationService.validateUser(registrationRequest);

		final User user = UserMapper.INSTANCE.convertToUser(registrationRequest);
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setUserRole( registrationRequest.getIsPublisher() ? UserRole.PUBLISHER : UserRole.USER);

		userRepository.save(user);

		final String username = registrationRequest.getUsername();
		final String registrationSuccessMessage = generalMessageAccessor.getMessage(null, REGISTRATION_SUCCESSFUL, username);

		logger.info("{} registered successfully!", username);

		return new RegistrationResponse(registrationSuccessMessage);
	}

	@Override
	public AuthenticatedUserDto findAuthenticatedUserByUsername(String username) {

		final User user = findByUsername(username);

		return UserMapper.INSTANCE.convertToAuthenticatedUserDto(user);
	}

	@Override
	public String getAuthenticatedUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.isAuthenticated()) {
			return authentication.getName();
		}

		return null;
	}
}
