package org.oxerr.spring.security.phone.samples.helloworld;

import org.oxerr.spring.security.phone.config.annotation.web.configurers.PhoneLoginConfigurer;
import org.oxerr.spring.security.phone.core.userdetails.PhoneUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfiguration {

	@Configuration
	public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

		@Autowired
		private PhoneUserDetailsService phoneUserDetailsService;

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.authorizeRequests()
					.anyRequest().permitAll()
					.and()
				.formLogin().loginPage("/login").permitAll()
					.and()
				.apply(new PhoneLoginConfigurer<>(phoneUserDetailsService)).loginProcessingUrl("/login/phone").permitAll();
		}

	}

}
