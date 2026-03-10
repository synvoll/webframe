package com.pharossolutions.svnkitint.models;

public class LoginUser {

	private String mUserName;
	private LdapConfiguration mLdapConfiguration;
	
	
	public LoginUser(String userName, LdapConfiguration ldapConfiguration) {
		this.mUserName = userName;
		this.mLdapConfiguration = ldapConfiguration;
	}

	
	public String getUserName() {
		return mUserName;
	}

	
	public LdapConfiguration getLdapConfiguration() {
		return mLdapConfiguration;
	}
}
