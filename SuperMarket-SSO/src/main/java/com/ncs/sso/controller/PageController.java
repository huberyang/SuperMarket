package com.ncs.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/page")
@Controller
public class PageController {

	@RequestMapping("/login")
	public String loginPage() {
		return "login";
	}

	@RequestMapping("/register")
	public String registePage() {
		return "register";
	}

}
