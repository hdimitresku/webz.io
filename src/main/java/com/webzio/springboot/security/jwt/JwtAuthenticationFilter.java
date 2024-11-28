package com.webzio.springboot.security.jwt;

import com.webzio.springboot.security.service.UserDetailsServiceImpl;
import com.webzio.springboot.security.utils.SecurityConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

/**
 * Filter that processes incoming HTTP requests, extracts JWT tokens from the authorization header,
 * and validates the token to authenticate the user.
 */
@Service
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenManager jwtTokenManager;

	private static final Logger logger = LogManager.getLogger(JwtAuthenticationFilter.class);
	private final UserDetailsServiceImpl userDetailsService;

	/**
	 * Constructor to initialize the JWT authentication filter with dependencies.
	 *
	 * @param jwtTokenManager The token manager for validating JWT tokens.
	 * @param userDetailsService Service for loading user details.
	 */
    public JwtAuthenticationFilter(JwtTokenManager jwtTokenManager, UserDetailsServiceImpl userDetailsService) {
        this.jwtTokenManager = jwtTokenManager;
        this.userDetailsService = userDetailsService;
    }

	/**
	 * Extracts the JWT token from the request header and authenticates the user if the token is valid.
	 *
	 * @param request The HttpServletRequest containing the token in the header.
	 * @param response The HttpServletResponse where authentication success or failure will be processed.
	 * @param chain The filter chain to continue the processing of the request.
	 * @throws IOException If an input error occurs while processing the request.
	 * @throws ServletException If a servlet-related error occurs.
	 */
    @Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		final String header = request.getHeader(SecurityConstants.HEADER_STRING);

		String username = null;
		String authToken = null;
		if (Objects.nonNull(header) && header.startsWith(SecurityConstants.TOKEN_PREFIX)) {

			authToken = header.replace(SecurityConstants.TOKEN_PREFIX, Strings.EMPTY);

			try {
				username = jwtTokenManager.getUsernameFromToken(authToken);
			}
			catch (Exception e) {
				logger.error("Authentication Exception : {}", e.getMessage());
				chain.doFilter(request, response);
				return;
			}
		}

		final SecurityContext securityContext = SecurityContextHolder.getContext();

		final boolean canBeStartTokenValidation = Objects.nonNull(username) && Objects.isNull(securityContext.getAuthentication());

		if (!canBeStartTokenValidation) {
			chain.doFilter(request, response);
			return;
		}

		final UserDetails user = userDetailsService.loadUserByUsername(username);
		final boolean validToken = jwtTokenManager.validateToken(authToken, user.getUsername());

		if (!validToken) {
			chain.doFilter(request, response);
			return;
		}

		final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		securityContext.setAuthentication(authentication);

		logger.info("Authentication successful. Logged in username : {} ", username);

		chain.doFilter(request, response);
	}
}
