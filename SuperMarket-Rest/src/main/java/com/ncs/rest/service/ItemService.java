package com.ncs.rest.service;

import com.ncs.common.utils.pojo.SmResult;
import com.ncs.pojo.TbItem;
import com.ncs.pojo.TbItemDesc;
import com.ncs.pojo.TbItemParamItem;

public interface ItemService {
    
	public TbItem findItem(Long itemId) throws Exception;

	public TbItemDesc findItemDesc(Long itemId) throws Exception;

	public TbItemParamItem findItemParamItem(Long itemId) throws Exception;

	/**
	 * 同步商品信息
	 * 
	 * @param itemId
	 * @return
	 * @throws Exception
	 */
	public SmResult syncItem(Long itemId) throws Exception;

	/**
	 * 同步商品描述信息
	 * 
	 * @param itemId
	 * @return
	 * @throws Exception
	 */
	public SmResult syncItemDesc(Long itemId) throws Exception;

	/**
	 * 同步商品规格参数信息
	 * 
	 * @param itemId
	 * @return
	 * @throws Exception
	 */
	public SmResult syncItemParamIten(Long itemId) throws Exception;

}
