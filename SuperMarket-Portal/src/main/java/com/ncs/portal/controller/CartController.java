package com.ncs.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ncs.common.utils.ExceptionUtil;
import com.ncs.common.utils.pojo.SmResult;
import com.ncs.portal.pojo.CartItem;
import com.ncs.portal.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;


	@RequestMapping("/addCart/{itemId}")
	public String addItemToCart(@PathVariable("itemId") Long itemId, @RequestParam Integer num,
			HttpServletRequest request, HttpServletResponse response) {

		try {
			SmResult result = cartService.AddToCart(itemId, num, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "cartSuccess";
	}

	@RequestMapping("/show")
	public String cartShow(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			List<CartItem> cartItemList = cartService.getCartDetails(request, response);
			model.addAttribute("cartList", cartItemList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "cart";
	}


	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody
	public SmResult cartUpdate(@PathVariable("itemId") Long itemId, @PathVariable("num") Integer num,
			HttpServletRequest request,
			HttpServletResponse response) {
		try {
		SmResult result = cartService.updateCartItemNum(itemId, num, request, response);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return SmResult.build(500, ExceptionUtil.getStatckTrace(e));
		}

	}

	@RequestMapping("/delete/{itemId}")
	public String cartDelete(@PathVariable("itemId") Long itemId, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			cartService.delCartItem(itemId, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/cart/show.html";
	}

	@RequestMapping("/transfer/{userId}")
	@ResponseBody
	public SmResult transferCartDataToRedis(@PathVariable("userId") Long userId, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			SmResult result = cartService.transferCookieDataToRedis(userId, request, response);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return SmResult.build(500, ExceptionUtil.getStatckTrace(e));
		}
	}

}
