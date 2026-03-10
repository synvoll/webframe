package com.pharossolutions.svnkitint.models;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

public class Configuration {

	private static Configuration configuration = null;
	
	private SvnConfiguration    mSvnConfig;
	private LdapConfiguration[] mLdapConfigurations;
	private LoginUser           mLoginUser;
	
	
	public static Configuration get() {
		if (configuration == null) {
			configuration = new Configuration();
		}
		return configuration;
	}
	
	
	public Configuration () {
		this.load();
	}
	
	
	public SvnConfiguration getSvnConfig() {
		return mSvnConfig;
	}


	public LdapConfiguration[] getLdapConfigurations() {
		return mLdapConfigurations;
	}
	
	
	public LoginUser getLoginUser() {
		return this.mLoginUser;
	}
	
	
	public void setLoginUser(LoginUser loginUser) {
		this.mLoginUser = loginUser;
	}
	
	
	public void reload() {
		this.load();
	}
	
	
	private void load() {
		
		String confFileLocation = "webform-config.xml";
		
		if (System.getProperty("tfa.webform.configuration.file") != null) {
			confFileLocation = System.getProperty("tfa.webform.configuration.file");
		}
		
		ClassLoader classLoader = getClass().getClassLoader();
		URL  url = classLoader.getResource(confFileLocation);
		
		Logger.getLogger(this.getClass()).debug("configuration URL: " + url.getPath().replace("%20", " "));
		
		File configFile = null;
		if (url != null) {
			configFile = new File(url.getPath().replace("%20", " "));
		}
		

		 
		if (configFile.exists()) {
			SAXBuilder saxBuilder = new SAXBuilder();
			Document document = null;
			try {
				document = saxBuilder.build(configFile);
			} catch (JDOMException e) {
				Logger.getLogger(this.getClass()).error("Error reading configuration: " + e);
				return;
			} catch (IOException e) {
				Logger.getLogger(this.getClass()).error("Error reading configuration: " + e);
				return;
			}  
			
			XPathFactory xpathFactory = XPathFactory.instance();
			
			XPathExpression<Object> svnExpr = xpathFactory.compile("/webform-configuration/svn-configuration");
			Object svnObject = svnExpr.evaluateFirst(document);
			if (svnObject instanceof Element) {
				
				Element svnConfigElem = (Element) svnObject;
				
				String svnUrl   = svnConfigElem.getAttributeValue("url", "https://localhost/svn/tfa-webform");
				String user     = svnConfigElem.getAttributeValue("user", "service");
				String password = svnConfigElem.getAttributeValue("password", "service");
				
				boolean isNonApache = false;		
				String value = svnConfigElem.getAttributeValue("non-apache", "no");
				if (value.equalsIgnoreCase("yes") || value.equalsIgnoreCase("true")) {
					isNonApache = true;
				}
				
				
				
				this.mSvnConfig = new SvnConfiguration(svnUrl, user, password, !isNonApache);
			}
			
			ArrayList<LdapConfiguration> ldapConfigurations = new ArrayList<LdapConfiguration>();
			XPathExpression<Object> ldapExpr = xpathFactory.compile("/webform-configuration/ldap-configurations/ldap-configuration");
			List<Object> ldapObjects = ldapExpr.evaluate(document);
			for (Object ldapObject : ldapObjects) {
				
				if (ldapObject instanceof Element) {
					
					Element ldapElem = (Element) ldapObject;
					
					String host   = ldapElem.getAttributeValue("host", "localhost");
					String port   = ldapElem.getAttributeValue("port", "389");
					String bindDn = ldapElem.getAttributeValue("bind-dn", "bind.dn.not.configured");
					
					
					ldapConfigurations.add(new LdapConfiguration(host, port, bindDn));
				}
				
			}
			this.mLdapConfigurations = ldapConfigurations.toArray(new LdapConfiguration[ldapConfigurations.size()]);
		}
	}
}
