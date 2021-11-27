package org.oxerr.spring.security.phone.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class PhoneAuthenticationToken
		extends UsernamePasswordAuthenticationToken {

	private static final long serialVersionUID = 2016092004L;

	public PhoneAuthenticationToken(String number, String code) {
		super(number, code);
	}

	public PhoneAuthenticationToken(UserDetails userDetails) {
		super(userDetails, null, userDetails.getAuthorities());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCredentials() {
		return (String) super.getCredentials();
	}

}
