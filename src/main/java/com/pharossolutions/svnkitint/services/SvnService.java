package com.pharossolutions.svnkitint.services;

import java.io.ByteArrayInputStream;

import com.pharossolutions.svnkitint.models.SvnUser;

public interface SvnService {

	/**
	 * Logins into SVN
	 * 
	 * @param username
	 *            SVN username
	 * @param password
	 *            SVN password
	 * @return true if logged in successfully
	 */
	boolean authenticateAndTestConnection(String username, String password);

	/**
	 * Commits text entered by user to new/exiting file. This functions
	 * authenticates the user, checks if file exists and modifies contents or if
	 * not, creates a new file on the remote SVN server and adds the contents
	 * 
	 * @param fileName
	 *            new/existing filename to commit changes to
	 * @param inputStream
	 *            contains text entered by the user
	 * @param user
	 *            SVN user
	 * @return
	 */
	boolean commit(String fileName, ByteArrayInputStream inputStream, SvnUser user);
}
