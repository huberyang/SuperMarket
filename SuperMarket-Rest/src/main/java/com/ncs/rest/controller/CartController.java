package com.ncs.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ncs.common.utils.ExceptionUtil;
import com.ncs.common.utils.pojo.SmResult;
import com.ncs.rest.pojo.CartItem;
import com.ncs.rest.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	public CartService cartService;

	@RequestMapping("/save/{userId}")
	@ResponseBody
	public SmResult saveCartItemListToRedis(@PathVariable Long userId, @RequestBody List<CartItem> list) {

		try {
			SmResult result = cartService.saveCartItemListToRedis(userId, list);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return SmResult.build(500, ExceptionUtil.getStatckTrace(e));
		}

	}

	@RequestMapping("/retrieve/{userId}")
	@ResponseBody
	public SmResult retrieveCartItemListFromRedis(@PathVariable Long userId) {

		try {
			List<CartItem> result = cartService.retrieveCartItemListFromRedis(userId);
			return SmResult.ok(result);
		} catch (Exception e) {
			e.printStackTrace();
			return SmResult.build(500, ExceptionUtil.getStatckTrace(e));

		}
	}

}
