package com.ncs.portal.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.ncs.common.utils.pojo.SmResult;
import com.ncs.pojo.TbItemDesc;
import com.ncs.pojo.TbItemParamItem;
import com.ncs.portal.pojo.ItemDeatils;
import com.ncs.portal.service.ContentService;
import com.ncs.portal.service.StaticPageGenerService;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class StaticPageGenerServiceImpl implements StaticPageGenerService {

	@Autowired
	private ContentService contentService;

	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;

	@Value("${static_page_path}")
	private String static_page_path;

	@Override
	public SmResult staticPageService(Long itemId) throws Exception {

		// call rest service to obtain item info for current itemId
		// get Item base info
		ItemDeatils itemDeatils = contentService.getItemById(itemId);
		// get item param info
		TbItemParamItem itemParamItems = contentService.getItemParamById(itemId);
		// get item desc info
		TbItemDesc itemDesc = contentService.getItemDescById(itemId);

		// load data, and insert into configuraion object that use for generating static page
		Configuration configuration = freeMarkerConfigurer.getConfiguration();
		// load the freemarker template page
		Template template = configuration.getTemplate("itemTemplate.ftl");
		// for each the data to the template page to generate static page
		Map dataList = new HashMap<>();
		dataList.put("item", itemDeatils);
		dataList.put("itemParam", itemParamItems);
		dataList.put("itemDesc", itemDesc);
		
		// create a write object to specify output static page filepath
		Writer writer = new FileWriter(new File(static_page_path + itemId + ".html"));
		// call the template object process function and to params is need
		template.process(dataList, writer);

		// flush the data steam and close writer steam
		writer.flush();
		writer.close();

		return SmResult.ok();

	}

}
