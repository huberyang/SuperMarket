package com.ncs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ncs.common.pojo.EasyUITreePojo;
import com.ncs.service.ContentCatService;

@Controller
public class ContentCatController {
	
	@Autowired
	private ContentCatService contentCatService;
	
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<EasyUITreePojo> getContentCatList(@RequestParam(value="id",defaultValue="0") Long parentId) throws Exception {
		
		List<EasyUITreePojo> contentCatList = contentCatService.getContentCatList(parentId);
		return contentCatList;
	}
	

}
