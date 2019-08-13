package com.ncs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ncs.common.utils.HttpClientUtils;
import com.ncs.common.utils.pojo.EasyDataGridResult;
import com.ncs.common.utils.pojo.SmResult;
import com.ncs.pojo.TbItem;
import com.ncs.service.ItemParamService;
import com.ncs.service.ItemService;

/**
 * 
 * @Title: ItemParamController.java
 * @Package com.ncs.controller
 * @Description: TODO(商品规格参数及其详细信息操作)
 * @author: Hubery Yang
 * @date: Apr 25, 2019 11:39:02 PM
 * @version V1.0
 * @Copyright: 2019 Inc. All rights reserved.
 *
 */

@Controller
@RequestMapping("/item")
public class ItemParamController {

	@Value("${rest_service_base_url}")
	private String rest_service_base_url;
	@Value("${rest_sync_itemParamItem_url}")
	private String rest_sync_itemParamItem_url;
	
	@Value("${portal_base_url}")
	private String portal_base_url;
	@Value("${gener_static_page}")
	private String gener_static_page;

	@Autowired
	private ItemParamService itemParamService;
	@Autowired
	private ItemService itemService;

	/**
	 * 查询商品规格模板，及其所对应的商品类目
	 * 
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/param/list")
	public EasyDataGridResult findItemParamList(Integer page, Integer rows) throws Exception {

		// use pageHelper to Pagination
		EasyDataGridResult result = itemParamService.selectItemParamByPage(page, rows);
		return result;
	}

	/**
	 * 根據Ids刪除對應的商品規格模板
	 * 
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/param/delete", method = RequestMethod.POST)
	public SmResult deleteItemParamById(Long[] ids) throws Exception {

		SmResult result = itemParamService.deleteItemParamByIds(ids);
		// 更新操作结束后，执行数据同步操作，清除缓存相关数据
		for(int i=0;i<ids.length;i++) {
			HttpClientUtils.doGet(rest_service_base_url + rest_sync_itemParamItem_url + ids[i]);
		}
		return result;
	}

	/**
	 * 查詢當前商品類別下是否已經存在商品規格模板
	 * 
	 * @param nodeId
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/param/query/itemcatid/{cId}", method = RequestMethod.GET)
	public SmResult checkItemParamForCat(@PathVariable("cId") Long cId) throws Exception {

		SmResult result = itemParamService.findItemParamByCatId(cId);
		return result;

	}

	/**
	 * 為指定商品類別保存商品規格參數模板
	 * 
	 * @param cId
	 * @param paramData
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/param/save/{cId}", method = RequestMethod.POST)
	public SmResult saveItemParamForCat(@PathVariable("cId") Long cId, String paramData) throws Exception {

		SmResult result = itemParamService.saveItemParamForCat(cId, paramData);
		// 查询当前类别下的所有商品ID，清除这些商品ID缓存
		List<TbItem> itemList = itemService.findItemByCateId(cId);
		if (itemList != null && !itemList.isEmpty()) {
			for (TbItem item : itemList) {
				// 更新操作结束后，执行数据同步操作，清除缓存相关数据
				HttpClientUtils.doGet(rest_service_base_url + rest_sync_itemParamItem_url + item.getId());
				
				//更新操作结束后，执行生成对应商品的静态页面
				HttpClientUtils.doGet(portal_base_url + gener_static_page+ item.getId()+".action");
			}
		}
		
		
		return result;
	}

	/**
	 * 为指定商品类别保存商品规格参数模板
	 * 
	 * @param cId
	 * @param paramData
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/param/update/{cId}", method = RequestMethod.POST)
	public SmResult updateItemParamForCat(@PathVariable("cId") Long cId, String paramData) throws Exception {

		SmResult result = itemParamService.updateItemParamForCat(cId, paramData);
		// 查询当前类别下的所有商品ID，清除这些商品ID缓存
		List<TbItem> itemList = itemService.findItemByCateId(cId);
		if (itemList != null && !itemList.isEmpty()) {
			for (TbItem item : itemList) {
				// 更新操作结束后，执行数据同步操作，清除缓存相关数据
				HttpClientUtils.doGet(rest_service_base_url + rest_sync_itemParamItem_url + item.getId());
			}
		}
		return result;
	}

	/**
	 * 根据商品id查询规格参数详细信息
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/page/params/{itemId}", method = RequestMethod.POST)
	public SmResult getItemParams(@PathVariable("itemId") Long itemId) throws Exception {

		SmResult result = itemParamService.getItemParams(itemId);
		return result;
	}

}
