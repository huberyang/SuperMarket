package com.ncs.service;

import java.util.List;

import com.ncs.common.utils.pojo.EasyUITreePojo;
import com.ncs.common.utils.pojo.SmResult;

public interface ContentCatService {

	/**
	 * 根据父ID查询对应的内容分类数据集
	 * 
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public List<EasyUITreePojo> getContentCatList(Long parentId) throws Exception;
	
	
	/**
	 * 更新对应的ContentCat内容
	 * @param id
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public SmResult updateContentCat(Long id,String name) throws Exception;
	
	/**
	 * 更新对应的ContentCat内容
	 * @param id
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public SmResult deleteContentCat(Long id) throws Exception;
	
	/**
	 * 保存新添加的ContentCat
	 * @param id
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public SmResult saveContentCat(Long parentId,String name) throws Exception;
	
	
	
	
	

}
