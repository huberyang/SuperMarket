package com.ncs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ncs.common.utils.pojo.EasyDataGridResult;
import com.ncs.common.utils.pojo.SmResult;
import com.ncs.common.utils.HttpClientUtils;
import com.ncs.pojo.TbContent;
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

	@Value("${rest_service_base_url}")
	private String rest_service_base_url;
	@Value("${rest_sync_content_url}")
	private String rest_sync_content_url;

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
		// 更新内容结束后，我们需要调用服务来清除redis缓存中的数据
		HttpClientUtils.doGet(rest_service_base_url + rest_sync_content_url + contentCateVO.getCategoryId());
		return result;
	}

	@RequestMapping(value = "/content/save", method = RequestMethod.POST)
	@ResponseBody
	public SmResult saveContent(ContentCateVO contentCateVO) throws Exception {
		SmResult result = contentService.saveContent(contentCateVO);
		// 保存新的content，我们需要清除之前的缓存
		HttpClientUtils.doGet(rest_service_base_url + rest_sync_content_url + contentCateVO.getCategoryId());
		return result;
	}

	@RequestMapping(value = "/content/delete", method = RequestMethod.POST)
	@ResponseBody
	public SmResult deleteContent(Long[] ids) throws Exception {
		String categoryId = "";

		// 删除内容前，我们需要查询当前的内容所属的类别，去清除其在缓存服务器中的内容
		TbContent content = contentService.findContentById(ids[0]);
		if (content != null) {
			categoryId = content.getCategoryId() + "";
		}

		SmResult result = contentService.deleteContent(ids);

		HttpClientUtils.doGet(rest_service_base_url + rest_sync_content_url + categoryId);
		return result;
	}

}
