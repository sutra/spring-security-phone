package org.oxerr.spring.security.phone.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.oxerr.spring.security.phone.core.PhoneAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

public class PhoneAuthenticationFilter
		extends AbstractAuthenticationProcessingFilter {

	public static final String SPRING_SECURITY_FORM_NUMBER_KEY = "number";
	public static final String SPRING_SECURITY_FORM_CODE_KEY = "code";

	private String numberParameter = SPRING_SECURITY_FORM_NUMBER_KEY;
	private String codeParameter = SPRING_SECURITY_FORM_CODE_KEY;

	public PhoneAuthenticationFilter() {
		super(new AntPathRequestMatcher("/login/phone", "POST"));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException(
					"Authentication method not supported: " + request.getMethod());
		}

		final String number = request.getParameter(numberParameter);
		final String code = request.getParameter(codeParameter);

		final PhoneAuthenticationToken authRequest = new PhoneAuthenticationToken(number, code);
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));

		return this.getAuthenticationManager().authenticate(authRequest);
	}

	public void setNumberParameter(String numberParameter) {
		Assert.hasText(numberParameter, "Number parameter must not be empty or null");
		this.numberParameter = numberParameter;
	}

	public void setCodeParameter(String codeParameter) {
		Assert.hasText(codeParameter, "Code parameter must not be empty or null");
		this.codeParameter = codeParameter;
	}

}
