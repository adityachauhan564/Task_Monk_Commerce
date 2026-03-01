package com.monk.commerce.exception;

public class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException(String message) {
        super(message);
    }
}
