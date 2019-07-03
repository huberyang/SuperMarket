package com.ncs.search.service;

import com.ncs.common.utils.pojo.SmResult;

public interface ItemService {
	
	//将数据库所有数据导入索引库
	public SmResult importAllData() throws Exception;
	
}
