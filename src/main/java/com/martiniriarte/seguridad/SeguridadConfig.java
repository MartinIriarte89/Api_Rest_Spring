package com.martiniriarte.seguridad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SeguridadConfig extends WebSecurityConfigurerAdapter {

	
		private final UserDetailsService userDetailsService;
		private final PasswordEncoder passwordEncoder;
		private final JwtAuthenticationEntryPoint authenticationEntryPoint;
		private final JwtAuthoritationFilter authoritationFilter;
		
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
		}

		@Bean
		@Override
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.csrf().disable()
				.exceptionHandling()
					.authenticationEntryPoint(authenticationEntryPoint)
				.and()
				.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeHttpRequests()
					.antMatchers(HttpMethod.GET, "/producto/**", "/lote/**").hasRole("USER")
					.antMatchers(HttpMethod.POST, "/producto/**", "/lote/**").hasRole("ADMIN")
					.antMatchers(HttpMethod.PUT, "/producto/**").hasRole("ADMIN")
					.antMatchers(HttpMethod.DELETE, "/producto/**").hasRole("ADMIN")
					.antMatchers(HttpMethod.POST, "/pedido/**").hasAnyRole("USER","ADMIN")
					.anyRequest().authenticated();
			
			http.addFilterBefore(authoritationFilter, UsernamePasswordAuthenticationFilter.class);
		}
}
