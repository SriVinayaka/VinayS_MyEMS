/**
 * 
 */
package com.gl.myems.security.entity.service.implementation;

/**
 * 
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gl.myems.security.entity.MyEmsUserDetails;
import com.gl.myems.security.entity.User;
import com.gl.myems.security.repository.UserRepository;

@Service
public class MyEmsUserDetailsServiceImplementation implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.getUserByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException("Could not find user");
		}

		return new MyEmsUserDetails(user);
	}
}
