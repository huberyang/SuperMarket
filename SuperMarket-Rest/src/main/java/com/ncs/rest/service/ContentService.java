package com.ncs.rest.service;

import java.util.List;

import com.ncs.pojo.TbContent;

public interface ContentService {

	/**
	 * 根据内容分类查询对应的广告内容
	 * 
	 * @param categoryId
	 * @return
	 * @throws Exception
	 */
	public List<TbContent> getContentList(Long categoryId) throws Exception;

}
