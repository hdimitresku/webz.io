package com.webzio.springboot.security.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * Entry point that handles unauthorized access attempts by sending an HTTP 401 Unauthorized response.
 * This is typically used to handle scenarios where the user is unauthenticated.
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	/**
	 * Responds to an authentication error by returning a 401 Unauthorized response with a JSON message.
	 *
	 * @param request The HttpServletRequest that triggered the authentication exception.
	 * @param response The HttpServletResponse where the error message is written.
	 * @param authException The exception that was thrown during authentication.
	 * @throws IOException If an input or output error occurs during the handling of the response.
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
			throws IOException {
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().write("{\"error\": \"Unauthorized\", \"message\": \"Authentication required\", \"path\": \"" + request.getRequestURI() + "\"}");
	}
}