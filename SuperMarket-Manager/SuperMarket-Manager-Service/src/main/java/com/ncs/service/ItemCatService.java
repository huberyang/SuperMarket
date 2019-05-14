package com.ncs.service;

import java.util.List;

import com.ncs.pojo.TbItemCat;

public interface ItemCatService {
	
	/**
	 * 根据父id查询对应的商品分类
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	List<TbItemCat> getItemCatList(long parentId) throws Exception;

}
