package org.oxerr.spring.security.phone.authentication;

import org.oxerr.spring.security.phone.core.userdetails.PhoneUserDetailsService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.util.Assert;

public class PhoneAuthenticationProvider implements AuthenticationProvider,
		InitializingBean, MessageSourceAware {

	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
	private final PhoneUserDetailsService phoneUserDetailsService;

	private UserDetailsChecker postAuthenticationChecks = new AccountStatusUserDetailsChecker();

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
		final String code = (String) phoneAuthenticationToken.getCredentials();

		if (!phoneUserDetailsService.consumeCode(number, code)) {
			throw new BadCredentialsException(messages.getMessage(
					"AbstractUserDetailsAuthenticationProvider.badCredentials",
					"Bad credentials"));
		}

		UserDetails user = phoneUserDetailsService.loadUserByNumber(number);

		postAuthenticationChecks.check(user);

		return new PhoneAuthenticationToken(user);
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
		Assert.notNull(this.messages, "A message source must be set");
		Assert.notNull(this.phoneUserDetailsService, "A phoneUserDetailsService must be set.");
		Assert.notNull(this.postAuthenticationChecks, "A postAuthenticationChecks must be set.");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messages = new MessageSourceAccessor(messageSource);
	}

}
