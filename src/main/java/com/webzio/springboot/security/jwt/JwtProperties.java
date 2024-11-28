package com.webzio.springboot.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration properties for JWT authentication, including issuer, secret key, and expiration time.
 * These properties are loaded from application configuration files.
 */
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

	private String issuer;

	private String secretKey;

	private long expirationMinute;

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public long getExpirationMinute() {
		return expirationMinute;
	}

	public void setExpirationMinute(long expirationMinute) {
		this.expirationMinute = expirationMinute;
	}
}
