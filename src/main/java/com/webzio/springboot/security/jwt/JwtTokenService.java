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


@Service
public class JwtTokenService {

	private final UserService userService;
	private static final Logger logger = LogManager.getLogger(JwtTokenService.class);
	private final JwtTokenManager jwtTokenManager;

	private final AuthenticationManager authenticationManager;

    public JwtTokenService(UserService userService, JwtTokenManager jwtTokenManager, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtTokenManager = jwtTokenManager;
        this.authenticationManager = authenticationManager;
    }

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
