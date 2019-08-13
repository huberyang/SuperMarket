package com.ncs.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ncs.common.utils.ExceptionUtil;
import com.ncs.common.utils.pojo.SmResult;
import com.ncs.portal.service.StaticPageGenerService;

@Controller
public class ItemStaticPageController {
	
	@Autowired
	private StaticPageGenerService staticPageGenerService;
	
	
	@RequestMapping("/item/gene/{itemId}")
	@ResponseBody
	public SmResult generItemPage(@PathVariable Long itemId) {
		try {
			SmResult staticPageServiceOk = staticPageGenerService.staticPageService(itemId);
			return staticPageServiceOk;
		} catch (Exception e) {
			e.printStackTrace();
			return SmResult.build(500, ExceptionUtil.getStatckTrace(e));
		}
	}
	

}
