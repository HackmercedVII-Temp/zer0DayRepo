package com.example.demo.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class LoginConfiguration extends WebSecurityConfigurerAdapter {

	private static String adminAuthPass = "B0BCATS!5200";

	@Autowired
	private MyBasicAuthenticationEntryPoint authenticationEntryPoint;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("Admin").password(passwordEncoder().encode(adminAuthPass))
				.roles("ADMIN");

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/test").permitAll().antMatchers("/CalculateConfidence").permitAll()
				.antMatchers("/TestCompanyColors").permitAll().antMatchers("/test").permitAll()
				.antMatchers("/LogoDecoder").hasAnyRole("ADMIN").and().httpBasic()
				.authenticationEntryPoint(authenticationEntryPoint);
		http.csrf().disable();
	}

}
