package com.pharossolutions.svnkitint.services.impl;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.pharossolutions.svnkitint.services.SvnService;
import com.pharossolutions.svnkitint.services.SvnUserService;

@Component
public class SvnUserServiceImpl implements SvnUserService {
	@Autowired
	private SvnService svnService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();

		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			throw new BadCredentialsException("Invalid User Credentials");
		}
		boolean isAuthenticated = svnService.authenticateAndTestConnection(username, password);
		if (isAuthenticated) {
			return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
		}
		throw new BadCredentialsException("Invalid User Credentials");
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
