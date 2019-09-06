package com.ncs.vo;

import java.util.List;

import com.ncs.pojo.TbOrder;
import com.ncs.pojo.TbOrderItem;
import com.ncs.pojo.TbOrderShipping;

public class OrderInfo extends TbOrder {

	private List<TbOrderItem> orderItems;

	private TbOrderShipping orderShipping;

	public List<TbOrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<TbOrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public TbOrderShipping getOrderShipping() {
		return orderShipping;
	}

	public void setOrderShipping(TbOrderShipping orderShipping) {
		this.orderShipping = orderShipping;
	}

}
