package com.ncs.service;

import com.ncs.common.utils.pojo.EasyDataGridResult;
import com.ncs.common.utils.pojo.SmResult;

public interface ItemParamService {
	
	/**
	 * 根据分页查询Item
	 * @param page
	 * @param rows
	 * @return EasyDataGridResult
	 */
	EasyDataGridResult selectItemParamByPage(int page,int rows) throws Exception;
	
	/**
	 * 根據ids刪除對應的商品規格參數模板
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	SmResult deleteItemParamByIds(Long [] ids) throws Exception;
	
	
	/**
	 * 根據商品類別ID查詢對應的商品規格參數信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	SmResult findItemParamByCatId(Long id)throws Exception;

	
	/**
	 * 為指定商品類別保存商品規格參數模板
	 * @param cId
	 * @param paramData
	 * @return
	 */
	SmResult saveItemParamForCat(Long cId, String paramData);
	
    /**
     * 根据商品id查询对应的商品规格参数详细信息
     * @param id
     * @return
     * @throws Exception
     */
	SmResult getItemParams(Long itemId)throws Exception;

	
	/**
	 * 为指定商品更新商品类别参数模板
	 * @param cId
	 * @param paramData
	 * @return
	 */
	SmResult updateItemParamForCat(Long cId, String paramData);

}
