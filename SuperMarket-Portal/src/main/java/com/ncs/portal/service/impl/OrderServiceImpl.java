package com.ncs.portal.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ncs.common.utils.HttpClientUtils;
import com.ncs.common.utils.JsonUtils;
import com.ncs.common.utils.pojo.SmResult;
import com.ncs.portal.service.OrderService;
import com.ncs.vo.OrderInfo;

@Service
public class OrderServiceImpl implements OrderService {

	@Value("${ORDER_SERVICE_BASE_URL}")
	private String ORDER_SERVICE_BASE_URL;

	@Value("${ORDER_SERVICE_CREATE_ORDER}")
	private String ORDER_SERVICE_CREATE_ORDER;


	@Override
	public String createOrder(OrderInfo orderInfo) {

		String data = JsonUtils.objectToJson(orderInfo);

		// request Order service to create order
		String postResult = HttpClientUtils.doPostJson(ORDER_SERVICE_BASE_URL + ORDER_SERVICE_CREATE_ORDER, data);

		SmResult result = SmResult.format(postResult);
		String orderId = (String) result.getData();
		return orderId;
	}

}
