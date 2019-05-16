package com.ncs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ncs.common.pojo.EasyDataGridResult;
import com.ncs.common.pojo.SmResult;
import com.ncs.service.ContentService;
import com.ncs.vo.ContentCateVO;

/***
 * 
 * @Title: ContentController.java
 * @Package com.ncs.controller
 * @Description: TODO(广告操作api)
 * @author: Hubery Yang
 * @date: May 15, 2019 10:54:41 AM
 * @version V1.0
 * @Copyright: 2019 Inc. All rights reserved.
 *
 */
@Controller
public class ContentController {

	@Autowired
	private ContentService contentService;

	@RequestMapping(value = "/content/query/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyDataGridResult findItemList(Long categoryId, Integer page, Integer rows) throws Exception {

		// use pageHelper to Pagination
		EasyDataGridResult result = contentService.selectContentByPage(categoryId, page, rows);

		return result;
	}

	@RequestMapping(value = "/rest/content/edit", method = RequestMethod.POST)
	@ResponseBody
	public SmResult updateContent(ContentCateVO contentCateVO) throws Exception {

		SmResult result = contentService.updateContent(contentCateVO);
		return result;
	}

	@RequestMapping(value = "/content/save", method = RequestMethod.POST)
	@ResponseBody
	public SmResult saveContent(ContentCateVO contentCateVO) throws Exception {
		SmResult result = contentService.saveContent(contentCateVO);
		return result;
	}

	@RequestMapping(value = "/content/delete", method = RequestMethod.POST)
	@ResponseBody
	public SmResult deleteContent(Long[] ids) throws Exception {
		SmResult result = contentService.deleteContent(ids);
		return result;
	}

}
