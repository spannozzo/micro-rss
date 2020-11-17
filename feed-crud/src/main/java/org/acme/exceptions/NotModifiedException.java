package org.acme.exceptions;

import javax.validation.ValidationException;

public class NotModifiedException extends ValidationException {

	private static final long serialVersionUID = -3415376536459076538L;

	public NotModifiedException(String message) {
		super(message);
	}
	
}
