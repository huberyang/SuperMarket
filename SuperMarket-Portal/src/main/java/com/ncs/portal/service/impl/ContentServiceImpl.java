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
import com.ncs.portal.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {

	@Value("${rest_server_url}")
	private String rest_server_url;
	@Value("${rest_server_content_url}")
	private String rest_server_content_url;
	@Value("${ad_content_category}")
	private String ad_content_category;

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

}
