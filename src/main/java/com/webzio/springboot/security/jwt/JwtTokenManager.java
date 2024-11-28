package com.webzio.springboot.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.webzio.springboot.model.User;
import com.webzio.springboot.model.enumerators.UserRole;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Utility class responsible for managing JWT tokens: generating, validating, and extracting information from them.
 * This class provides methods to generate a JWT token for a user, validate the token, and retrieve user information from the token.
 */
@Component
public class JwtTokenManager {

	private final JwtProperties jwtProperties;

	/**
	 * Constructor to initialize JwtTokenManager with JWT configuration properties.
	 *
	 * @param jwtProperties Configuration properties for JWT such as issuer, secret key, and expiration time.
	 */
    public JwtTokenManager(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

	/**
	 * Generates a JWT token for the specified user.
	 * The generated token contains the user's username, role, issued time, and expiration time.
	 *
	 * @param user The user for whom the token will be generated.
	 * @return A JWT token as a string.
	 */
    public String generateToken(User user) {

		final String username = user.getUsername();
		final UserRole userRole = user.getUserRole();

		//@formatter:off
		return JWT.create()
				.withSubject(username)
				.withIssuer(jwtProperties.getIssuer())
				.withClaim("role", userRole.name())
				.withIssuedAt(new Date())
				.withExpiresAt(new Date(System.currentTimeMillis() + jwtProperties.getExpirationMinute() * 60 * 1000))
				.sign(Algorithm.HMAC256(jwtProperties.getSecretKey().getBytes()));
		//@formatter:on
	}

	/**
	 * Extracts the username from the given JWT token.
	 *
	 * @param token The JWT token to extract the username from.
	 * @return The username embedded in the JWT token.
	 */
	public String getUsernameFromToken(String token) {

		final DecodedJWT decodedJWT = getDecodedJWT(token);

		return decodedJWT.getSubject();
	}

	public boolean validateToken(String token, String authenticatedUsername) {

		final String usernameFromToken = getUsernameFromToken(token);

		final boolean equalsUsername = usernameFromToken.equals(authenticatedUsername);
		final boolean tokenExpired = isTokenExpired(token);

		return equalsUsername && !tokenExpired;
	}

	/**
	 * Checks whether the JWT token has expired.
	 *
	 * @param token The JWT token to check.
	 * @return True if the token is expired, false otherwise.
	 */
	private boolean isTokenExpired(String token) {

		final Date expirationDateFromToken = getExpirationDateFromToken(token);
		return expirationDateFromToken.before(new Date());
	}

	/**
	 * Retrieves the expiration date from the JWT token.
	 *
	 * @param token The JWT token to extract the expiration date from.
	 * @return The expiration date of the token.
	 */
	private Date getExpirationDateFromToken(String token) {

		final DecodedJWT decodedJWT = getDecodedJWT(token);

		return decodedJWT.getExpiresAt();
	}

	/**
	 * Decodes the JWT token using the HMAC256 algorithm and the secret key.
	 *
	 * @param token The JWT token to decode.
	 * @return A DecodedJWT object representing the decoded token.
	 */
	private DecodedJWT getDecodedJWT(String token) {

		final JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(jwtProperties.getSecretKey().getBytes())).build();

		return jwtVerifier.verify(token);
	}

}
