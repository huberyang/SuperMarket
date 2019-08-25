package com.ncs.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/page")
@Controller
public class PageController {

	@RequestMapping("/login")
	public String loginPage(String redirectUrl, Model model) {

		model.addAttribute("redirect", redirectUrl);
		return "login";
	}

	@RequestMapping("/register")
	public String registePage() {
		return "register";
	}

}
