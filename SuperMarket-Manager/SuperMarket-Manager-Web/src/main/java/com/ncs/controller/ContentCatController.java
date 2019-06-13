package com.ncs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ncs.common.utils.pojo.EasyUITreePojo;
import com.ncs.common.utils.pojo.SmResult;
import com.ncs.service.ContentCatService;

/**
 * 
 * @Title: ContentCatController.java
 * @Package com.ncs.controller
 * @Description: TODO(广告分类api)
 * @author: Hubery Yang
 * @date: May 15, 2019 10:54:12 AM
 * @version V1.0
 * @Copyright: 2019 Inc. All rights reserved.
 *
 */
@Controller
public class ContentCatController {

	@Autowired
	private ContentCatService contentCatService;

	@RequestMapping(value = "/content/category/list", method = RequestMethod.GET)
	@ResponseBody
	public List<EasyUITreePojo> getContentCatList(@RequestParam(value = "id", defaultValue = "0") Long parentId)
			throws Exception {

		List<EasyUITreePojo> contentCatList = contentCatService.getContentCatList(parentId);
		return contentCatList;
	}

	@RequestMapping(value = "/content/category/update", method = RequestMethod.POST)
	@ResponseBody
	public SmResult updateContentCat(Long id, String name) throws Exception {

		SmResult result = contentCatService.updateContentCat(id, name);
		return result;

	}

	@RequestMapping(value = "/content/category/delete", method = RequestMethod.POST)
	@ResponseBody
	public SmResult deleteContentCat(Long id) throws Exception {

		SmResult result = contentCatService.deleteContentCat(id);
		return result;
	}

	@RequestMapping(value = "/content/category/create", method = RequestMethod.POST)
	@ResponseBody
	public SmResult saveContentCat(Long parentId, String name) throws Exception {

		SmResult result = contentCatService.saveContentCat(parentId, name);
		return result;
	}

}
