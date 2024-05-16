/**
 * 
 */
package com.gl.myems.security.entity.configuration;

/**
 * 
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.web.SecurityFilterChain;

import com.gl.myems.security.entity.service.implementation.MyEmsUserDetailsServiceImplementation;

@Configuration
public class MyEmsSecurityConfiguration {

	@Bean
	public UserDetailsService userDetailsService() {
		return new MyEmsUserDetailsServiceImplementation();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	//@Bean
	/*
	public JwtAuthenticationProvider jwtAuthenticationProvider() {

	    JwtAuthenticationProvider authProvider = new OAu();//JwtAuthenticationProvider();

	    // Configure password encoder (e.g., BCryptPasswordEncoder)
	    authProvider.setPasswordEncoder(passwordEncoder());

	    // Set custom UserDetailsService for user details retrieval
	    authProvider.setUserDetailsService(userDetailsService());

	    // Configure token signing and validation settings
	    authProvider.setSigningKey(secretKey); // Replace with a strong secret key
	    authProvider.setTokenValidityInSeconds(tokenValidityInSeconds); // Set an appropriate expiration time

	    return authProvider;
	}
	*/
	
	@Bean
	public DaoAuthenticationProvider myemsDaoAuthenticationProvider() {

		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		//authProvider.setPasswordEncoder(BCryptPasswordEncoder());
		

		return authProvider;
	}
	
	
	//16MAY2024
/*
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(requests -> requests
                .requestMatchers(
                        "/",
                        "/*",
                        "/api/employees",
                        "/api/employees/*",
                        "/api/employees/createEmployee"
                )
                .hasAnyAuthority("NORMAL_USER", "ADMIN_USER")
                .requestMatchers("/api/employees", "/api/employees/*", "/api/employees/createEmployee")
                .hasAuthority("ADMIN_USER")
                .anyRequest().authenticated());
	        // ... rest of your configuration

		http.authenticationProvider(myemsDaoAuthenticationProvider());

        return http.build();
	}
*/
	
	/*
	@override
	protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	  auth.authenticationProvider(myemsDaoAuthenticationProvider())
	      .withUser("user1")
	      .password(passwordEncoder().encode("password1"))
	      .roles("NORMAL_USER")
	      .and()
	      .withUser("admin")
	      .password(passwordEncoder().encode("admin123"))
	      .roles("ADMIN_USER");
	}
	*/
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.requestMatchers(
						"/",
						"/*",
						"/api/employees",
						"/api/employees/*",
						"/api/employees/createEmployee"
						)
				.hasAnyAuthority("NORMAL_USER", "ADMIN_USER")
				.requestMatchers(
						"/api/employees",
						"/api/employees/*",
						"/api/employees/createEmployee"
						).hasAuthority("ADMIN_USER")
				.anyRequest().authenticated();
				//.and().formLogin().loginProcessingUrl("/login")
				//and().successForwardUrl(
						//"/api/employees"
						//"/api/employees/*",
						//"/api/employees/createEmployee"

						//).permitAll()
				//.and().logout().logoutSuccessUrl("/login").permitAll()
				//.and().exceptionHandling();
				//.accessDeniedPage("/403")
				//.and().cors().and().csrf().disable();

		http.authenticationProvider(myemsDaoAuthenticationProvider());
		return http.build();
	}

}