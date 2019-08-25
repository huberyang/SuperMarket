package com.ncs.portal.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ncs.pojo.TbUser;

public interface UserService {

	public TbUser getUserByToken(HttpServletRequest request, HttpServletResponse response) throws Exception;

}
