package com.ncs.portal.service;

import com.ncs.pojo.TbItem;
import com.ncs.pojo.TbItemDesc;
import com.ncs.pojo.TbItemParamItem;

public interface ItemService {
	
	public TbItem findItem(Long itemId) throws Exception;

	public TbItemDesc findItemDesc(Long itemId) throws Exception;

	public TbItemParamItem findItemParamItem(Long itemId) throws Exception;

}
