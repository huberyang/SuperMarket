package com.ncs.rest.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ncs.common.utils.ExceptionUtil;
import com.ncs.common.utils.pojo.SmResult;
import com.ncs.pojo.TbItem;
import com.ncs.pojo.TbItemDesc;
import com.ncs.pojo.TbItemParamItem;
import com.ncs.rest.component.JedisClient;
import com.ncs.rest.service.ItemService;

/**
 * 
 * @Title: ItemController.java
 * @Package com.ncs.rest.controller
 * @Description: TODO(商品详细页面的相关服务)
 * @author: Hubery Yang
 * @date: Jun 17, 2019 3:57:25 PM
 * @version V1.0
 * @Copyright: 2019 Inc. All rights reserved.
 *
 */
@Controller
@RequestMapping("/item")
public class ItemController {
	
	@Value("${redis_tbItem_key}")
	private String redis_tbItem_key;
	@Value("${redis_tbItemDesc_key}")
	private String redis_tbItemDesc_key;
	@Value("${redis_tbItemParamItem_key}")
	private String redis_tbItemParamItem_key;
	
	@Autowired
	private JedisClient jedisClient;
	@Autowired
	private ItemService itemService;

	/**
	 * 商品基本信息服务
	 * 
	 * @param itemId
	 * @return
	 */
	@RequestMapping(value = "/base/{itemId}")
	@ResponseBody
	public SmResult findItem(@PathVariable("itemId") Long itemId) {

		try {
			TbItem item = itemService.findItem(itemId);
			return SmResult.ok(item);
		} catch (Exception e) {
			return SmResult.build(500, ExceptionUtil.getStatckTrace(e));
		}

	}

	/**
	 * 商品描述信息服务
	 * 
	 * @param itemId
	 * @return
	 */
	@RequestMapping(value = "/desc/{itemId}")
	@ResponseBody
	public SmResult findItemDesc(@PathVariable("itemId") Long itemId) {

		try {
			TbItemDesc itemDesc = itemService.findItemDesc(itemId);
			return SmResult.ok(itemDesc);
		} catch (Exception e) {
			return SmResult.build(500, ExceptionUtil.getStatckTrace(e));
		}

	}

	/**
	 * 商品规格参数信息服务
	 * 
	 * @param itemId
	 * @return
	 */
	@RequestMapping(value = "/paramItem/{itemId}")
	@ResponseBody
	public SmResult findItemParamItem(@PathVariable("itemId") Long itemId) {

		try {
			TbItemParamItem itemParamItem = itemService.findItemParamItem(itemId);
			return SmResult.ok(itemParamItem);
		} catch (Exception e) {
			return SmResult.build(500, ExceptionUtil.getStatckTrace(e));
		}

	}

	/**
	 * 同步商品基本信息
	 * @param itemId
	 * @return
	 */
	@RequestMapping(value = "/sync/item/{itemId}")
	@ResponseBody
	public SmResult syncItem(@PathVariable("itemId") Long itemId) {
		// 查询缓存中是否存在该ID的缓存数据，如果存在则删除
		try {
			String result = jedisClient.hget(redis_tbItem_key, itemId + "");
			if (StringUtils.isNotBlank(result)) {
				jedisClient.hdel(redis_tbItem_key, itemId + "");
			}
		} catch (Exception e) {
			return SmResult.build(500, ExceptionUtil.getStatckTrace(e));
		}
		return SmResult.ok();
	}
	
	/**
	 * 同步商品描述信息
	 * @param itemId
	 * @return
	 */
	@RequestMapping(value = "/sync/itemDesc/{itemId}")
	@ResponseBody
	public SmResult syncItemDesc(@PathVariable("itemId") Long itemId) {
		// 查询缓存中是否存在该ID的缓存数据，如果存在则删除
		try {
			String result = jedisClient.hget(redis_tbItemDesc_key, itemId + "");
			if (StringUtils.isNotBlank(result)) {
				jedisClient.hdel(redis_tbItemDesc_key, itemId + "");
			}
		} catch (Exception e) {
			return SmResult.build(500, ExceptionUtil.getStatckTrace(e));
		}
		return SmResult.ok();
	}
	
	/**
	 * 同步商品规格参数信息
	 * @param itemId
	 * @return
	 */
	@RequestMapping(value = "/sync/itemParamItem/{itemId}")
	@ResponseBody
	public SmResult syncItemParamItem(@PathVariable("itemId") Long itemId) {
		// 查询缓存中是否存在该ID的缓存数据，如果存在则删除
		try {                                
		    String result = jedisClient.hget(redis_tbItemParamItem_key, itemId + "");
			if (StringUtils.isNotBlank(result)) {
				jedisClient.hdel(redis_tbItemParamItem_key, itemId + "");
			}
		} catch (Exception e) {
			return SmResult.build(500, ExceptionUtil.getStatckTrace(e));
		}
		return SmResult.ok();
	}

}
