package org.oxerr.spring.security.phone.samples.helloworld;

import java.util.Arrays;

import org.oxerr.spring.security.phone.core.userdetails.PhoneNumberNotFoundException;
import org.oxerr.spring.security.phone.core.userdetails.PhoneUserDetailsService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@SpringBootApplication
@EnableGlobalMethodSecurity(jsr250Enabled = true)
public class Application {

	/**
	 * Writes the request URI (and optionally the query string) to the Commons Log.
	 *
	 * @return the filter registration bean for the {@link CommonsRequestLoggingFilter}.
	 */
	@Bean
	public FilterRegistrationBean<CommonsRequestLoggingFilter> commonsRequestLoggingFilterRegistrationBean() {
		FilterRegistrationBean<CommonsRequestLoggingFilter> registrationBean = new FilterRegistrationBean<>();
		CommonsRequestLoggingFilter requestLoggingFilter = new CommonsRequestLoggingFilter();
		registrationBean.setFilter(requestLoggingFilter);
		registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return registrationBean;
	}

	@Bean
	public PhoneUserDetailsService phoneUserDetailsService() {
		return new PhoneUserDetailsService() {

			@Override
			public UserDetails loadUserByNumber(String number)
					throws PhoneNumberNotFoundException {
				return new User("user", "user1234", Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
			}

			@Override
			public boolean consumeCode(String number, String code) {
				return "123456".equals(code);
			}
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
