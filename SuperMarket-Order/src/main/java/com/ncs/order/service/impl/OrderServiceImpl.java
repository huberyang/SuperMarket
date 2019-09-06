package com.ncs.order.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ncs.common.utils.pojo.SmResult;
import com.ncs.mapper.TbOrderItemMapper;
import com.ncs.mapper.TbOrderMapper;
import com.ncs.mapper.TbOrderShippingMapper;
import com.ncs.order.component.JedisClient;
import com.ncs.order.service.OrderService;
import com.ncs.pojo.TbOrderItem;
import com.ncs.pojo.TbOrderShipping;
import com.ncs.vo.OrderInfo;

@Service
public class OrderServiceImpl implements OrderService {

	@Value("${REDIS_ORDER_GENE_KEY}")
	private String REDIS_ORDER_GENE_KEY;
	@Value("${ORDER_ID_BEGIN}")
	private String ORDER_ID_BEGIN;
	@Value("${REDIS_ORDER_DETAIL_GEN_KEY}")
	private String REDIS_ORDER_DETAIL_GEN_KEY;
	@Autowired
	private TbOrderMapper tbOrderMapper;
	@Autowired
	private TbOrderItemMapper tbOrderItemMapper;
	@Autowired
	private TbOrderShippingMapper tbOrderShippingMapper;

	@Autowired
	private JedisClient jedisClient;

	@Override
	public SmResult createOrder(OrderInfo orderInfo) throws Exception {

		// because redis was single thread（no repeat）, so when can use incr command to
		// generate the orderId,

		String id = jedisClient.get("REDIS_ORDER_GENE_KEY");

		if (StringUtils.isBlank(id)) {
			// 如果订单号生成Key不存在,需要我们提前设定
			jedisClient.set(REDIS_ORDER_GENE_KEY, ORDER_ID_BEGIN);
		}

		Long orderId = jedisClient.incr(REDIS_ORDER_GENE_KEY);

		// complete the order
		orderInfo.setOrderId(orderId.toString());
		// 状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
		orderInfo.setStatus(1);
		Date currDate = new Date();
		orderInfo.setCreateTime(currDate);
		orderInfo.setUpdateTime(currDate);
		tbOrderMapper.insert(orderInfo);

		// complete the orderItem list
		for (TbOrderItem orderItem : orderInfo.getOrderItems()) {
			// 1、生成订单明细id，使用redis的incr命令生成。
			Long detailId = jedisClient.incr(REDIS_ORDER_DETAIL_GEN_KEY);
			orderItem.setId(detailId.toString());
			orderItem.setOrderId(orderId.toString());
			tbOrderItemMapper.insert(orderItem);
		}

		// complete the order
		TbOrderShipping orderShipping = orderInfo.getOrderShipping();
		orderShipping.setOrderId(orderId.toString());
		orderShipping.setCreated(currDate);
		orderShipping.setUpdated(currDate);
		tbOrderShippingMapper.insert(orderShipping);

		// return the orderId
		return SmResult.ok(orderId);
	}

}
