package com.ncs.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ncs.common.utils.ExceptionUtil;
import com.ncs.common.utils.pojo.SmResult;
import com.ncs.pojo.TbItem;
import com.ncs.search.service.ItemService;

@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;

	
	/**
	 * 导入所有状态为 “1”的商品数据到索引库
	 * @return
	 */
	@RequestMapping("/importAllData")
	@ResponseBody
	public SmResult importAllData() {

		try {
			SmResult result = itemService.importAllData();
			return result;

		} catch (Exception e) {
			e.printStackTrace();
			return SmResult.build(500, ExceptionUtil.getStatckTrace(e));
		}
	}
	
	/**
	 * 删除指定商品的索引数据
	 * @return
	 */
	@RequestMapping("/delItemIndexData")
	@ResponseBody
	public SmResult delItemIndex(Long[] ids) {

		try {
			SmResult result = itemService.delItemIndex(ids);
			return result;

		} catch (Exception e) {
			e.printStackTrace();
			return SmResult.build(500, ExceptionUtil.getStatckTrace(e));
		}
	}
	
	/**
	 * 添加指定商品的索引数据
	 * @return
	 */
	@RequestMapping("/addItemIndexData")
	@ResponseBody
	public SmResult addItemIndex(TbItem item) {

		try {
			SmResult result = itemService.addItemIndex(item);
			return result;

		} catch (Exception e) {
			e.printStackTrace();
			return SmResult.build(500, ExceptionUtil.getStatckTrace(e));
		}
	}
	
	
	
}
