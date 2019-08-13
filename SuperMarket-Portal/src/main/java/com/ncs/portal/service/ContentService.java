package com.ncs.portal.service;

import com.ncs.pojo.TbItemDesc;
import com.ncs.pojo.TbItemParamItem;
import com.ncs.portal.pojo.ItemDeatils;

public interface ContentService {

	/**
	 * 大广告位
	 * 
	 * @return
	 */
	public String getBigADList() throws Exception;

	public ItemDeatils getItemById(Long itemId) throws Exception;

	public TbItemParamItem getItemParamById(Long itemId) throws Exception;

	public TbItemDesc getItemDescById(Long itemId) throws Exception;

}
