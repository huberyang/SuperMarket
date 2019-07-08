package com.ncs.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ncs.common.utils.ExceptionUtil;
import com.ncs.common.utils.pojo.SmResult;
import com.ncs.search.pojo.SearchResult;
import com.ncs.search.service.SearchService;

@Controller
public class SearchController {

	@Autowired
	private SearchService searchService;

	@RequestMapping("/query")
	@ResponseBody
	public SmResult search(@RequestParam(defaultValue = "") String keyword,
			@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "30") Integer rows) {

		try {
			// 更改请求参数的编码
			keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
			SearchResult result = searchService.queryData(keyword, page, rows);
			return SmResult.ok(result);
		} catch (Exception e) {
			e.printStackTrace();
			return SmResult.build(500, ExceptionUtil.getStatckTrace(e));
		}

	}
	
}
