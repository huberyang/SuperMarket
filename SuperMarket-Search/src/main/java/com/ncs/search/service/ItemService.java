package com.ncs.search.service;

import com.ncs.common.utils.pojo.SmResult;
import com.ncs.pojo.TbItem;

public interface ItemService {
	
	//将数据库所有数据导入索引库
	public SmResult importAllData() throws Exception;

	public SmResult delItemIndex(Long itemId) throws Exception;

	public SmResult addItemIndex(TbItem item) throws Exception;
	
	
}
