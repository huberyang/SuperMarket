package com.ncs.service;

import com.ncs.common.utils.pojo.EasyDataGridResult;
import com.ncs.common.utils.pojo.SmResult;
import com.ncs.pojo.TbItem;

public interface ItemService {

	/**
	 * 根据Item的id查询指定的Item
	 * 
	 * @param itemId
	 * @return TbItem
	 */
	TbItem selectItemById(long itemId) throws Exception;

	/**
	 * 根据分页查询Item
	 * 
	 * @param page
	 * @param rows
	 * @return EasyDataGridResult
	 */
	EasyDataGridResult selectItemByPage(int page, int rows) throws Exception;

	/**
	 * 创建保存商品及其描述信息
	 * 
	 * @param item
	 * @param desc
	 * @param itemParams
	 * @return
	 * @throws Exception
	 */
	SmResult createItem(TbItem item, String desc, String itemParams) throws Exception;

	/**
	 * 删除指定的商品
	 * 
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	SmResult deleteItem(Long[] ids) throws Exception;

	/**
	 * 商品下架
	 * 
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	SmResult outstockItem(Long[] ids) throws Exception;

	/**
	 * 商品上架
	 * 
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	SmResult reshelfItem(Long[] ids) throws Exception;

	/**
	 * 查询商品描述信息
	 * 
	 * @param ids
	 * @return
	 */
	SmResult queryItemDesc(Long itemId);

	/**
	 * 查询商品规格模板参数信息
	 * 
	 * @param ids
	 * @return
	 */
	SmResult queryItemParam(Long itemId);

	/**
	 * 更新商品详细信息
	 * 
	 * @param item
	 * @param desc
	 * @param itemParams
	 * @return
	 */
	SmResult updateItem(TbItem item, String desc, String itemParams);

}
