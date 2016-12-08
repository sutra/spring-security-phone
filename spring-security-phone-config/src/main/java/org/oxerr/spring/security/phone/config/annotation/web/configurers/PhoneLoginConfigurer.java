package org.oxerr.spring.security.phone.config.annotation.web.configurers;

import org.oxerr.spring.security.phone.core.PhoneAuthenticationProvider;
import org.oxerr.spring.security.phone.core.PhoneUserDetailsService;
import org.oxerr.spring.security.phone.web.PhoneAuthenticationFilter;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public final class PhoneLoginConfigurer<H extends HttpSecurityBuilder<H>>
		extends
		AbstractAuthenticationFilterConfigurer<H, PhoneLoginConfigurer<H>,
		PhoneAuthenticationFilter> {

	private final PhoneUserDetailsService phoneUserDetailsService;

	public PhoneLoginConfigurer(PhoneUserDetailsService phoneUserDetailsService) {
		super(new PhoneAuthenticationFilter(), "/login/phone");
		this.phoneUserDetailsService = phoneUserDetailsService;
		numberParameter("number");
		codeParameter("code");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void configure(H http) throws Exception {

		// Make sure the filter be registered in
		// org.springframework.security.config.annotation.web.builders.FilterComparator
		http.addFilterAfter(getAuthenticationFilter(),
				UsernamePasswordAuthenticationFilter.class);

		super.configure(http);
	}

	@Override
	public PhoneLoginConfigurer<H> loginPage(String loginPage) {
		return super.loginPage(loginPage);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(H http) throws Exception {
		super.init(http);

		PhoneAuthenticationProvider authenticationProvider = new PhoneAuthenticationProvider(
				phoneUserDetailsService);
		postProcess(authenticationProvider);
		http.authenticationProvider(authenticationProvider);

		initDefaultLoginFilter(http);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected RequestMatcher createLoginProcessingUrlMatcher(
			String loginProcessingUrl) {
		return new AntPathRequestMatcher(loginProcessingUrl, "POST");
	}

	private void initDefaultLoginFilter(H http) {
		DefaultLoginPageGeneratingFilter loginPageGeneratingFilter = http
				.getSharedObject(DefaultLoginPageGeneratingFilter.class);
		if (loginPageGeneratingFilter != null && !isCustomLoginPage()) {
			String loginPageUrl = loginPageGeneratingFilter.getLoginPageUrl();
			if (loginPageUrl == null) {
				loginPageGeneratingFilter.setLoginPageUrl(getLoginPage());
				loginPageGeneratingFilter.setFailureUrl(getFailureUrl());
			}
		}
	}

	public PhoneLoginConfigurer<H> numberParameter(String numberParameter) {
		getAuthenticationFilter().setNumberParameter(numberParameter);
		return this;
	}

	public PhoneLoginConfigurer<H> codeParameter(String codeParameter) {
		getAuthenticationFilter().setCodeParameter(codeParameter);
		return this;
	}

}
