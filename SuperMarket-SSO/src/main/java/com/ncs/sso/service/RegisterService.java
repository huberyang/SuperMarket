package com.ncs.sso.service;

import com.ncs.common.utils.pojo.SmResult;
import com.ncs.pojo.TbUser;

public interface RegisterService {
	
	
	/**
	 * 	
	 * to check the user's info when register
	 * 
	 * @param param   user's info
	 * @param type    1: phone   2:email   3:username
	 * @return
	 */
	public SmResult CheckInfo(String param, int type) throws Exception;
	
	public SmResult Registe(TbUser user) throws Exception;

}
