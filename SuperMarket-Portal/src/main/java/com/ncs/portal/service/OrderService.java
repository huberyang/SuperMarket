package com.ncs.portal.service;

import com.ncs.vo.OrderInfo;

public interface OrderService {

	public Integer createOrder(OrderInfo orderInfo) throws Exception;

}
