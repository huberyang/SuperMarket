package com.ncs.portal.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ncs.common.utils.HttpClientUtils;
import com.ncs.common.utils.pojo.SmResult;
import com.ncs.portal.pojo.SearchResult;
import com.ncs.portal.service.SearchService;


@Service
public class SearchServiceImpl implements SearchService {

	@Value("${search_service_url}")
	private String search_service_url;

	@Override
	public SearchResult search(String keyword, int page, int rows) throws Exception {

		SearchResult searchResult = null;

		// 封装查询参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("keyword", keyword);
		params.put("page", page + "");
		params.put("rows", rows + "");

		String jsonSearchResult = HttpClientUtils.doGet(search_service_url, params);

		if (jsonSearchResult != null) {
			// 转换结果
			SmResult result = SmResult.formatToPojo(jsonSearchResult, SearchResult.class);
			searchResult = (SearchResult) result.getData();
		}

		return searchResult;
	}
}
