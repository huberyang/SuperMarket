package com.ncs.service;

import java.util.List;

import com.ncs.common.pojo.EasyUITreePojo;

public interface ContentCatService {

	/**
	 * 根据父ID查询对应的内容分类数据集
	 * 
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public List<EasyUITreePojo> getContentCatList(Long parentId) throws Exception;

}
