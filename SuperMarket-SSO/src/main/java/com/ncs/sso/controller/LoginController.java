package com.ncs.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ncs.common.utils.ExceptionUtil;
import com.ncs.common.utils.pojo.SmResult;
import com.ncs.sso.service.LoginService;

@RequestMapping("/user")
@Controller
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ResponseBody
	public SmResult login(@RequestParam String username,@RequestParam String password,HttpServletRequest request,HttpServletResponse response) {
		try {
		
		SmResult result = loginService.login(username, password, request, response);
		return result;
		
		}catch(Exception e) {
			e.printStackTrace();
			return SmResult.build(500, ExceptionUtil.getStatckTrace(e)) ;
		}
	}
	
	

}
