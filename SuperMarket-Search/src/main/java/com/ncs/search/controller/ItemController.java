package com.ncs.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ncs.common.utils.ExceptionUtil;
import com.ncs.common.utils.pojo.SmResult;
import com.ncs.search.service.ItemService;

@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;

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

}
