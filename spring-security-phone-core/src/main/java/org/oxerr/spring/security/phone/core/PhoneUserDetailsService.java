package org.oxerr.spring.security.phone.core;

import org.springframework.security.core.userdetails.UserDetails;

public interface PhoneUserDetailsService {

	boolean consumeCode(String number, String code);

	UserDetails loadUserByNumber(String number) throws PhoneNumberNotFoundException;

}
