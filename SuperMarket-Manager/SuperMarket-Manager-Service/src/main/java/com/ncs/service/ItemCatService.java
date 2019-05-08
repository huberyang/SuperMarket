package com.ncs.service;

import java.util.List;

import com.ncs.pojo.TbItemCat;

public interface ItemCatService {
	
	
	List<TbItemCat> getItemCatList(long parentId) throws Exception;

}
