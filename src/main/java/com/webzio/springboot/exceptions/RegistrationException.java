package com.webzio.springboot.exceptions;

import lombok.Getter;


public class RegistrationException extends RuntimeException {

	private final String errorMessage;

    public RegistrationException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
