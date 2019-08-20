package com.ncs.sso.service.impl;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.ncs.common.utils.CookieUtils;
import com.ncs.common.utils.JsonUtils;
import com.ncs.common.utils.pojo.SmResult;
import com.ncs.mapper.TbUserMapper;
import com.ncs.pojo.TbUser;
import com.ncs.pojo.TbUserExample;
import com.ncs.pojo.TbUserExample.Criteria;
import com.ncs.sso.component.JedisClient;
import com.ncs.sso.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	@Value("${user_token_key}")
	private String user_token_key;
	@Value("${user_token_expire}")
	private Integer user_token_expire;

	@Autowired
	private TbUserMapper tbUserMapper;

	@Autowired
	private JedisClient jedisClient;

	@Override
	public SmResult login(String username, String password, HttpServletRequest request, HttpServletResponse response) {

		// check username and password first
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<TbUser> userList = tbUserMapper.selectByExample(example);

		if (userList == null || userList.isEmpty()) {
			return SmResult.build(400, "there's no user for such username,please check username");
		}

		TbUser user = userList.iterator().next();

		// check password,password was encypted with MD5
		if (!user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
			return SmResult.build(400, "user's password is not correct,please check your password");
		}

		// username and password are right, so we need to save user's info into redis
		// cache(generate token)
		String token = UUID.randomUUID().toString();

		// only save the user's basic info, don't save password into the cache
		user.setPassword(null);
		jedisClient.set(user_token_key + ":" + token, JsonUtils.objectToJson(user));
		// set the redis cache repire time
		jedisClient.expire(user_token_key + ":" + token, user_token_expire);

		// save the token to the cookie,so that browser can use it to check user's login
		// status
		CookieUtils.setCookie(request, response, "SM_TOKEN", token);

		return SmResult.ok(token);
	}

	@Override
	public SmResult getUserByToken(String token) {

		String userJson = jedisClient.get(user_token_key + ":" + token);

		TbUser user = null;

		if (StringUtils.isNotBlank(userJson)) {
			user = JsonUtils.jsonToPojo(userJson, TbUser.class);
			//updated the user session expire time
			jedisClient.expire(user_token_key + ":" + token, user_token_expire);
		}else {
			return SmResult.build(400, "user session has been expired!");
		}
		
		return SmResult.ok(user);
	}

	@Override
	public SmResult safeLoginOut(String token) {
		
		//delete the user session cache
		jedisClient.expire(user_token_key + ":" + token, 0);
		return SmResult.ok();
	}
	
	

}
