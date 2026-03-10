package com.pharossolutions.svnkitint.services.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pharossolutions.svnkitint.models.FormInput;
import com.pharossolutions.svnkitint.models.SvnUser;
import com.pharossolutions.svnkitint.services.FormInputService;
import com.pharossolutions.svnkitint.services.SvnService;

@Component
public class FormInputServiceImpl implements FormInputService {
	@Autowired
	private SvnService svnService;

	@Override
	public boolean commit(FormInput formInput, SvnUser user) {
		StringBuilder stringBuilder = new StringBuilder("version.project=").append(formInput.getPacketName()).append("\n")//
				.append("version.release=").append(formInput.getVersion()).append("\n")//
				.append("workflow.server.deployment=").append(formInput.isServerDeployment()).append("\n")//
				.append("workflow.client.deployment=").append(formInput.isClientDeployment()).append("\n")//
				.append("workflow.citrixts.deployment=").append(formInput.isCitrixTsDeployment()).append("\n")//
				.append("version.comment=").append(formInput.getComment()).append("\n")//
				.append("version.branchtype=").append(formInput.getPacketType()).append("\n")//
				.append("workflow.deploy.unifa=").append(formInput.isUnifa()).append("\n")//
				.append("workflow.deploy.fa=").append(formInput.isFa()).append("\n")//
				.append("workflow.deploy.fust=").append(formInput.isFust()).append("\n")//
				.append("workflow.deploy.gst=").append(formInput.isGst()).append("\n")//
				.append("workflow.deploy.sonderpc=").append(formInput.isSonderpc()).append("\n")//
				.append("workflow.deploy.entwicklerpc=").append(formInput.isEntwicklerpc()).append("\n")//
				.append("workflow.deploy.bap=").append(formInput.isBap()).append("\n")//
				.append("workflow.deploy.pruefernb=").append(formInput.isPruefernb()).append("\n")//
				.append("workflow.deploy.kassennb=").append(formInput.isKassennb()).append("\n")//
				.append("workflow.deploy.vergnuegungssteuernb=").append(formInput.isVergnuegungssteuernb()).append("\n")//
				.append("workflow.deploy.other=").append(formInput.isOthers()).append("\n")//
				.append("workflow.update=").append(formInput.isUpdate()).append("\n")//
				.append("workflow.predecessors=").append(formInput.getDependencies());
		ByteArrayInputStream inputStream = new ByteArrayInputStream(stringBuilder.toString().getBytes());
		try {
			return svnService.commit(constructFileName(formInput), inputStream, user);
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				// ignore
			}
		}
	}

	private String constructFileName(FormInput formInput) {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(formInput.getPacketName());
		sb.append("/");
		
		sb.append("branches/");
		
		if (formInput.getPacketType().equalsIgnoreCase("hotfix")) {
			sb.append("hotfixes/");
		}
		else if (formInput.getPacketType().equalsIgnoreCase("fehlerbereinigung")) {
			sb.append("fehlerbereinigung/");
		}
		else {
			sb.append(formInput.getVersion());
			sb.append("/");
		}
		
		sb.append("package.properties");
		
		return sb.toString();
	}
}
