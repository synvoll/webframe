package com.pharossolutions.svnkitint.models;

public class SvnConfiguration {

	private String mUrl;
	private String mUser;
	private String mPassword;
	private boolean mIsApache;
	
	
	public SvnConfiguration(String url,
							String user,
							String password,
							boolean isApache) {
		
		this.mUrl      = url;
		this.mUser     = user;
		this.mPassword = password;
		this.mIsApache = isApache;
	}
	
	
	public String getUrl() {
		return mUrl;
	}
	
	
	public String getUser() {
		return mUser;
	}
	
	
	public String getPassword() {
		return mPassword;
	}
	
	
	public boolean isApache() {
		return this.mIsApache;
	}
}
