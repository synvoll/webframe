package com.pharossolutions.svnkitint.models;

public class LdapConfiguration {

	private String mHost;
	private String mPort;
	private String mBindDn;
	
	
	public LdapConfiguration(String host,
							 String port,
							 String bindDn) {
		
		this.mHost   = host;
		this.mPort   = port;
		this.mBindDn = bindDn;
	}
	
	
	public String getHost() {
		return mHost;
	}
	
	
	public String getPort() {
		return mPort;
	}
	
	
	public String getBindDn() {
		return mBindDn;
	}
	
}
