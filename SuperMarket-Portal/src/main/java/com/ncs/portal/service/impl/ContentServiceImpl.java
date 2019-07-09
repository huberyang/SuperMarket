package com.ncs.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ncs.common.utils.pojo.AdNode;
import com.ncs.common.utils.pojo.SmResult;
import com.ncs.common.utils.HttpClientUtils;
import com.ncs.common.utils.JsonUtils;
import com.ncs.pojo.TbContent;
import com.ncs.pojo.TbItem;
import com.ncs.pojo.TbItemDesc;
import com.ncs.pojo.TbItemParam;
import com.ncs.pojo.TbItemParamItem;
import com.ncs.portal.pojo.ItemDeatils;
import com.ncs.portal.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {

	@Value("${rest_server_url}")
	private String rest_server_url;
	@Value("${rest_server_content_url}")
	private String rest_server_content_url;
	@Value("${ad_content_category}")
	private String ad_content_category;
	@Value("${rest_server_item_base_url}")
	private String rest_server_item_base_url;
	@Value("${rest_server_item_param_url}")
	private String rest_server_item_param_url;
	@Value("${rest_server_item_desc_url}")
	private String rest_server_item_desc_url;

	@Override
	public String getBigADList() {

		String jsonResult = null;

		// httpClient请求rest 服务
		String url = rest_server_url + rest_server_content_url + ad_content_category;
		String smJson = HttpClientUtils.doGet(url);
		SmResult result = SmResult.formatToList(smJson, TbContent.class);
		List<TbContent> list = (List<TbContent>) result.getData();

		if (list != null && !list.isEmpty()) {
			List<AdNode> resultList = new ArrayList<>();
			for (TbContent tbContent : list) {
				// 设置node信息
				AdNode node = new AdNode();
				node.setHeight(240);
				node.setWidth(670);
				node.setSrc(tbContent.getPic());

				node.setHeightB(240);
				node.setWidthB(550);
				node.setSrcB(tbContent.getPic2());

				node.setAlt(tbContent.getSubTitle());
				node.setHref(tbContent.getUrl());

				resultList.add(node);
			}
			// 将结果转化为json 数据
			jsonResult = JsonUtils.objectToJson(resultList);
		}

		return jsonResult;
	}

	@Override
	public ItemDeatils getItemById(String itemId) throws Exception {
		ItemDeatils item = new ItemDeatils();
		// httpClient请求rest 服务
		String url = rest_server_url + rest_server_item_base_url + itemId;
		String smJson = HttpClientUtils.doGet(url);
		if (smJson != "") {

			SmResult result = SmResult.formatToPojo(smJson, TbItem.class);
			TbItem tbItem = (TbItem) result.getData();

			item.setId(tbItem.getId());
			item.setTitle(tbItem.getTitle());
			item.setSellPoint(tbItem.getSellPoint());
			item.setPrice(tbItem.getPrice());
			item.setImages(tbItem.getImage().split(","));

		}
		return item;
	}

	@Override
	public TbItemParamItem getItemParamById(String itemId) throws Exception {
		TbItemParamItem tbItemParamItem = null;

		// httpClient请求rest 服务
		String url = rest_server_url + rest_server_item_param_url + itemId;
		String smJson = HttpClientUtils.doGet(url);
		if (smJson != "") {
			tbItemParamItem = new TbItemParamItem();
			SmResult result = SmResult.formatToPojo(smJson, TbItemParamItem.class);
			tbItemParamItem = (TbItemParamItem) result.getData();
		}

		return tbItemParamItem;
	}

	@Override
	public TbItemDesc getItemDescById(String itemId) throws Exception {

		TbItemDesc tbItemDesc = null;
		// httpClient请求rest 服务
		String url = rest_server_url + rest_server_item_desc_url + itemId;
		String smJson = HttpClientUtils.doGet(url);
		if (smJson != "") {
			tbItemDesc = new TbItemDesc();
			SmResult result = SmResult.formatToPojo(smJson, TbItemDesc.class);
			tbItemDesc = (TbItemDesc) result.getData();
		}

		return tbItemDesc;
	}

}
