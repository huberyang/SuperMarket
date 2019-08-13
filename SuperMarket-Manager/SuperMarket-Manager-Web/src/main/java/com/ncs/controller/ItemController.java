package com.ncs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ncs.common.utils.HttpClientUtils;
import com.ncs.common.utils.JsonUtils;
import com.ncs.common.utils.pojo.EasyDataGridResult;
import com.ncs.common.utils.pojo.SmResult;
import com.ncs.pojo.TbItem;
import com.ncs.service.ItemService;

/**
 * 
 * @Title: ItemController.java
 * @Package com.ncs.controller
 * @Description: TODO(商品控制器)
 * @author: Hubery Yang
 * @date: Mar 26, 2019 11:42:01 PM
 * @version V1.0
 * @Copyright: 2019 Inc. All rights reserved.
 *
 */
@Controller
public class ItemController {

	@Value("${rest_service_base_url}")
	private String rest_service_base_url;
	@Value("${rest_sync_item_url}")
	private String rest_sync_item_url;
	@Value("${rest_sync_itemDesc_url}")
	private String rest_sync_itemDesc_url;
	@Value("${rest_sync_itemParamItem_url}")
	private String rest_sync_itemParamItem_url;
	@Value("${search_base_url}")
	private String search_base_url;
	@Value("${sync_item_index_reimport}")
	private String sync_item_index_reimport;
	@Value("${sync_item_index_del}")
	private String sync_item_index_del;
	@Value("${sync_item_index_add}")
	private String sync_item_index_add;
	
	@Value("${portal_base_url}")
	private String portal_base_url;
	@Value("${gener_static_page}")
	private String gener_static_page;

	@Autowired
	private ItemService itemService;

	/**
	 * find item by it itemId resultful api format GET
	 * "http://localhost:8080/item/{itemId}
	 * 
	 * @param itemId
	 * @return json item
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/item/{itemId}")
	public TbItem findItemById(@PathVariable Long itemId) throws Exception {

		TbItem item = itemService.selectItemById(itemId);
		return item;
	}

	/**
	 * find the item list use pageHeleper to pagination GET
	 * "http://localhost:8080/item/list?page=1&rows=30".
	 * 
	 * @return json items
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/item/list")
	public EasyDataGridResult findItemList(Integer page, Integer rows) throws Exception {

		// use pageHelper to Pagination
		EasyDataGridResult result = itemService.selectItemByPage(page, rows);

		return result;
	}

	/**
	 * 後台創建商品，保存商品信息及其描述,及其规格参数详细信息（描述是由富文本編輯器生成出來的html）
	 * 
	 * @param item
	 * @param desc
	 * @param itemParams
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/item/save", method = RequestMethod.POST)
	public SmResult createItem(TbItem item, String desc, String itemParams) throws Exception {

		SmResult result = itemService.createItem(item, desc, itemParams);
		// 更新操作结束后，执行数据同步操作，清除缓存相关数据
		HttpClientUtils.doGet(rest_service_base_url + rest_sync_item_url + item.getId());
		HttpClientUtils.doGet(rest_service_base_url + rest_sync_itemDesc_url + item.getId());
		HttpClientUtils.doGet(rest_service_base_url + rest_sync_itemParamItem_url + item.getId());
		// 更新操作结束后，执行数据同步操作，更新索引库数据
		HttpClientUtils.doPostJson(search_base_url + sync_item_index_add, JsonUtils.objectToJson(item));
		
		//更新操作结束后，执行生成对应商品的静态页面
		HttpClientUtils.doGet(portal_base_url + gener_static_page+ item.getId()+".action");
		return result;
	}

	/**
	 * 删除指定的商品
	 * 
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/rest/item/delete", method = RequestMethod.POST)
	public SmResult deleteItem(Long[] ids) throws Exception {

		SmResult result = itemService.deleteItem(ids);
		// 更新操作结束后，执行数据同步操作，清除缓存相关数据
		for (int i = 0; i < ids.length; i++) {
			HttpClientUtils.doGet(rest_service_base_url + rest_sync_item_url + ids[i]);
			HttpClientUtils.doGet(rest_service_base_url + rest_sync_itemDesc_url + ids[i]);
			HttpClientUtils.doGet(rest_service_base_url + rest_sync_itemParamItem_url + ids[i]);

			// 索引同步数据的操作只能在controller层操作，应为service层存在事务，需要在完成所有操作后才会提交数据，但是可能会存在索引库同步后，数据提交失败导致数据不一致的情况！
			// 更新操作结束后，执行数据同步操作，更新索引库数据
			HttpClientUtils.doGet(search_base_url + sync_item_index_del +ids[i]);
		}

		return result;
	}

	/**
	 * 商品下架
	 * ----更新rest服务，同步数据
	 * 
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/rest/item/outstock", method = RequestMethod.POST)
	public SmResult outstockItem(Long[] ids) throws Exception {
		
		SmResult result = itemService.outstockItem(ids);
		// 更新操作结束后，执行数据同步操作，清除缓存相关数据
		for (int i = 0; i < ids.length; i++) {
			HttpClientUtils.doGet(rest_service_base_url + rest_sync_item_url + ids[i]);
			HttpClientUtils.doGet(rest_service_base_url + rest_sync_itemDesc_url + ids[i]);
			HttpClientUtils.doGet(rest_service_base_url + rest_sync_itemParamItem_url + ids[i]);
			
			// 更新操作结束后，执行数据同步操作，更新索引库数据
			HttpClientUtils.doGet(search_base_url + sync_item_index_del + ids[i]);
		}

		return result;
	}

	/**
	 * 商品上架
	 * 
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/rest/item/reshelf", method = RequestMethod.POST)
	public SmResult reshelfItem(Long[] ids) throws Exception {
		
		SmResult result = itemService.reshelfItem(ids);
		for (int i = 0; i < ids.length; i++) {
			// 先查询出对应商品的详细信息
			TbItem item = itemService.selectItemById(ids[i]);
			// 更新操作结束后，执行数据同步操作，更新索引库数据
			HttpClientUtils.doPostJson(search_base_url + sync_item_index_add, JsonUtils.objectToJson(item));
		}

		return result;
	}

	/**
	 * 根据商品Id查询商品描述信息
	 * 
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/rest/item/query/item/desc/{itemId}", method = RequestMethod.GET)
	public SmResult queryItemDesc(@PathVariable("itemId") Long itemId) throws Exception {
		SmResult result = itemService.queryItemDesc(itemId);
		return result;
	}

	/**
	 * 根据商品Id查询商品规格参数
	 * 
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/rest/item/param/item/query/{itemId}", method = RequestMethod.GET)
	public SmResult queryItemParam(@PathVariable("itemId") Long itemId) throws Exception {
		SmResult result = itemService.queryItemParam(itemId);
		return result;
	}

	/**
	 * 更新商品详细信息（商品信息，描述，参数）
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/rest/item/update", method = RequestMethod.POST)
	public SmResult updateItemDetails(TbItem item, String desc, String itemParams) throws Exception {
		
		SmResult result = itemService.updateItem(item, desc, itemParams);
		// 更新操作结束后，执行数据同步操作，清除缓存相关数据
		HttpClientUtils.doGet(rest_service_base_url + rest_sync_item_url + item.getId());
		HttpClientUtils.doGet(rest_service_base_url + rest_sync_itemDesc_url + item.getId());
		HttpClientUtils.doGet(rest_service_base_url + rest_sync_itemParamItem_url + item.getId());

		return result;

	}

}
