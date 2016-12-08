package org.oxerr.spring.security.phone.core;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.util.Assert;

public class PhoneAuthenticationProvider implements AuthenticationProvider,
		InitializingBean, MessageSourceAware {

	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
	private final PhoneUserDetailsService phoneUserDetailsService;

	public PhoneAuthenticationProvider(
		PhoneUserDetailsService phoneUserDetailsService
	) {
		this.phoneUserDetailsService = phoneUserDetailsService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Authentication authenticate(final Authentication authentication)
			throws AuthenticationException {
		final PhoneAuthenticationToken phoneAuthenticationToken = (PhoneAuthenticationToken) authentication;

		final String number = (String) phoneAuthenticationToken.getPrincipal();
		final String code = phoneAuthenticationToken.getCredentials();

		if (!phoneUserDetailsService.consumeCode(number, code)) {
			throw new BadCredentialsException(messages.getMessage(
					"AbstractUserDetailsAuthenticationProvider.badCredentials",
					"Bad credentials"));
		}

		return new PhoneAuthenticationToken(phoneUserDetailsService.loadUserByNumber(number));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		return PhoneAuthenticationToken.class.isAssignableFrom(authentication);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.phoneUserDetailsService, "A phoneUserDetailsService must be set.");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messages = new MessageSourceAccessor(messageSource);
	}

}
