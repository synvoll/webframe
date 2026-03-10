package com.pharossolutions.svnkitint.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

	/**
	 * Login page
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "login")
	public String loginRedirect(HttpServletRequest request) {
		return "login";
	}

	/**
	 * Default path
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/")
	public String rootRedirect(HttpServletRequest request) {
		return "redirect:/login";
	}
}
