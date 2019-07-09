package com.ncs.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ncs.pojo.TbItemDesc;
import com.ncs.pojo.TbItemParamItem;
import com.ncs.portal.pojo.ItemDeatils;
import com.ncs.portal.service.ContentService;

@Controller
public class IndexController {

	@Autowired
	private ContentService contentService;

	/*
	 * 
	 * <servlet-mapping> <servlet-name>springMVC</servlet-name> <!--
	 * <url-pattern>/</url-pattern> --> </servlet-mapping>
	 * 
	 * @RequestMapping("/") public String showIndex() { return "index"; }
	 *
	 * 
	 * <mvc:resources location="/WEB-INF/css/" mapping="/css/**"></mvc:resources>
	 * <mvc:resources location="/WEB-INF/js/" mapping="/js/**"></mvc:resources>
	 * <mvc:resources location="/WEB-INF/images/"
	 * mapping="/images/**"></mvc:resources> 这样配置后可以把css/js/image等资源放在web-inf文件夹里面
	 */

	@RequestMapping("/index")
	public String showIndex(Model model) throws Exception {

		String result = contentService.getBigADList();
		model.addAttribute("ad1", result);
		return "index";
	}
	

	
	@RequestMapping("/item/{itemId}")
	public String showItemPage(Model model, @PathVariable("itemId") String itemId) throws Exception {
		// 根据商品id查询对应的商品信息
		 ItemDeatils result = contentService.getItemById(itemId);
		 model.addAttribute("item", result);
		return "item";
	}


	@RequestMapping("/item/param/{itemId}")
	public void showItemParam(Model model, @PathVariable("itemId") String itemId) throws Exception {
		// 根据商品id查询对应的商品信息
		TbItemParamItem result = contentService.getItemParamById(itemId);
		 model.addAttribute("itemParam", result);
	}
	
	@RequestMapping("/item/desc/{itemId}")
	public void showItemDesc(Model model, @PathVariable("itemId") String itemId) throws Exception {
		// 根据商品id查询对应的商品信息
		 TbItemDesc result = contentService.getItemDescById(itemId);
		 model.addAttribute("itemDesc", result);
	}


}
