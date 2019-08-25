package com.ncs.portal.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ncs.common.utils.CookieUtils;
import com.ncs.common.utils.HttpClientUtils;
import com.ncs.common.utils.pojo.SmResult;
import com.ncs.pojo.TbUser;
import com.ncs.portal.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private static final Class<?> Tb = null;
	@Value("${SSO_BASE_URL}")
	private String SSO_BASE_URL;
	@Value("${SSO_USER_TOKEN_SERVICE}")
	private String SSO_USER_TOKEN_SERVICE;

	@Override
	public TbUser getUserByToken(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// get the token from the cookie
		String token = CookieUtils.getCookieValue(request, "SM_TOKEN");
		
		if(StringUtils.isBlank(token)) {
			// doesn't have user info, so return null
			return null;
		}

		String jsonResult = HttpClientUtils.doGet(SSO_BASE_URL + SSO_USER_TOKEN_SERVICE + token);

		SmResult result = SmResult.format(jsonResult);

		if (result.getStatus() != 200) {
			// maybe so error occur,so can't get user info,return null
			return null;
		}

		// get the user object
		result = SmResult.formatToPojo(jsonResult, TbUser.class);
		TbUser user = (TbUser) result.getData();
		return user;

	}

}
