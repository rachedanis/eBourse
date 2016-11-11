package com.bfigroupe.ebourse.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@RequestMapping(value = "/home")
	@PreAuthorize("hasAuthority('LOGIN_PRIVILEGE')")
	public String home(Model model,  HttpServletRequest request) {
		
		return "home";
	}
}
