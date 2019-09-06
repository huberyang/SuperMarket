package com.ncs.order.service;

import com.ncs.common.utils.pojo.SmResult;
import com.ncs.vo.OrderInfo;

public interface OrderService {

	/**
	 * 接收订单细节，创建订单
	 * 
	 * @param orderInfo
	 * @return
	 * @throws Exception
	 */
	public SmResult createOrder(OrderInfo orderInfo) throws Exception;

}
