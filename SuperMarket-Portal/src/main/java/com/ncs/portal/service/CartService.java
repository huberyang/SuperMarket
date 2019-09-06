package com.ncs.portal.service;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ncs.common.utils.pojo.SmResult;
import com.ncs.portal.pojo.CartItem;

/**
 * 
 * @Title: CateService.java
 * @Package com.ncs.portal.service
 * @Description: TODO(购物车（cookie版）服务接口)
 * @author: Hubery Yang
 * @date: Aug 28, 2019 10:31:51 PM
 * @version V1.0
 * @Copyright: 2019 Inc. All rights reserved.
 *
 */
public interface CartService {

	/**
	 * 将商品添加到购物车中，cookie版 1.如果用户已经登陆，购物车商品列表放入redis中 2.如果用户未登陆，购物车商品列表放入cookie中
	 * 
	 * @param id
	 * @param num
	 * @param request
	 * @param response
	 * @return
	 */
	public SmResult AddToCart(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response)
			throws Exception;

	/**
	 * 展示购物车商品列表,需要检查用户是否已经登陆， 1.如果用户已经登陆，从redis中取购物车商品列表 2.如果用户未登陆，从cookie中取购物车商品列表
	 * 
	 * @param request
	 * @return
	 */
	public List<CartItem> getCartDetails(HttpServletRequest request, HttpServletResponse response) throws Exception;

	/**
	 * 修改购物车中商品的数量
	 * 
	 * @param itemId
	 * @param num
	 * @param request
	 * @param response
	 * @return
	 */
	public SmResult updateCartItemNum(Long itemId, Integer num, HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	/**
	 * 删除购物车中的商品
	 * 
	 * @param itemId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public SmResult delCartItem(Long itemId, HttpServletRequest request, HttpServletResponse response) throws Exception;


	/**
	 * 用户登陆后，需要将用户未登陆时加入到cookie购物车内的商品列表保存到登陆时的redis商品列表中
	 * 
	 * @param userId
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public SmResult transferCookieDataToRedis(Long userId, HttpServletRequest request, HttpServletResponse response)
			throws Exception;

}
