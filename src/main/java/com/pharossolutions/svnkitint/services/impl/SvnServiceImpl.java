package com.pharossolutions.svnkitint.services.impl;

import java.io.ByteArrayInputStream;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.io.ISVNEditor;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.io.diff.SVNDeltaGenerator;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import com.pharossolutions.svnkitint.models.Configuration;
import com.pharossolutions.svnkitint.models.SvnUser;
import com.pharossolutions.svnkitint.services.SvnService;



@Component
public class SvnServiceImpl implements SvnService {
	
	// deprecated
	// private final static String SVN_URL = Configuration.get().getProperty("tfa.webform.svn.url");
	private final static String SVN_URL = Configuration.get().getSvnConfig().getUrl();

	@Override
	public boolean authenticateAndTestConnection(String username, String password) {
		
		try {
			SVNRepository repository = authenticate(username, password);
			repository.testConnection();
			return true;
		} catch (SVNException e) {
			Logger.getLogger(SvnServiceImpl.class).error("Error during login.", e);
			return false;
		}
	}

	@Override
	public boolean commit(String fileName, ByteArrayInputStream inputStream, SvnUser user) {
		try {
			// deprecated
			// SVNRepository repository = authenticate(Configuration.get().getProperty("tfa.webform.svn.user"), Configuration.get().getProperty("tfa.webform.svn.password"));
			SVNRepository repository = authenticate(Configuration.get().getSvnConfig().getUser(), Configuration.get().getSvnConfig().getPassword());
			long latestRevision = repository.getLatestRevision();
			ISVNEditor commitEditor;
			
			
			String[] pathSegments = fileName.split("/");
			String projectName = pathSegments[0];
			pathSegments[0] = projectName.toLowerCase();
			
			
			// check if project exists
			boolean projectExists = true;
			if (repository.checkPath(pathSegments[0], -1) == SVNNodeKind.NONE) {
				projectExists = false;
			}
			
			
			// add project structure if project doesn't exist yet
			if (! projectExists) {
				commitEditor = repository.getCommitEditor("project \"" + pathSegments[0] + "\" created", null);
				commitEditor.openRoot(-1);
				commitEditor.addDir(pathSegments[0], "project_template", latestRevision);
				commitEditor.closeDir();
				commitEditor.closeEdit();
			}
			
			
			// check which part of the file path already exists
			String path = pathSegments[0];
			int firstNonpresentIndex = pathSegments.length;
			for (int i=1;i<pathSegments.length;i++) {
				path = path + "/" + pathSegments[i];
				if (repository.checkPath(path, -1) == SVNNodeKind.NONE) {
					firstNonpresentIndex = i;
					break;
				}
			}
			
			boolean directoryExists = true;
			if (firstNonpresentIndex < pathSegments.length - 1) {
				directoryExists = false;
			}
			
			
			boolean fileExists = true;
			if (firstNonpresentIndex < pathSegments.length) {
				fileExists = false;
			}
			
			// repository = authenticate(Configuration.get().getSvnConfig().getUser(), Configuration.get().getSvnConfig().getPassword());
			// create commit editor
			latestRevision = repository.getLatestRevision();
			String message = (fileExists) ? "File changed" : "First commit";
			commitEditor = repository.getCommitEditor(message, null);
			commitEditor.openRoot(-1);
			
			
			


			if (! directoryExists && Configuration.get().getSvnConfig().isApache()) {
				// create missing directories if needed
				for (int i=firstNonpresentIndex; i<pathSegments.length - 1; i++) {
					path = this.getSvnPath(pathSegments, i);
					commitEditor.addDir(path, null, latestRevision);
				}
			}
			else if (! Configuration.get().getSvnConfig().isApache()) {
				for (int i=0; (i<firstNonpresentIndex && i<pathSegments.length - 1); i++) {
					path = pathSegments[i];
					commitEditor.openDir(path, latestRevision);
				}
				for (int i=firstNonpresentIndex; i<pathSegments.length - 1; i++) {
					path = pathSegments[i];
					commitEditor.addDir(path, null, latestRevision);
				}
			}

			
			// create file if needed and process changes
			if (Configuration.get().getSvnConfig().isApache()) {
				String dirPath = this.getSvnPath(pathSegments, pathSegments.length - 2);
				String filePath = this.getSvnPath(pathSegments, pathSegments.length - 1);
				commitEditor.openDir(dirPath,latestRevision);
				if (fileExists) {
					commitEditor.openFile(filePath, latestRevision);
				}
				else {
					commitEditor.addFile(filePath, null, latestRevision);
				}
				
				commitEditor.applyTextDelta(filePath, null);
				SVNDeltaGenerator deltaGenerator = new SVNDeltaGenerator();
				String checksum = deltaGenerator.sendDelta(filePath, inputStream, commitEditor, true);
				commitEditor.closeFile(filePath, checksum);
			}
			else {
				String filePath = pathSegments[pathSegments.length - 1];
				if (fileExists) {
					commitEditor.openFile(filePath, latestRevision);
				}
				else {
					commitEditor.addFile(filePath, null, latestRevision);
				}
				commitEditor.applyTextDelta(filePath, null);
				SVNDeltaGenerator deltaGenerator = new SVNDeltaGenerator();
				String checksum = deltaGenerator.sendDelta(filePath, inputStream, commitEditor, true);
				commitEditor.closeFile(filePath, checksum);
			}
	

			commitEditor.closeDir();
			
			// commit changes
			commitEditor.closeEdit();
			
			return true;
		} catch (SVNException e) {
			Logger.getLogger(SvnServiceImpl.class).error("Error during commit.", e);
			return false;
		}
	}
	

	private SVNRepository authenticate(String username, String password) throws SVNException {
		SVNRepository repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(SVN_URL));
		ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(username,
				password.toCharArray());
		repository.setAuthenticationManager(authManager);
		return repository;
	}
	
	
	private String getSvnPath(String[] segments, int index) {
		
		StringBuilder builder = new StringBuilder();
		builder.append("/");
		for (int i=0;i<=index;i++) {
			builder.append(segments[i]);
			builder.append("/");
		}
		builder.delete(builder.length() - 1, builder.length());
		
		return builder.toString();
	}
}
