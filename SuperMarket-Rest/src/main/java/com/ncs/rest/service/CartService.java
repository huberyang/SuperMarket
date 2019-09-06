package com.ncs.rest.service;

import java.util.List;

import com.ncs.common.utils.pojo.SmResult;
import com.ncs.rest.pojo.CartItem;

public interface CartService {
	/**
	 * 根据用户id取出redis中的购物车列表数据
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<CartItem> retrieveCartItemListFromRedis(Long userId) throws Exception;

	/**
	 * 保存用户购物车列表信息到redis中
	 * 
	 * @param userId
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public SmResult saveCartItemListToRedis(Long userId, List<CartItem> list) throws Exception;

}
