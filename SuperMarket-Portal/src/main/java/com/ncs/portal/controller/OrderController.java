package com.ncs.portal.controller;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ncs.pojo.TbUser;
import com.ncs.portal.service.OrderService;
import com.ncs.vo.OrderInfo;

@Controller("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createOrder(OrderInfo orderInfo, Model model, HttpServletRequest request) {

		TbUser user = (TbUser) request.getAttribute("user");

		// complete order info
		orderInfo.setUserId(user.getId());
		orderInfo.setBuyerNick(user.getUsername());

		String orderId = orderService.createOrder(orderInfo);
		
		// pass the orderId to the front end
		model.addAttribute("orderId", orderId);
		model.addAttribute("payment", orderInfo.getPayment());

		DateTime dateTime = new DateTime();
		dateTime = dateTime.plusDays(3);

		model.addAttribute("date", dateTime.toString("dd-MM-yyyy"));

		return "success";

	}


}
