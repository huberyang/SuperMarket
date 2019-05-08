package com.ncs.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	
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
	public String showIndex() {
		return "index";
	}

}
