package com.ncs.rest.controller;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ncs.common.utils.JsonUtils;
import com.ncs.rest.pojo.ItemCatResult;
import com.ncs.rest.service.ItemCatService;

/**
 * 
 * @Title: ItemCatController.java
 * @Package com.ncs.rest.controller
 * @Description: TODO(商品分类控制器)
 * @author: Hubery Yang
 * @date: May 9, 2019 5:15:36 PM
 * @version V1.0
 * @Copyright: 2019 Inc. All rights reserved.
 *
 */
@Controller
@RequestMapping("/item/cat")
public class ItemCatController {

	@Autowired
	private ItemCatService itemCatService;

	@RequestMapping("/list")
	@ResponseBody
	public String findItemCatList(String callback) {

		ItemCatResult result = itemCatService.findItemCatResult();

		if (StringUtils.isBlank(callback)) {
			return JsonUtils.objectToJson(result);
		} else {
			return callback + "(" + JsonUtils.objectToJson(result) + ");";
		}
	}

}
