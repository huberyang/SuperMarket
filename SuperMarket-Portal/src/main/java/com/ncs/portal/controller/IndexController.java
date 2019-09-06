package com.ncs.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	public String showItemPage(Model model, @PathVariable("itemId") Long itemId) throws Exception {
		// 根据商品id查询对应的商品信息
		 ItemDeatils result = contentService.getItemById(itemId);
		 model.addAttribute("item", result);
		return "item";
	}

	@RequestMapping("/order/order-cart")
	public String showOrderPage() throws Exception {
		// 根据商品id查询对应的商品信息
		return "order-cart";
	}

	@RequestMapping(value="/item/param/{itemId}",produces=MediaType.TEXT_HTML_VALUE+";charset=utf-8")
	@ResponseBody
	public String showItemParam(Model model, @PathVariable("itemId") Long itemId) throws Exception {
		 // 根据商品id查询对应的商品信息
		 TbItemParamItem result = contentService.getItemParamById(itemId);
		 return result.getParamData();
	}
	
	@RequestMapping(value="/item/desc/{itemId}",produces=MediaType.TEXT_HTML_VALUE+";charset=utf-8")
	@ResponseBody
	public String showItemDesc(Model model, @PathVariable("itemId") Long itemId) throws Exception {
		 // 根据商品id查询对应的商品信息
		 TbItemDesc result = contentService.getItemDescById(itemId);
		 return result.getItemDesc();
	}


}
