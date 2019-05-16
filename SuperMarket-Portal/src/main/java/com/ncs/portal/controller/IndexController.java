package com.ncs.portal.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ncs.portal.service.ContentService;

@Controller
public class IndexController {
	
	@Autowired
	private ContentService contentService;
	
	/*
	 * 
	 * <servlet-mapping>
		<servlet-name>springMVC</servlet-name>
		<!-- <url-pattern>/</url-pattern> -->
	</servlet-mapping>
	 * 
	 * @RequestMapping("/")
	public String showIndex() {
		return "index";
	}
	
	*
	*<mvc:resources location="/WEB-INF/css/" mapping="/css/**"></mvc:resources>
	<mvc:resources location="/WEB-INF/js/" mapping="/js/**"></mvc:resources>
	<mvc:resources location="/WEB-INF/images/" mapping="/images/**"></mvc:resources>
         这样配置后可以把css/js/image等资源放在web-inf文件夹里面
	*/
	
	@RequestMapping("/index")
	public String showIndex(Model model) {
		
		String result = contentService.getBigADList();
		model.addAttribute("ad1", result);
		return "index";
	}
	
}
