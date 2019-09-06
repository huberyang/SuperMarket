package com.ncs.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ncs.common.utils.CookieUtils;
import com.ncs.common.utils.HttpClientUtils;
import com.ncs.common.utils.JsonUtils;
import com.ncs.common.utils.pojo.SmResult;
import com.ncs.pojo.TbUser;
import com.ncs.portal.pojo.CartItem;
import com.ncs.portal.pojo.ItemDeatils;
import com.ncs.portal.service.CartService;
import com.ncs.portal.service.ContentService;
import com.ncs.portal.service.UserService;

/**
 * 
 * @Title: CartServiceImpl.java
 * @Package com.ncs.portal.service.impl
 * @Description: TODO(购物车接口实现，cookie版本)
 * @author: Hubery Yang
 * @date: Aug 28, 2019 10:36:55 PM
 * @version V1.0
 * @Copyright: 2019 Inc. All rights reserved.
 *
 */

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private ContentService itemService;
	@Autowired
	private UserService userService;

	// 设置cookie的过期时间
	@Value("${Cookie_Expire}")
	private Integer Cookie_Expire;

	@Value("${SSO_BASE_URL}")
	private String SSO_BASE_URL;
	@Value("${SSO_USER_TOKEN_SERVICE}")
	private String SSO_USER_TOKEN_SERVICE;
	@Value("${rest_server_url}")
	private String rest_server_url;

	@Value("${REST_SERVER_CART_ADD}")
	private String REST_SERVER_CART_ADD;
	@Value("${REST_SERVER_CART_RETRIEVE}")
	private String REST_SERVER_CART_RETRIEVE;

	/**
	 * 从cookie中取出商品列表
	 * 
	 * @param request
	 * @return
	 */
	private List<CartItem> getCateItemListFromCookie(HttpServletRequest request) {

		String cookieJson = CookieUtils.getCookieValue(request, "SM_CART", true);
		// 将取出的商品列表数据（json）转换为java对象
		return cookieJson == null ? new ArrayList<CartItem>() : JsonUtils.jsonToList(cookieJson, CartItem.class);

	}

	/**
	 * 根据用户id从redis中取出商品列表
	 * 
	 * @param userId
	 * @return
	 */
	private List<CartItem> getCateItemListFromRedis(Long userId) {

		String jsonData = HttpClientUtils.doGet(rest_server_url + REST_SERVER_CART_RETRIEVE + userId);
		// 将取出的商品列表数据（json）转换为java对象
		return jsonData == "" ? new ArrayList<CartItem>()
				: (List<CartItem>) SmResult.formatToList(jsonData, CartItem.class).getData();
	}

	@Override
	public SmResult AddToCart(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// 先查询当前cookie下是否用户已经登陆
		TbUser user = userService.getUserByToken(request, response);

		if (user == null) {
			// 1.先根据商品的id，查询cookie，判断是否存在该商品,如果存在则直接增加商品在cookie中的数量 SM_TOKEN
			List<CartItem> cartItemList = getCateItemListFromCookie(request);

			boolean existFlag = false;
			// 对于我们存在cookie中的商品，我们可以直接保存一个商品集合到cookie中
			for (CartItem cateItem : cartItemList) {
				// 如果cookie中已经存在该商品
				if (cateItem != null && cateItem.getItemId().equals(itemId)) {
					existFlag = true;
					// 商品添加num
					cateItem.setNum(cateItem.getNum() + num);
				}
			}
			// 2.如果商品不存在，则根据商品Id，调用商品查询服务，将返回的商品信息（包含商品的数量）添加到cookie中,返回结果SmResult
			if (!existFlag) {
				ItemDeatils itemDeatils = itemService.getItemById(itemId);
				CartItem cartItem = new CartItem();
				cartItem.setItemId(itemId);
				cartItem.setTitle(itemDeatils.getTitle());
				cartItem.setPrice(itemDeatils.getPrice());
				cartItem.setNum(num);
				// 取第一张图片
				cartItem.setImage(itemDeatils.getImages()[0]);
				// 保存商品信息到列表内
				cartItemList.add(cartItem);
			}
			// 将列表保存到cookie中，并返回success结果
			CookieUtils.setCookie(request, response, "SM_CART", JsonUtils.objectToJson(cartItemList), Cookie_Expire,
					true);
		} else {

			// 已经登陆,发送http请求将商品保存到redis购物车列表中

			// 1.我们需要先请求，获取到redis缓存中的数据，将我们新添加的数据加入
			List<CartItem> cartItemList = getCateItemListFromRedis(user.getId());
			boolean existFlag = false;

			for (CartItem item : cartItemList) {
				if (item != null && item.getItemId().equals(itemId)) {
					existFlag = true;
					// 商品添加num
					item.setNum(item.getNum() + num);
				}
			}
			// 如果redis緩存中不存在
			if (!existFlag) {
				ItemDeatils itemDeatils = itemService.getItemById(itemId);
				CartItem cartItem = new CartItem();
				cartItem.setItemId(itemId);
				cartItem.setTitle(itemDeatils.getTitle());
				cartItem.setPrice(itemDeatils.getPrice());
				cartItem.setNum(num);
				// 取第一张图片
				cartItem.setImage(itemDeatils.getImages()[0]);
				// 保存商品信息到列表内
				cartItemList.add(cartItem);
			}

			// 2.然后将我们更新后的购物车数据列表从新放入redis
			// 保存跟新后的购物车列表到redis缓存中
			HttpClientUtils.doPostJson(rest_server_url + REST_SERVER_CART_ADD + user.getId(),
					JsonUtils.objectToJson(cartItemList));
		}

		return SmResult.ok();
	}

	@Override
	public List<CartItem> getCartDetails(HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<CartItem> cateItemList = null;
		// 判断用户是否已经登陆
		TbUser user = userService.getUserByToken(request, response);

		if (user == null) {
			// 未登录,从cookie中取出购物车列表数据
			cateItemList = getCateItemListFromCookie(request);
		} else {
			// 已经登陆,发送http请求从redis中取出用户购物车列表信息
			cateItemList = getCateItemListFromRedis(user.getId());
		}

		return cateItemList;
	}

	@Override
	public SmResult updateCartItemNum(Long itemId, Integer num, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 1.从cookie中取出商品列表，匹配对应的商品，重新设定商品的数量，将更新后的商品列表放回cookie中，返回成功消息
		List<CartItem> cartItemList = null;
		// 判断用户是否已经登陆
		TbUser user = userService.getUserByToken(request, response);

		if (user == null) {
			cartItemList = getCateItemListFromCookie(request);
			for (CartItem cartItem : cartItemList) {
				if (cartItem != null && cartItem.getItemId().equals(itemId)) {// 注意变量和对象比较数据值使用 == 和 equal
					cartItem.setNum(num);
					break;
				}
			}

			// 将商品列表放回cookie
			CookieUtils.setCookie(request, response, "SM_CART", JsonUtils.objectToJson(cartItemList), Cookie_Expire,
					true);
		} else {
			// 已经登陆,发送http请求从redis中取出用户购物车列表信息
			cartItemList = getCateItemListFromRedis(user.getId());
			for (CartItem cartItem : cartItemList) {
				if (cartItem != null && cartItem.getItemId().equals(itemId)) {
					cartItem.setNum(num);
					break;
				}
			}

			// 然后将我们更新后的购物车数据列表从新放入redis
			HttpClientUtils.doPostJson(rest_server_url + REST_SERVER_CART_ADD + user.getId(),
					JsonUtils.objectToJson(cartItemList));
		}

		return SmResult.ok();

	}

	@Override
	public SmResult delCartItem(Long itemId, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 1.从cookie中取出商品列表，匹配对应的商品，从商品列表中移除对应的商品，将更新后的商品列表放回cookie中，返回成功消息

		List<CartItem> cartItemList = null;
		// 判断用户是否已经登陆
		TbUser user = userService.getUserByToken(request, response);

		if (user == null) {
			cartItemList = getCateItemListFromCookie(request);
			for (CartItem cartItem : cartItemList) {
				if (cartItem != null && cartItem.getItemId().equals(itemId)) {
					cartItemList.remove(cartItem);
					break;
				}
			}

			// 将商品列表放回cookie
			CookieUtils.setCookie(request, response, "SM_CART", JsonUtils.objectToJson(cartItemList), Cookie_Expire,
					true);
		} else {
			// 已经登陆,发送http请求从redis中取出用户购物车列表信息
			cartItemList = getCateItemListFromRedis(user.getId());
			for (CartItem cartItem : cartItemList) {
				if (cartItem != null && cartItem.getItemId().equals(itemId)) {
					cartItemList.remove(cartItem);
					break;
				}
			}

			// 然后将我们更新后的购物车数据列表从新放入redis
			HttpClientUtils.doPostJson(rest_server_url + REST_SERVER_CART_ADD + user.getId(),
					JsonUtils.objectToJson(cartItemList));

		}

		return SmResult.ok();
	}

	@Override
	public SmResult transferCookieDataToRedis(Long userId, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		List<CartItem> cartItemListFromCookie = getCateItemListFromCookie(request);
		List<CartItem> cartItemListFromRedis = getCateItemListFromRedis(userId);

		if (!cartItemListFromCookie.isEmpty()) {
			for (CartItem cartItem : cartItemListFromCookie) {
				if (cartItemListFromRedis == null) {
					cartItemListFromRedis = new ArrayList<>();
				}
				cartItemListFromRedis.add(cartItem);
			}

			// save to redis
			HttpClientUtils.doPostJson(rest_server_url + REST_SERVER_CART_ADD + userId,
					JsonUtils.objectToJson(cartItemListFromRedis));
		}

		return SmResult.ok();
	}

}
