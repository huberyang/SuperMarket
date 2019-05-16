package com.ncs.service;

import com.ncs.common.pojo.EasyDataGridResult;
import com.ncs.common.pojo.SmResult;
import com.ncs.vo.ContentCateVO;

public interface ContentService {

	/**
	 * 分页查询广告内容
	 * 
	 * @param pageSize
	 * @param pageNum
	 * @throws Exception
	 */
	EasyDataGridResult selectContentByPage(Long categoryId, int pageSize, int pageNum) throws Exception;

	/**
	 * 修改广告内容
	 * 
	 * @param contentCateVO
	 * @return
	 * @throws Exception
	 */
	SmResult updateContent(ContentCateVO contentCateVO) throws Exception;

	/**
	 * 保存广告内容
	 * 
	 * @param contentCateVO
	 * @return
	 * @throws Exception
	 */
	SmResult saveContent(ContentCateVO contentCateVO) throws Exception;

	/**
	 * 删除广告内容
	 * 
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	SmResult deleteContent(Long[] ids) throws Exception;

}
