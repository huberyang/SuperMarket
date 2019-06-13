package com.ncs.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ncs.common.utils.pojo.SmResult;
import com.ncs.common.utils.ExceptionUtil;
import com.ncs.pojo.TbContent;
import com.ncs.rest.service.ContentService;

/**
 * 
 * @Title: ContentCateController.java
 * @Package com.ncs.rest.controller
 * @Description: TODO(广告内容操作)
 * @author: Hubery Yang
 * @date: May 15, 2019 10:45:10 PM
 * @version V1.0
 * @Copyright: 2019 Inc. All rights reserved.
 *
 */
@Controller
public class ContentCateController {

	@Autowired
	private ContentService contentService;

	@RequestMapping(value = "/content/{categoryId}")
	@ResponseBody
	public SmResult getContentList(@PathVariable("categoryId") Long categoryId) {
		try {
			List<TbContent> list = contentService.getContentList(categoryId);
			return SmResult.ok(list);
		} catch (Exception e) {
			e.printStackTrace();
			return SmResult.build(500, ExceptionUtil.getStatckTrace(e));
		}

	}
	
	//注意rest服务里面我配置的是/rest/...
	@RequestMapping(value = "/sync/content/{categoryId}")
	@ResponseBody
	public SmResult syncContent(@PathVariable("categoryId") Long categoryId) {
		
		try {
			SmResult result =contentService.syncContent(categoryId);
			return result;
		}catch(Exception e) {
			return SmResult.build(500, ExceptionUtil.getStatckTrace(e));
		}
	}

}
