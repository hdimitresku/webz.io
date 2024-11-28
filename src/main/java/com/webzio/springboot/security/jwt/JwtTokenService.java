package com.webzio.springboot.security.jwt;

import com.webzio.springboot.security.mapper.UserMapper;
import com.webzio.springboot.security.service.UserService;
import com.webzio.springboot.model.User;
import com.webzio.springboot.security.dto.AuthenticatedUserDto;
import com.webzio.springboot.security.dto.LoginRequest;
import com.webzio.springboot.security.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

/**
 * Service that handles the generation of JWT tokens during user login.
 * It authenticates the user, generates the JWT token upon successful login, and returns the token in the response.
 */
@Service
public class JwtTokenService {

	private final UserService userService;
	private static final Logger logger = LogManager.getLogger(JwtTokenService.class);
	private final JwtTokenManager jwtTokenManager;

	private final AuthenticationManager authenticationManager;

	/**
	 * Constructor to initialize the JwtTokenService with the required dependencies.
	 *
	 * @param userService Service for retrieving user details.
	 * @param jwtTokenManager The manager responsible for creating and validating JWT tokens.
	 * @param authenticationManager The manager for authenticating users.
	 */
    public JwtTokenService(UserService userService, JwtTokenManager jwtTokenManager, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtTokenManager = jwtTokenManager;
        this.authenticationManager = authenticationManager;
    }

	/**
	 * Authenticates the user using the provided login credentials and generates a JWT token upon successful authentication.
	 * The token is returned in a LoginResponse DTO.
	 *
	 * @param loginRequest Contains the user's username and password for authentication.
	 * @return A LoginResponse DTO containing the generated JWT token.
	 */
    public LoginResponse getLoginResponse(LoginRequest loginRequest) {

		final String username = loginRequest.getUsername();
		final String password = loginRequest.getPassword();

		final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);

		authenticationManager.authenticate(usernamePasswordAuthenticationToken);

		final AuthenticatedUserDto authenticatedUserDto = userService.findAuthenticatedUserByUsername(username);

		final User user = UserMapper.INSTANCE.convertToUser(authenticatedUserDto);
		final String token = jwtTokenManager.generateToken(user);

		logger.info("{} has successfully logged in!", user.getUsername());

		return new LoginResponse(token);
	}

}
