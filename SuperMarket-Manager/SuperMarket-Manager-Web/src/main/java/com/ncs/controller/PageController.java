package com.ncs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @Title: PageController.java
 * @Package com.ncs.controller
 * @Description: TODO(页面跳转)
 * @author: Stephen Yang
 * @date: Mar 26, 2019 11:38:11 PM
 * @version V1.0
 * @Copyright: 2019 Inc. All rights reserved.
 *
 */
@Controller
public class PageController {

	@RequestMapping("/")
	public String showIndex() {
		return "index";
	}

	@RequestMapping("/{page}")
	public String showPage(@PathVariable String page) {
		return page;
	}

}
