package com.ncs.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ncs.common.utils.JsonUtils;
import com.ncs.common.utils.pojo.SmResult;
import com.ncs.rest.component.JedisClient;
import com.ncs.rest.pojo.CartItem;
import com.ncs.rest.service.CartService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private JedisClient jedisClient;

	@Value("${redis_cartItem_list_key}")
	private String redis_cartItem_list_key;


	@Override
	public List<CartItem> retrieveCartItemListFromRedis(Long userId) {
		// 1.根据用户Id从redis中取出购物车商品列表
		String result = jedisClient.hget(redis_cartItem_list_key, userId + "");
		List<CartItem> cartItemList = new ArrayList<CartItem>();
		if (result != null) {
			cartItemList = JsonUtils.jsonToList(result, CartItem.class);
			return cartItemList;
		}

		return cartItemList;
	}


	@Override
	public SmResult saveCartItemListToRedis(Long userId, List<CartItem> list) throws Exception {
		// 1.根据用户Id保存购物车商品列表信息到redis
		jedisClient.hset(redis_cartItem_list_key, userId + "", JsonUtils.objectToJson(list));
		return SmResult.ok();
	}


}
