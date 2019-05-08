package com.ncs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ncs.mapper.TbItemCatMapper;
import com.ncs.pojo.TbItemCat;
import com.ncs.pojo.TbItemCatExample;
import com.ncs.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	
	@Autowired
	private TbItemCatMapper tbItemCatMapper;
	
	@Override
	public List<TbItemCat> getItemCatList(long parentId) throws Exception {
		
		TbItemCatExample example=new TbItemCatExample();
		com.ncs.pojo.TbItemCatExample.Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> list = tbItemCatMapper.selectByExample(example);
		
		return list;
	}

}
