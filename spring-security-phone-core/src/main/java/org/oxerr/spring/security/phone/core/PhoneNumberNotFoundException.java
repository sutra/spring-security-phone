package org.oxerr.spring.security.phone.core;

import org.springframework.security.core.AuthenticationException;

public class PhoneNumberNotFoundException extends AuthenticationException {

	private static final long serialVersionUID = 2016092101L;

	public PhoneNumberNotFoundException(String msg) {
		super(msg);
	}

	public PhoneNumberNotFoundException(String msg, Throwable t) {
		super(msg, t);
	}

}
