package com.ncs.portal.service;

import com.ncs.pojo.TbItem;

public interface ContentService {

	
	/**
	 * 大广告位
	 * @return
	 */
	public String getBigADList() throws Exception;

	
	
	public TbItem getItemById(String itemId) throws Exception;

}
