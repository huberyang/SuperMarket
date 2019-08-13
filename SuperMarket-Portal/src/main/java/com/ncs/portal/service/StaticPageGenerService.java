package com.ncs.portal.service;

import com.ncs.common.utils.pojo.SmResult;

public interface StaticPageGenerService {
	
	//interface for generating static page for item
	public SmResult staticPageService(Long itemId) throws Exception;
	
}
