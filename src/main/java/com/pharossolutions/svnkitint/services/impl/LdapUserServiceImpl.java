package com.pharossolutions.svnkitint.services.impl;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.pharossolutions.svnkitint.models.Configuration;
import com.pharossolutions.svnkitint.models.LdapConfiguration;
import com.pharossolutions.svnkitint.models.LoginUser;
import com.pharossolutions.svnkitint.services.LdapUserService;

@Component
public class LdapUserServiceImpl implements LdapUserService {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		String username = (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();
		
		try {
			Configuration.get();
		}
		catch (Exception ex) {
			throw new BadCredentialsException("Could not read application configuration. Please notify the administrator.");
		}

		
		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			throw new BadCredentialsException("Invalid User Credentials");
		}

		boolean isAuthenticated = this.authenticate(username, password);

		if (isAuthenticated) {
			return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
		}
		throw new BadCredentialsException("Invalid User Credentials");
	}


	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	
	private boolean authenticate(String username, String password) {
		
		boolean result = false;
		
		LdapConfiguration[] ldapConfigurations = Configuration.get().getLdapConfigurations();
		
		for (LdapConfiguration ldapConfiguration : ldapConfigurations) {
		
			String host    = ldapConfiguration.getHost();
			String port    = ldapConfiguration.getPort();
			String bindDn  = ldapConfiguration.getBindDn();

			for (String s : new String[] {"cn", "uid"}) { 
				Hashtable<String, String> env = new Hashtable<String, String>();
				env.put("com.sun.jndi.ldap.connect.timeout", "500");
				env.put(Context.INITIAL_CONTEXT_FACTORY, 
				    "com.sun.jndi.ldap.LdapCtxFactory");
				env.put(Context.PROVIDER_URL, "ldap://"+ host + ":" + port );
				env.put(Context.SECURITY_AUTHENTICATION, "simple");
				if (bindDn.startsWith("@")) {
					Logger.getLogger(LdapUserServiceImpl.class).debug("security principal: " + username + bindDn);
					env.put(Context.SECURITY_PRINCIPAL, username + bindDn);
				}
				else {
					Logger.getLogger(LdapUserServiceImpl.class).debug("security principal: " + s + "=" + username +"," + bindDn);
					env.put(Context.SECURITY_PRINCIPAL, s+ "=" + username +"," + bindDn);
				}
				env.put(Context.SECURITY_CREDENTIALS, password);
		
				try {
				    DirContext ctx = new InitialDirContext(env);		    
				    ctx.close();
				    Configuration.get().setLoginUser(new LoginUser(username, ldapConfiguration));
				    result = true;
				    break;
				} catch (AuthenticationException e) {
					// do nothing
				} catch (NamingException e) {
					// do nothing
				}
			}
			if (result) {
				break;
			}
		}
		
		if (! result) {
			Logger.getLogger(LdapUserServiceImpl.class).error("Could not authenticate via LDAP");
		}
		
		return result;
	}

}
