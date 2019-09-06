package com.ncs.sso.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ncs.common.utils.pojo.SmResult;

public interface LoginService {
	
	public SmResult login(String username, String password, HttpServletRequest request, HttpServletResponse response)
			throws Exception;
	
	public SmResult getUserByToken(String token) throws Exception;
	
	public SmResult safeLoginOut(String token) throws Exception;


}
