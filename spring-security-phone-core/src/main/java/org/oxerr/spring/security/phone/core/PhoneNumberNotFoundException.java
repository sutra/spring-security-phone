package org.oxerr.spring.security.phone.core;

import org.springframework.security.core.AuthenticationException;

public class PhoneNumberNotFoundException extends AuthenticationException {

	private static final long serialVersionUID = 2017062701L;

	public PhoneNumberNotFoundException() {
		super("Phone number was not found.");
	}

	public PhoneNumberNotFoundException(String msg) {
		super(msg);
	}

	public PhoneNumberNotFoundException(String msg, Throwable t) {
		super(msg, t);
	}

}
