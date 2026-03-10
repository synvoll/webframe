package com.pharossolutions.svnkitint.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.pharossolutions.svnkitint.models.Configuration;
import com.pharossolutions.svnkitint.models.FormInput;
import com.pharossolutions.svnkitint.models.LoginUser;
import com.pharossolutions.svnkitint.models.SvnUser;
import com.pharossolutions.svnkitint.services.FormInputService;

@Controller
public class FileFormController {
	@Autowired
	private FormInputService formInputService;

	/**
	 * Form Page. First page after login, contains form for user to submit and
	 * commit contents
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "form")
	public ModelAndView loginRedirect(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("form");

		LoginUser loginUser = Configuration.get().getLoginUser();
		
		SvnUser user = new SvnUser(Configuration.get().getSvnConfig().getUser(),
									Configuration.get().getSvnConfig().getPassword());
		
		modelAndView.addObject("loginUser", loginUser);
		modelAndView.addObject("svnUser", user);
		modelAndView.addObject("formInput", new FormInput());
		return modelAndView;
	}

	/**
	 * Submits form and commits contents to new/existing file defined in
	 * packetName input
	 * 
	 * @param request
	 * @param packetName
	 *            required filename
	 * @param version
	 * @param delivery
	 * @param comment
	 * @param packetType
	 * @return
	 */
	@RequestMapping(value = "submit")
	public String submit(HttpServletRequest request, //
			@RequestParam String packetName, //
			@RequestParam(required = false) String version, //
			@RequestParam(required = false) String dependencies, //
			@RequestParam(required = false) String comment, //
			@RequestParam(required = false) String packetType,//
			@RequestParam(required = false) String update) {
		FormInput formInput = new FormInput();
		formInput.setPacketName(packetName);
		formInput.setVersion(version);
		formInput.setDependencies(dependencies);
		formInput.setServerDeployment(request.getParameter("deploymentserver") != null);
		formInput.setClientDeployment(request.getParameter("deploymentclient") != null);
		formInput.setCitrixTsDeployment(request.getParameter("deploymentcitrixts") != null);
		formInput.setComment(comment);
		formInput.setPacketType(packetType);
		formInput.setUnifa(request.getParameter("unifa") != null);
		formInput.setFa(request.getParameter("fa") != null);
		formInput.setFust(request.getParameter("fust") != null);
		formInput.setGst(request.getParameter("gst") != null);
		formInput.setSonderpc(request.getParameter("sonderpc") != null);
		formInput.setEntwicklerpc(request.getParameter("entwicklerpc") != null);
		formInput.setBap(request.getParameter("bap") != null);
		formInput.setPruefernb(request.getParameter("pruefernb") != null);
		formInput.setKassennb(request.getParameter("kassennb") != null);
		formInput.setVergnuegungssteuernb(request.getParameter("vergnuegungssteuernb") != null);
		formInput.setOthers(request.getParameter("other") != null);
		formInput.setUpdate(request.getParameter("update") != null);
		SvnUser user = (SvnUser) request.getSession(false).getAttribute("user");
		boolean success = formInputService.commit(formInput, user);
		return "redirect:/form?success=" + success;
	}

}
