package com.ncs.portal.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ncs.portal.pojo.SearchResult;
import com.ncs.portal.service.SearchService;

@Controller
public class SearchController {

	@Autowired
	private SearchService searchService;

	@RequestMapping("/search")
	public String searchData(@RequestParam("q") String keyword, @RequestParam(defaultValue="1") Integer page,
			@RequestParam(defaultValue="60") Integer rows,Model model) {

		// get乱码处理
		try {
			keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			keyword = "";
			e.printStackTrace();
		}
		try {
			//search 数据
			SearchResult searchResult = searchService.search(keyword, page, rows);
			//封装数据到ModelAndView
			//参数传递 给页面
			model.addAttribute("query", keyword);
			model.addAttribute("totalPages", searchResult.getPageCount());
			model.addAttribute("itemList", searchResult.getItemList());
			model.addAttribute("page", searchResult.getStart());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "search";
	}

}
